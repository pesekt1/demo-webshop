package swc3.demowebshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swc3.demowebshop.entities.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
}
