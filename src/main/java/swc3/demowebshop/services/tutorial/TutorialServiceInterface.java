package swc3.demowebshop.services.tutorial;

import swc3.demowebshop.entities.Tutorial;

import java.util.List;
import java.util.Map;

public interface TutorialServiceInterface {
    Tutorial getById(long id);
    List<Tutorial> getAll(String title);
    Map<String, Object> getAll(String title, int page, int size); //overloaded method for pagination
    void create(Tutorial tutorial);
    void update(long id, Tutorial tutorial);
    void delete(long id);
    void deleteAll();
    List<Tutorial> findByPublished(boolean published);
}
