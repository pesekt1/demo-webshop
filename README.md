# demo web shop server

## Add DATA-REST dependency: 

pom.xml:

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-rest</artifactId>
        </dependency>

now if you run the root api: like http://localhost:5552/ you get endpoints generated based on the repositories, not your controllers

## Add swagger api docu:
pom.xml:

        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-data-rest</artifactId>
            <version>1.2.32</version>
        </dependency>
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-ui</artifactId>
            <version>1.2.32</version>
        </dependency>

application.properties:

```
# custom path for swagger-ui documentation
springdoc.swagger-ui.path=/swagger
springdoc.swagger-ui.operationsSorter=method

# custom path for api documentation as JSON object
springdoc.api-docs.path=/api-docs
```

now run http://localhost:5552/swagger and it should work
