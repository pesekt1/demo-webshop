package swc3.demowebshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swc3.demowebshop.entities.ProductRating;

public interface ProductRatingRepository extends JpaRepository<ProductRating, Integer> {
}
