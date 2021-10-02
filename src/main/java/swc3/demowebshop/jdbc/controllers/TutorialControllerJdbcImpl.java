package swc3.demowebshop.jdbc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swc3.demowebshop.jdbc.POJO.TutorialPOJO;
import swc3.demowebshop.jdbc.services.TutorialServiceJdbcInterface;

import java.util.List;

@RestController
@RequestMapping("/api-jdbc/tutorials")
public class TutorialControllerJdbcImpl implements TutorialControllerJdbcInterface {
    TutorialServiceJdbcInterface tutorialService;

    @Autowired
    public TutorialControllerJdbcImpl(TutorialServiceJdbcInterface tutorialService) {
        this.tutorialService = tutorialService;
    }

    @Override
    public List<TutorialPOJO> findAll() {
        return tutorialService.findAll();
    }

    @Override
    public TutorialPOJO findById(long id) {
        return tutorialService.findById(id);
    }

    @Override
    public void create(TutorialPOJO tutorial) {
        tutorialService.create(tutorial);
    }

    @Override
    public void update(TutorialPOJO tutorial, long id) {
        tutorialService.update(id,tutorial);
    }

    @Override
    public void delete(long id) {
        tutorialService.delete(id);
    }


    /**
     for showing an sql injection vulnerability
     This endpoint is vulnerable, it allows SQL injection
     use these lines as an argument:
     "tutorial1" OR 1 = 1
     "tutorial1"; DELETE from tutorials
     "tutorial1"; DROP table tutorials
     * **/
    @GetMapping("/vulnerable")
    public List<TutorialPOJO> getAllVulnerable(@RequestParam String filter) {
        return tutorialService.findAllVulnerable(filter);
    }
}