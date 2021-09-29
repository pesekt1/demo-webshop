package swc3.demowebshop.controllers.tutorial;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import swc3.demowebshop.DTOs.TutorialDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface TutorialControllerInterface {

    @GetMapping
    List<TutorialDto> getAll(@RequestParam(required = false) String title);

    @GetMapping("/paginated")
    Map<String, Object> getAllPaginated(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size);

    @GetMapping("/{id}")
    TutorialDto getById(@PathVariable("id") long id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void create(@Valid @RequestBody TutorialDto tutorial);

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@Valid @PathVariable("id") long id, @RequestBody TutorialDto tutorial);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable("id") long id);

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAll();

    @GetMapping("/published")
    List<TutorialDto> findByPublished(@RequestBody boolean published);
}
