package swc3.demowebshop.DTOs;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import swc3.demowebshop.entities.InvoiceStatus;
import swc3.demowebshop.entities.Payment;

import java.time.LocalDate;
import java.util.Collection;

@Setter
@Getter
@EqualsAndHashCode
public class InvoiceDto {
    private String number;
    private double invoiceTotal;
    private double paymentTotal;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private LocalDate paymentDate;
    private int orderId;
    private InvoiceStatus status;
    private Collection<Payment> paymentsByInvoiceId;
}
