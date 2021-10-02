package swc3.demowebshop.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

//TODO rating should be enum 1,2,3,4,5
@Setter
@Getter
@Entity
@Table(name = "product_ratings", schema = "swc3_webshop")
@IdClass(ProductRatingPK.class)
public class ProductRating {
    @Id
    @Column(name = "customer_id", nullable = false)
    private int customerId;

    @Id
    @Column(name = "product_id", nullable = false)
    private int productId;

    @Basic
    @Column(name = "rating", nullable = false)
    private int rating;

    @Basic
    @Column(name = "review", nullable = true, columnDefinition = "TEXT")
    private String review;

    @JsonBackReference
    @ManyToOne@JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false, insertable=false, updatable=false)
    private Customer customersByCustomerId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false, insertable=false, updatable=false)
    private Product productsByProductId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRating that = (ProductRating) o;
        return Objects.equals(rating, that.rating) && Objects.equals(review, that.review);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rating, review);
    }

}
