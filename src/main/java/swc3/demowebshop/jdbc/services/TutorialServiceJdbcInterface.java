package swc3.demowebshop.jdbc.services;
import swc3.demowebshop.jdbc.POJO.TutorialPOJO;

import java.util.List;

public interface TutorialServiceJdbcInterface {
    TutorialPOJO findById(long id);
    List<TutorialPOJO> findAll();
    void create(TutorialPOJO tutorial);
    void update(long id, TutorialPOJO tutorial);
    void delete(long id);
    List<TutorialPOJO> findAllVulnerable(String filter);
}
