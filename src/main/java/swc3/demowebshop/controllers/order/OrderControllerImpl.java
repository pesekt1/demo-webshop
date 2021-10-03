package swc3.demowebshop.controllers.order;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swc3.demowebshop.DTOs.OrderDto;
import swc3.demowebshop.entities.Order;
import swc3.demowebshop.services.order.OrderServiceInterface;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderControllerImpl implements OrderControllerInterface{
    OrderServiceInterface orderService;
    ModelMapper modelMapper; // for entity <--> DTO conversion

    @Autowired
    public OrderControllerImpl(OrderServiceInterface orderService) {
        this.orderService = orderService;
        this.modelMapper = new ModelMapper();
    }

    //helper method to convert entity -> DTO
    private OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

    //helper method to convert DTO -> entity
    private Order convertToEntity(OrderDto orderDto) {
        return modelMapper.map(orderDto, Order.class);
    }

    @Override
    public List<OrderDto> getAll() {
        return orderService.getAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getById(int id) {
        return convertToDto(orderService.getById(id));
    }

    @Override
    public void create(OrderDto orderDto) {
        orderService.create(convertToEntity(orderDto));
    }

    @Override
    public void delete(int id) {
        orderService.delete(id);
    }
}