package swc3.demowebshop.services.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swc3.demowebshop.entities.Payment;
import swc3.demowebshop.exceptionHandling.ResourceNotFoundException;
import swc3.demowebshop.repositories.InvoiceRepository;
import swc3.demowebshop.repositories.OrderRepository;
import swc3.demowebshop.repositories.PaymentRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentServiceInterface {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final InvoiceRepository invoiceRepository;

    private String errorMessage(long id){
        return "Not found payment with id = " + id;
    }

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              OrderRepository orderRepository,
                              InvoiceRepository invoiceRepository
    ) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment getById(int id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage(id)));
    }

    @Override
    public List<Payment> getByCustomerId(int id) {
        return paymentRepository.findByCustomerId(id);
    }

    @Override
    public void create(Payment payment) {
        payment.setDate(LocalDate.now());
        paymentRepository.save(payment);
    }

    @Override
    public void delete(int id) {
        if (!paymentRepository.existsById(id)) throw new ResourceNotFoundException(errorMessage(id));
        paymentRepository.deleteById(id);
    }

    private int findCustomerId(int invoiceId){
        var invoice = invoiceRepository.findById(invoiceId).orElseThrow(() -> new ResourceNotFoundException("invoice not found"));
        var order = orderRepository.findById(invoice.getOrderId()).orElseThrow(() -> new ResourceNotFoundException("order not found"));
        return order.getCustomerId();
    }
}
