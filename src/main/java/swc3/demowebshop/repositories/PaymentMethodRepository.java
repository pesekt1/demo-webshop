package swc3.demowebshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swc3.demowebshop.entities.PaymentMethod;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Byte> {
}
