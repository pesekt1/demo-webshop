package swc3.demowebshop.jdbc.DAO;

import java.util.List;
import java.util.Optional;

public interface DaoInterface<T, U extends Number> {
    List<T> findAll();
    List<T> findAllVulnerable(String filter);
    void create(T t);
    Optional<T> findById(U id);
    void update(T t, U id);
    void delete(U id);
}