# demo web shop server

## Pagination

We need to return a Map structure as a response from our controller.

in the repository, overload the methods to accept Pageable object:
```java
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {

	//The implementation is plugged in by Spring Data JPA automatically.
	List<Tutorial> findByPublished(boolean published);
	List<Tutorial> findByTitleContaining(String title);

	//overloaded methods using Pageable parameter:
	Page<Tutorial> findByPublished(boolean published, Pageable pageable);
	Page<Tutorial> findByTitleContaining(String title, Pageable pageable);
}
```

in the service, overload getAll method to accept page and size:

```java
public interface TutorialServiceInterface {
    Tutorial getById(long id);
    List<Tutorial> getAll(String title);
    Map<String, Object> getAll(String title, int page, int size); //overloaded method for pagination
```

Implement it the class: This one will return a map with paginated data:
```java
    @Override
    //pagination - overloaded method
    public Map<String, Object> getAll(String title, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
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
```

Add new method in the controller interface:
```java
public interface TutorialControllerInterface {
    @GetMapping("/paginated")
    Map<String, Object> getAllPaginated(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size);
```

Implement it in the controller class:
```java
	//retrieve paginated tutorials
	@Override
	public Map<String, Object> getAllPaginated(String title, int page, int size){
		return tutorialService.getAll(title, page, size);
	}
```

Done!

- Test it locally
- Deploy and test on heroku.