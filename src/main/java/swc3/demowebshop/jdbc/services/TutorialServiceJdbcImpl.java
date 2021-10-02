package swc3.demowebshop.jdbc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swc3.demowebshop.exceptionHandling.ResourceNotFoundException;
import swc3.demowebshop.jdbc.DAO.TutorialDAO;
import swc3.demowebshop.jdbc.POJO.TutorialPOJO;

import java.util.ArrayList;
import java.util.List;

@Service
public class TutorialServiceJdbcImpl implements TutorialServiceJdbcInterface{
    TutorialDAO dao;

    @Autowired
    public TutorialServiceJdbcImpl(TutorialDAO dao){
        this.dao = dao;
    }

    private String errorMessage(long id){
        return "Not found Tutorial with id = " + id;
    }

    @Override
    public TutorialPOJO findById(long id) {
        return dao.findById(id) //returns Optional class
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage(id)));
    }

    @Override
    public List<TutorialPOJO> findAll() {
        return new ArrayList<>(dao.findAll());
    }

    @Override
    public void create(TutorialPOJO tutorial) {
        dao.create(tutorial);
    }

    @Override
    public void update(long id, TutorialPOJO tutorial) {
        var updatedTutorial = dao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage(id)));

        updatedTutorial.setTitle(tutorial.getTitle());
        updatedTutorial.setDescription(tutorial.getDescription());
        updatedTutorial.setPublished(tutorial.getPublished());
        dao.update(updatedTutorial, id);
    }

    @Override
    public void delete(long id) {
        dao.findById(id).orElseThrow(() -> new ResourceNotFoundException(errorMessage(id)));
        dao.delete(id);
    }

    @Override
    public List<TutorialPOJO> findAllVulnerable(String filter) {
        return dao.findAllVulnerable(filter);
    }
}