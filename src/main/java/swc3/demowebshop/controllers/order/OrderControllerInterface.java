package swc3.demowebshop.controllers.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import swc3.demowebshop.DTOs.OrderDto;

import javax.validation.Valid;
import java.util.List;

public interface OrderControllerInterface {
    @GetMapping
    List<OrderDto> getAll();

    @GetMapping("/{id}")
    OrderDto getById(@PathVariable int id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(@Valid @RequestBody OrderDto orderDto);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable int id);
}
