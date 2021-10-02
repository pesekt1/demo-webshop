package swc3.demowebshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swc3.demowebshop.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
