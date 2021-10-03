package swc3.demowebshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swc3.demowebshop.entities.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByCustomerId(int id);
}
