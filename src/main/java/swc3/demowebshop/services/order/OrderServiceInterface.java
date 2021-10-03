package swc3.demowebshop.services.order;

import swc3.demowebshop.entities.Order;
import java.util.List;

public interface OrderServiceInterface {
    Order getById(int id);
    List<Order> getAll();
    void create(Order order);
    //void update(int id, Order order);
    void delete(int id);
}
