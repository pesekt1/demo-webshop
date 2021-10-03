package swc3.demowebshop.controllers.invoice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import swc3.demowebshop.DTOs.InvoiceDto;
import swc3.demowebshop.entities.InvoiceStatus;

import java.util.List;

public interface InvoiceControllerInterface {
    @GetMapping
    List<InvoiceDto> getAll();

    @GetMapping("/check-overdue")
    void checkOverdue();

    @GetMapping("/{id}")
    InvoiceDto getById(@PathVariable int id);

    @GetMapping("/order/{id}")
    List<InvoiceDto> getByOrderId(@PathVariable int id);

    @GetMapping("/status")
    List<InvoiceDto> getByStatus(@RequestParam(required = true) InvoiceStatus status);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable int id);
}
