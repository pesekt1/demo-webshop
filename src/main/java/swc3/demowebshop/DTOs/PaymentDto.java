package swc3.demowebshop.DTOs;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Setter
@Getter
public class PaymentDto {
    private int invoiceId;
    private int customerId;
    private double amount;
    private byte paymentMethod;
}
