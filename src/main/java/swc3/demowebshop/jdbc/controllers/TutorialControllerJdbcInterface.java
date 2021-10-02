package swc3.demowebshop.jdbc.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import swc3.demowebshop.jdbc.POJO.TutorialPOJO;


import javax.validation.Valid;
import java.util.List;

public interface TutorialControllerJdbcInterface {
    @GetMapping
    List<TutorialPOJO> findAll();

    @GetMapping("/{id}")
    TutorialPOJO findById(@PathVariable long id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //201
    void create(@Valid @RequestBody TutorialPOJO tutorial);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@Valid @RequestBody TutorialPOJO tutorial, @PathVariable long id);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) //204
    void delete(@PathVariable long id);
}
