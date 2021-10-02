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

- Create repositories extending JpaRepository.

- Test if DATA-REST dependency generates all the APIs based on the repositories:
in the browser run url: http://localhost:5552/profile
  
You should see something like this:
```json
{
  "_links": {
    "self": {
      "href": "http://localhost:5552/profile"
    },
    "users": {
      "href": "http://localhost:5552/profile/users"
    },
    "orderStatuses": {
      "href": "http://localhost:5552/profile/orderStatuses"
    },
    "roles": {
      "href": "http://localhost:5552/profile/roles"
    },
    "products": {
      "href": "http://localhost:5552/profile/products"
    },
    "shippers": {
      "href": "http://localhost:5552/profile/shippers"
    },
    "tutorials": {
      "href": "http://localhost:5552/profile/tutorials"
    },
    "paymentMethods": {
      "href": "http://localhost:5552/profile/paymentMethods"
    },
    "customers": {
      "href": "http://localhost:5552/profile/customers"
    }
  }
}
```



