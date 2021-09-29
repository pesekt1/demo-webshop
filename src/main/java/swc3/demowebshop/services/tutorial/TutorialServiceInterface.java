package swc3.demowebshop.services.tutorial;

import swc3.demowebshop.entities.Tutorial;

import java.util.List;

public interface TutorialServiceInterface {
    Tutorial getById(long id);
    List<Tutorial> getAll(String title);
    void create(Tutorial tutorial);
    void update(long id, Tutorial tutorial);
    void delete(long id);
    void deleteAll();
    List<Tutorial> findByPublished(boolean published);
}
