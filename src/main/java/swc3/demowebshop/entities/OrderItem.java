package swc3.demowebshop.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

//use @EmbeddableId, find out how to connect with OneToMany from Order
@Setter
@Getter
@Entity
@EqualsAndHashCode
@Table(name = "order_items", schema = "swc3_webshop")
@IdClass(OrderItemPK.class)
public class OrderItem {
    @Id
    @Column(name = "order_id", nullable = false)
    private int orderId;

    @Id
    @Column(name = "product_id", nullable = false)
    private int productId;

    @Basic
    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Basic
    @Column(name = "unit_price", columnDefinition = "DECIMAL(9,2)", nullable = false, precision = 2)
    private double unitPrice;

    @OneToMany(mappedBy = "orderItems")
    private Collection<OrderItemNote> orderItemNotes;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false, insertable=false, updatable=false)
    private Product productsByProductId;

    @Transient
    public double getTotalPrice() {
        return getUnitPrice() * getQuantity();
    }
}
