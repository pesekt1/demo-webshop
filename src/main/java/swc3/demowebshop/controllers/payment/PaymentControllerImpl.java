package swc3.demowebshop.controllers.payment;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swc3.demowebshop.DTOs.PaymentDto;
import swc3.demowebshop.entities.Payment;
import swc3.demowebshop.services.payment.PaymentServiceInterface;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentControllerImpl implements PaymentControllerInterface {
    PaymentServiceInterface paymentService;
    ModelMapper modelMapper; // for entity <--> DTO conversion

    @Autowired
    public PaymentControllerImpl(PaymentServiceInterface paymentService, ModelMapper modelMapper) {
        this.paymentService = paymentService;
        this.modelMapper = modelMapper;
    }


    //helper method to convert entity -> DTO
    private PaymentDto convertToDto(Payment payment) {
        return modelMapper.map(payment, PaymentDto.class);
    }

    //helper method to convert DTO -> entity
    private Payment convertToEntity(PaymentDto paymentDto) {
        return modelMapper.map(paymentDto, Payment.class);
    }

    @Override
    public List<PaymentDto> getAll() {
        return paymentService.getAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public PaymentDto getById(int id) {
        return convertToDto(paymentService.getById(id));
    }

    @Override
    public List<PaymentDto> getByCustomerId(int id) {
        return paymentService.getByCustomerId(id)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public void create(@Valid PaymentDto payment) {
        paymentService.create(convertToEntity(payment));
    }

    @Override
    public void delete(int id) {
        paymentService.delete(id);
    }
}
