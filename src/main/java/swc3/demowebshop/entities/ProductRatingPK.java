package swc3.demowebshop.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
public class ProductRatingPK implements Serializable {
    @Id
    @Column(name = "customer_id", nullable = false)
    private int customerId;

    @Id
    @Column(name = "product_id", nullable = false)
    private int productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRatingPK that = (ProductRatingPK) o;
        return customerId == that.customerId && productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, productId);
    }
}
