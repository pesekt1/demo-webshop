package swc3.demowebshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swc3.demowebshop.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
