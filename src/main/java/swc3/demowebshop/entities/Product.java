package swc3.demowebshop.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "products", schema = "swc3_webshop")
public class Product {
    private int productId;
    private String name;
    private int quantityInStock;
    private BigDecimal unitPrice;
    private String imagePath;
    private BigDecimal ratingAverage;
    private Integer ratingsNumber;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "quantity_in_stock", nullable = false)
    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    @Basic
    @Column(name = "unit_price", nullable = false, precision = 2)
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Basic
    @Column(name = "image_path", nullable = true, length = 255)
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Basic
    @Column(name = "rating_average", nullable = true, precision = 6)
    public BigDecimal getRatingAverage() {
        return ratingAverage;
    }

    public void setRatingAverage(BigDecimal ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

    @Basic
    @Column(name = "ratings_number", nullable = true)
    public Integer getRatingsNumber() {
        return ratingsNumber;
    }

    public void setRatingsNumber(Integer ratingsNumber) {
        this.ratingsNumber = ratingsNumber;
    }

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
