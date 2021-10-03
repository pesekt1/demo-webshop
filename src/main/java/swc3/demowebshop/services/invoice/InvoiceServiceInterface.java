package swc3.demowebshop.services.invoice;

import swc3.demowebshop.entities.Invoice;
import swc3.demowebshop.entities.InvoiceStatus;

import java.util.List;

public interface InvoiceServiceInterface {
    List<Invoice> getAll();
    Invoice getById(int id);
    List<Invoice> getByOrderId(int id);
    List<Invoice> getByStatus(InvoiceStatus status);
    void checkOverdue();
    void create(Invoice invoice);
    void delete(int id);
}
