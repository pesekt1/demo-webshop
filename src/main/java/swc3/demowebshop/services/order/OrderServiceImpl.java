package swc3.demowebshop.services.order;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swc3.demowebshop.entities.*;
import swc3.demowebshop.exceptionHandling.ResourceNotFoundException;
import swc3.demowebshop.repositories.OrderItemNoteRepository;
import swc3.demowebshop.repositories.OrderItemRepository;
import swc3.demowebshop.repositories.OrderRepository;
import swc3.demowebshop.services.invoice.InvoiceServiceInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderServiceInterface {

    private static final byte NEW_ORDER_STATUS = (byte)4;
    private static final int INVOICE_DUE_PERIOD = 30;

    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;
    OrderItemNoteRepository orderItemNoteRepository;
    InvoiceServiceInterface invoiceService;

    private String errorMessage(long id){
        return "Not found order with id = " + id;
    }

    @Autowired
    public OrderServiceImpl(
            OrderRepository ordersRepository,
            OrderItemRepository orderItemRepository,
            OrderItemNoteRepository orderItemNoteRepository,
            InvoiceServiceInterface invoiceService
    ) {
        this.orderRepository = ordersRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderItemNoteRepository = orderItemNoteRepository;
        this.invoiceService = invoiceService;
    }

    @Override
    public Order getById(int id) {
        return orderRepository.findById(id) //returns Optional class
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage(id)));
    }

    @Override
    public List<Order> getAll() {
        return new ArrayList<>(orderRepository.findAll());
    }

    @Override
    public void create(Order order) {
        order.setOrderDate(LocalDate.now()); //set the date
        order.setStatus(NEW_ORDER_STATUS); //set the default status

        var savedOrder = orderRepository.save(order);

        //saving order items
        var orderItems = savedOrder.getOrderItems();
        for (OrderItem orderItem:orderItems) {
            orderItem.setOrderId(savedOrder.getOrderId());
            var orderItemNotes = orderItem.getOrderItemNotes();
            var savedOrderItem = orderItemRepository.save(orderItem);

            //saving order item notes
            for (OrderItemNote orderItemNote:orderItemNotes) {
                orderItemNote.setOrderId(savedOrderItem.getOrderId());
                orderItemNote.setProductId(savedOrderItem.getProductId());
            }
            orderItemNoteRepository.saveAll(orderItemNotes);
        }
        createInvoice(savedOrder);
    }

    @Override
    public void delete(int id) {
        if (!orderRepository.existsById(id)) throw new ResourceNotFoundException(errorMessage(id));
        orderRepository.deleteById(id);
    }

    private void createInvoice(Order savedOrder) {
        var invoice = new Invoice();
        invoice.setNumber(ObjectId.get().toString()); //using ObjectId data type - generating new ObjectId
        invoice.setInvoiceTotal(savedOrder.getTotalOrderPrice());
        invoice.setPaymentTotal(0);
        invoice.setInvoiceDate(savedOrder.getOrderDate());
        invoice.setDueDate(savedOrder.getOrderDate().plusDays(INVOICE_DUE_PERIOD));
        invoice.setOrderId(savedOrder.getOrderId());
        invoice.setStatus(InvoiceStatus.OPEN);

        invoiceService.create(invoice);
    }
}