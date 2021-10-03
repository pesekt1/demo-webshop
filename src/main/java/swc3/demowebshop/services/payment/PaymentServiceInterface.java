package swc3.demowebshop.services.payment;

import swc3.demowebshop.entities.Payment;
import java.util.List;

public interface PaymentServiceInterface {
    List<Payment> getAll();
    Payment getById(int id);
    List<Payment> getByCustomerId(int id);
    void create(Payment payment);
    void delete(int id);
}
