package swc3.demowebshop.DTOs;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@EqualsAndHashCode
@Setter
@Getter
public class OrderItemDto {
    private int productId;
    private int quantity;
    private double unitPrice;
    private Collection<OrderItemNoteDto> orderItemNotes;
}
