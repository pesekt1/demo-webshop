package swc3.demowebshop.DTOs;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import swc3.demowebshop.entities.OrderItem;

import java.util.Collection;

@EqualsAndHashCode
@Setter
@Getter
public class OrderDto {
    private String comments;
    private Collection<OrderItem> orderItems;
    private int customerId;
}
