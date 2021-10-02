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
- JDBC-Communication

## Mapping the database

### Map the tables that do not have any foreign keys

Generate entity classes of these tables from the database into entities package:
- shippers as Shipper
- order_statuses as OrderStatus
- products as Product
- payment_methods as PaymentMethod
- customers as Customer

Add this annotation to the IDs:
```java
@GeneratedValue(strategy = GenerationType.IDENTITY)
```





