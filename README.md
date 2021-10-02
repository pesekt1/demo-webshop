# demo web shop server

- This is a Spring Boot Server demo project.
- Each branch is implementing some new feature.

## branches sequence:
- helloworld
- CRUD-tutorials
- swagger-DATA-REST
- exceptionHandling
- pagination
- integration_tests
- CI-CD
- httpClient  
- sorting
- profiles
- custom-queries
- spring_security

## implementing JDBC - communication with the database via a native driver.

We can communicate with the database directly via JDBC driver. 
That means we do not use JPA with Hibernate. We will not have entity classes mapping the tables.
- Create jdbc package

### Model classes:
- Create a TutorialPOJO class - POJO - plain old java object
```java
@Setter
@Getter
@EqualsAndHashCode
public class TutorialPOJO {
    @JsonIgnore
    private long id;
    private String description;
    private Boolean published;
    private String title;
}
```
### DAO -Data access object layer:
- Create a RowMapper for the TutorialPOJO class
```java
public class TutorialRowMapper implements RowMapper<TutorialPOJO> {

    @Override
    public TutorialPOJO mapRow(ResultSet resultSet, int i) throws SQLException {
        TutorialPOJO tutorial = new TutorialPOJO();
        tutorial.setId(resultSet.getInt("id"));
        tutorial.setTitle(resultSet.getString("title"));
        tutorial.setDescription(resultSet.getString("description"));
        tutorial.setPublished(resultSet.getBoolean("published"));
        return tutorial;
    }
}
```
- Create a generic interface for: DAO - data access object
```java
public interface DAO<T, U extends Number> {
    List<T> findAll();
    List<T> findAllVulnerable(String filter);
    void create(T t);
    Optional<T> findById(U id);
    void update(T t, U id);
    void delete(U id);
}
```
- Create TutorialDAO implementation class
```java
@Component
public class TutorialDAO implements DaoInterface<TutorialPOJO, Long> {

    private static final Logger log = LoggerFactory.getLogger(TutorialDAO.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TutorialDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TutorialPOJO> findAll() {
        String sql = "SELECT * from tutorials";
        return jdbcTemplate.query(sql, new TutorialRowMapper());
    }
    ...
    ...
```

### Service layer:
- Create tutorial service interface
```java
public interface TutorialServiceJdbcInterface {
    TutorialPOJO findById(long id);
    List<TutorialPOJO> findAll();
    void create(TutorialPOJO tutorial);
    void update(long id, TutorialPOJO tutorial);
    void delete(long id);
    List<TutorialPOJO> findAllVulnerable(String filter);
}
```
- Create tutorial service implementation:
```java
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
    ...
    ...
```

### REST Controller layer - REST APIs
- Create controller interface:
```java
public interface TutorialControllerJdbcInterface {
    @GetMapping
    List<TutorialPOJO> findAll();

    @GetMapping("/{id}")
    TutorialPOJO findById(@PathVariable long id);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //201
    void create(@Valid @RequestBody TutorialPOJO tutorial);
    ...
    ...
```

- Create controller implementation
```java
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
    ...
    ...
```

Create http file with some http requests:
```http request
###
GET http://{{host}}/api-jdbc/tutorials HTTP/1.1
Content-Type: application/json

###
GET http://{{host}}/api-jdbc/tutorials/1012 HTTP/1.1
Content-Type: application/json

### vulnerable endpoint: We can use sql injection to read more data:
GET http://{{host}}/api-jdbc/tutorials/vulnerable?filter="tutorial1" OR 1 = 1 HTTP/1.1
Content-Type: application/json

### vulnerable endpoint: We can use sql injection to delete records:
GET http://{{host}}/api-jdbc/tutorials/vulnerable?filter="tutorial1"; DELETE FROM tutorials HTTP/1.1
Content-Type: application/json

### vulnerable endpoint: We can use sql injection to run DDL statement - for example "DROP table tutorials":
GET http://{{host}}/api-jdbc/tutorials/vulnerable?filter="tutorial1"; DROP TABLE tutorials HTTP/1.1
Content-Type: application/json

###
POST http://{{host}}/api-jdbc/tutorials
Content-Type: application/json

{
  "title": "title1",
  "description": "description1",
  "link": "http://google.com"
}
```

- Test locally and deploy to Heroku.