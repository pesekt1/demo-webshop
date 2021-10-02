package swc3.demowebshop.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "order_statuses", schema = "swc3_webshop")
public class OrderStatus {
    private byte orderStatusId;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_status_id", nullable = false)
    public byte getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(byte orderStatusId) {
        this.orderStatusId = orderStatusId;
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
        OrderStatus that = (OrderStatus) o;
        return orderStatusId == that.orderStatusId && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderStatusId, name);
    }
}
