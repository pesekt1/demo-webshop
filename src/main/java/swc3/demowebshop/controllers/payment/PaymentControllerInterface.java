package swc3.demowebshop.controllers.payment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import swc3.demowebshop.DTOs.PaymentDto;

import javax.validation.Valid;
import java.util.List;

public interface PaymentControllerInterface {
    @GetMapping
    List<PaymentDto> getAll();

    @GetMapping("/{id}")
    PaymentDto getById(@PathVariable int id);

    @GetMapping("/customer/{id}")
    List<PaymentDto> getByCustomerId(@PathVariable int id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(@Valid @RequestBody PaymentDto payment);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable int id);
}
