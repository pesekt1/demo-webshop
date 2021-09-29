# demo web shop server

## exceptionHandling

Add exceptionHandling package

- Create ErrorMessage class
- Create ResourceNotFoundException
- Create ControllerExceptionHandler

Now in TutorialServiceImpl class we can utilize our new exception -ResourceNotFoundException in all methods where we search by id:

```java
    public Tutorial getById(long id) {
        return tutorialRepository.findById(id) //returns Optional class
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage(id)));
    }
```
```java
    public void update(long id, Tutorial tutorial) {
        var updatedTutorial = tutorialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(errorMessage(id)));
```

```java
    public void delete(long id) {
        if (!tutorialRepository.existsById(id)) throw new ResourceNotFoundException(errorMessage(id));
        tutorialRepository.deleteById(id);
```
