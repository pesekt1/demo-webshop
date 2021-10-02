package swc3.demowebshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swc3.demowebshop.entities.OrderItemNote;

public interface OrderItemNoteRepository extends JpaRepository<OrderItemNote, Integer> {
}
