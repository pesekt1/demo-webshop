package swc3.demowebshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swc3.demowebshop.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
