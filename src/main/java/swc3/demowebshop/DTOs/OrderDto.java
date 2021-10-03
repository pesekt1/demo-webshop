package swc3.demowebshop.DTOs;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@EqualsAndHashCode
@Setter
@Getter
public class OrderDto {
    private String comments;
    private Collection<OrderItemDto> orderItems;
    private int customerId;
}
