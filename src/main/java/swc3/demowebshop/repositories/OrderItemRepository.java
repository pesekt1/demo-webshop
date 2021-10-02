package swc3.demowebshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swc3.demowebshop.entities.OrderItem;
import swc3.demowebshop.entities.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
