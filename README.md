# demo web shop server

## profiles

We can set up profiles in Maven pom.xml:

```xml
    <profiles>
        <profile>
            <id>dev</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <spring.profiles.active>dev</spring.profiles.active>
            </properties>
        </profile>
        <profile>
            <id>prod</id>
            <properties>
                <spring.profiles.active>prod</spring.profiles.active>
            </properties>
        </profile>
    </profiles>
```

Now there is a naming convention for application properties:

we can have following:

- application.properties
- application-dev.properties
- application-prod.properties

now you could set up this for production:
```
#MySQL properties
spring.jpa.hibernate.ddl-auto=validate
```
and for development it could be:
```
#MySQL properties
spring.jpa.hibernate.ddl-auto=update
```

Update the maven.yml file:
```yml
      run: mvn -B package -Pprod --file pom.xml
```

We tell maven to use prod profile.

In heroku we need to create config var for the profiles to tell it to use prod profile:

heroku / settings / config vars:

create new key - value pair:

SPRING_PROFILES_ACTIVE = prod

more info here:
https://docs.spring.io/spring-boot/docs/2.3.x/reference/html/spring-boot-features.html#boot-features-profiles