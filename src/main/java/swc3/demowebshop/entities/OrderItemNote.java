package swc3.demowebshop.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "order_item_notes", schema = "swc3_webshop")
public class OrderItemNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id", nullable = false)
    private int noteId;

    @Basic
    @Column(name = "note", nullable = false, length = 255)
    private String note;

    @Basic
    @Column(name = "order_id", nullable = false)
    private int orderId;

    @Basic
    @Column(name = "product_id", nullable = false)
    private int productId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false, insertable=false, updatable=false)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false, insertable=false, updatable=false)
    private OrderItem orderItems;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemNote that = (OrderItemNote) o;
        return noteId == that.noteId && Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noteId, note);
    }

}
