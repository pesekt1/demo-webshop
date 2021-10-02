package swc3.demowebshop.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "products", schema = "swc3_webshop")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private int productId;

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Basic
    @Column(name = "quantity_in_stock", nullable = false)
    private int quantityInStock;

    @Basic
    @Column(name = "unit_price", nullable = false, precision = 2)
    private BigDecimal unitPrice;

    @Basic
    @Column(name = "image_path", nullable = true, length = 255)
    private String imagePath;

    @Basic
    @Column(name = "rating_average", nullable = true, precision = 6)
    private BigDecimal ratingAverage;

    @Basic
    @Column(name = "ratings_number", nullable = true)
    private Integer ratingsNumber;

    @JsonBackReference
    @OneToMany(mappedBy = "productsByProductId")
    private Collection<OrderItem> orderItemsByProductId;

    @OneToMany(mappedBy = "productId")
    private Collection<ProductRating> productRatingsByProductId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId && quantityInStock == product.quantityInStock && Objects.equals(name, product.name) && Objects.equals(unitPrice, product.unitPrice) && Objects.equals(imagePath, product.imagePath) && Objects.equals(ratingAverage, product.ratingAverage) && Objects.equals(ratingsNumber, product.ratingsNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, quantityInStock, unitPrice, imagePath, ratingAverage, ratingsNumber);
    }

}
