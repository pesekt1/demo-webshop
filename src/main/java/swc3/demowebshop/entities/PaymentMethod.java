package swc3.demowebshop.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "payment_methods", schema = "swc3_webshop")
public class PaymentMethod {
    private byte paymentMethodId;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_method_id", nullable = false)
    public byte getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(byte paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentMethod that = (PaymentMethod) o;
        return paymentMethodId == that.paymentMethodId && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentMethodId, name);
    }
}
