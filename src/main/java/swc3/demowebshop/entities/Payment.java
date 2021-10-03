package swc3.demowebshop.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "payments", schema = "swc3_webshop")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private int paymentId;

    @Basic
    @Column(name = "invoice_id", nullable = false)
    private int invoiceId;

    @Basic
    @Column(name = "customer_id", nullable = false)
    private int customerId;

    @Basic
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Basic
    @Column(name = "amount", columnDefinition = "DECIMAL(9,2)", nullable = false, precision = 2)
    private double amount;

    @Basic
    @Column(name = "payment_method", nullable = false)
    private byte paymentMethod;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return paymentId == payment.paymentId && paymentMethod == payment.paymentMethod && Objects.equals(date, payment.date) && Objects.equals(amount, payment.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, date, amount, paymentMethod);
    }

}
