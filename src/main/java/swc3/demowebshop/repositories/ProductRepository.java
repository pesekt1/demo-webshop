package swc3.demowebshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swc3.demowebshop.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
