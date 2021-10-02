package swc3.demowebshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swc3.demowebshop.entities.OrderStatus;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Byte> {
}
