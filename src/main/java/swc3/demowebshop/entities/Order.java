package swc3.demowebshop.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "orders", schema = "swc3_webshop")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private int orderId;

    @Basic
    @Column(name = "customer_id", nullable = false)
    private int customerId;

    @Basic
    @Column(name = "order_date", nullable = false)
    private Date orderDate;

    @Basic
    @Column(name = "status", nullable = false)
    private byte status;

    @Basic
    @Column(name = "comments", nullable = true, length = 2000)
    private String comments;

    @Basic
    @Column(name = "shipped_date", nullable = true)
    private Date shippedDate;

    @Basic
    @Column(name = "shipper_id", nullable = true)
    private Short shipperId;

    @OneToMany(mappedBy = "orderId")
    private Collection<Invoice> invoicesByOrderId;

    @OneToMany(mappedBy = "orderId")
    private Collection<OrderItem> orderItemsByOrderId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId && status == order.status && Objects.equals(orderDate, order.orderDate) && Objects.equals(comments, order.comments) && Objects.equals(shippedDate, order.shippedDate) && Objects.equals(shipperId, order.shipperId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, orderDate, status, comments, shippedDate, shipperId);
    }

}
