package swc3.demowebshop.services.tutorial;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import swc3.demowebshop.DTOs.TutorialDto;
import swc3.demowebshop.entities.Tutorial;
import swc3.demowebshop.exceptionHandling.ResourceNotFoundException;
import swc3.demowebshop.repositories.TutorialRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TutorialServiceImpl implements TutorialServiceInterface {
    TutorialRepository tutorialRepository;
    ModelMapper modelMapper; // for entity <--> DTO conversion

    private String errorMessage(long id){
        return "Not found Tutorial with id = " + id;
    }

    @Autowired
    public TutorialServiceImpl(TutorialRepository tutorialRepository){
        this.tutorialRepository = tutorialRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public Tutorial getById(long id) {
        return tutorialRepository.findById(id) //returns Optional class
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage(id)));
    }

    @Override
    public List<Tutorial> getAll(String title) {
        List<Tutorial> tutorials = new ArrayList<>();

        if (title == null)
            tutorials.addAll(tutorialRepository.findAll());
        else
            tutorials.addAll(tutorialRepository.findByTitleContaining(title));

        return tutorials;
    }

    @Override
    public void create(Tutorial tutorial) {
        tutorial.setPublished(false);
        tutorialRepository.save(tutorial);
    }

    @Override
    public void update(long id, Tutorial tutorial) {
        var updatedTutorial = tutorialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage(id)));

        updatedTutorial.setTitle(tutorial.getTitle());
        updatedTutorial.setDescription(tutorial.getDescription());
        updatedTutorial.setPublished(tutorial.getPublished());

        tutorialRepository.save(updatedTutorial);
    }

    @Override
    public void delete(long id) {
        if (!tutorialRepository.existsById(id)) throw new ResourceNotFoundException(errorMessage(id));
        tutorialRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        tutorialRepository.deleteAll();
    }

    @Override
    public List<Tutorial> findByPublished(boolean published) {
        return tutorialRepository.findByPublished(published);
    }

    @Override
    //pagination - overloaded method
    public Map<String, Object> getAll(String title, int page, int size) {
        Pageable paging = PageRequest.of(page, size); //paging
        return getResponseMap(title, paging);
    }

    //pagination and sorting - overloaded method
    @Override
    public Map<String, Object> getAll(String title, int page, int size, String[] sort) {
        List<Sort.Order> orders = getSortOrders(sort); //object for sorting
        Pageable paging = PageRequest.of(page, size, Sort.by(orders)); // paging with sorting
        return getResponseMap(title, paging);
    }

    //helper method retrieving a list of sort orders
    private List<Sort.Order> getSortOrders(String[] sort) {
        List<Sort.Order> orders = new ArrayList<>();

        if (sort[0].contains(",")) {
            // will sort more than 2 fields
            // sortOrder="field, direction"
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            // sort=[field, direction]
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        return orders;
    }

    //helper method retrieving the Sort.Direction enum
    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

    //helper method to create the response as a Map
    private Map<String, Object> getResponseMap(String title, Pageable paging) {
        Page<Tutorial> pageTuts;

        if (title == null)
            pageTuts = tutorialRepository.findAll(paging);
        else
            pageTuts = tutorialRepository.findByTitleContaining(title, paging);

        List<Tutorial> tutorials = pageTuts.getContent();
        //convert to DTO
        List<TutorialDto> tutorialsDto = tutorials.stream()
                .map(tutorial -> modelMapper.map(tutorial, TutorialDto.class))
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("tutorials", tutorialsDto);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalItems", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());

        return response;
    }
}