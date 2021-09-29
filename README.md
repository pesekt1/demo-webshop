# demo web shop server

## integration tests:

in Tutorial entity class, add a constructor: We need it for our tests.

```java
    //this constructor is for the testing suite because we need to create some objects
    public Tutorial(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }
```

Create some tests:
```java
//spring boot will do all the work on the database within a transaction and then it rolls back.
//so the database is not affected by the tests.
@SpringBootTest
@AutoConfigureDataJpa
@Transactional
class TutorialsIntegrationTests {

    @Autowired
    private TutorialRepository repository;

    @BeforeEach
    public void init(){
        repository.deleteAll();
    }

    @Test
    void should_find_no_tutorials_if_repository_is_empty() {
        Iterable<Tutorial> tutorials = repository.findAll();
        assertThat(tutorials).isEmpty();
    }

    @Test
    void should_store_a_tutorial() {
        Tutorial tutorial = repository.save(new Tutorial("Tut title", "Tut desc", true));
        assertThat(tutorial)
            .hasFieldOrPropertyWithValue("title", "Tut title")
            .hasFieldOrPropertyWithValue("description", "Tut desc")
            .hasFieldOrPropertyWithValue("published", true);
    }

}
```

- Set up environment variables for tests: it must be set up separately - it cannot read the ones for the application.
- So set up the same connection string to the database.
- tests will run on the same database.

Make sure it works locally. For Heroku deployment we need to set up other things. We will do it in a separate branch.
