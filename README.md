# demo web shop server

## custom queries

In the repository, we can write custom queries:

Examples:

```java
	//JPQL - Java Persistence Query Language
	@Query("SELECT t FROM Tutorial t WHERE t.published = true")
	List<Tutorial> findAllPublished();

	//native query
	@Query(
			value = "SELECT * FROM Tutorials t WHERE t.published = true ",
			nativeQuery = true) // this enables native SQL
	List<Tutorial> findAllPublishedNative();

	//indexed parameters in JPQL:
	@Query("SELECT t FROM Tutorial t WHERE t.published = ?1 and t.id > ?2")
	List<Tutorial> findPublishedWithHigherId(Boolean published, Long id);

	//named parameters in JPQL:
	@RestResource(
			path = "findByPublishedNamedParams",
			rel = "findByPublishedNamedParams")
	@Query("SELECT t FROM Tutorial t WHERE t.published = :published and t.id > :id ")
	List<Tutorial> findPublishedWithHigherId(
			@Param("id") Long id,
			@Param("published") Boolean published);

	//TODO: Does not work, it needs @EnableTransactionManagement on the datasource
	//modifying query
	@Modifying
	@Transactional
	@Query("UPDATE Tutorial t SET t.published = true WHERE t.id = :id")
	void publishById(@Param("id") Long id);
```

To easily test it we can use DATA-REST APIs... we dont need to create a controller for each endpoint.

We can also use httpClient in IntelliJ: in http file:
```http request
### APIs coming from DATA-REST using the methods in TutorialsRepository
GET http://{{host}}/tutorials/search HTTP/1.1
Content-Type: application/json
```

We should see all the endpoints generated from the repository methods:

```
// http://localhost:5552/tutorials/search

{
  "_links": {
    "publishById": {
      "href": "http://localhost:5552/tutorials/search/publishById{?id}",
      "templated": true
    },
    "findPublishedWithHigherId": {
      "href": "http://localhost:5552/tutorials/search/findPublishedWithHigherId{?published,id}",
      "templated": true
    },
    "findAllPublished": {
      "href": "http://localhost:5552/tutorials/search/findAllPublished"
    },
    "findByPublishedPaged": {
      "href": "http://localhost:5552/tutorials/search/findByPublishedPaged{?published,page,size,sort}",
      "templated": true
    },
    "findAllPublishedNative": {
      "href": "http://localhost:5552/tutorials/search/findAllPublishedNative"
    },
```

