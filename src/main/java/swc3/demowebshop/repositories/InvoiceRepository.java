package swc3.demowebshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swc3.demowebshop.entities.Invoice;
import swc3.demowebshop.entities.InvoiceStatus;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    List<Invoice> findByOrderId(int id);
    List<Invoice> findByStatus(InvoiceStatus status);
}
