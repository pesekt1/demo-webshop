package swc3.demowebshop.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "invoices", schema = "swc3_webshop")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id", nullable = false)
    private int invoiceId;

    @Basic
    @Column(name = "number", nullable = false, length = 50)
    private String number;

    @Basic
    @Column(name = "invoice_total", nullable = false, precision = 2)
    private BigDecimal invoiceTotal;

    @Basic
    @Column(name = "payment_total", nullable = false, precision = 2)
    private BigDecimal paymentTotal;

    @Basic
    @Column(name = "invoice_date", nullable = false)
    private Date invoiceDate;

    @Basic
    @Column(name = "due_date", nullable = false)
    private Date dueDate;

    @Basic
    @Column(name = "payment_date", nullable = true)
    private Date paymentDate;

    @Basic
    @Column(name = "order_id", nullable = false)
    private int orderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InvoiceStatus status;

    @OneToMany(mappedBy = "invoiceId")
    private Collection<Payment> paymentsByInvoiceId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return invoiceId == invoice.invoiceId && Objects.equals(number, invoice.number) && Objects.equals(invoiceTotal, invoice.invoiceTotal) && Objects.equals(paymentTotal, invoice.paymentTotal) && Objects.equals(invoiceDate, invoice.invoiceDate) && Objects.equals(dueDate, invoice.dueDate) && Objects.equals(paymentDate, invoice.paymentDate) && Objects.equals(status, invoice.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, number, invoiceTotal, paymentTotal, invoiceDate, dueDate, paymentDate, status);
    }

}
