# demo web shop server

## Spring Security

create a new package: security, 

### mapping the tables
map the special tables for it - we have them in the database:
- users
- roles

Generate the mapping classes using Persistence / Generate persistence mapping / By database schema

only map users as User and roles as Role... so you dont have relationship.

Add this to id:
```java
@GeneratedValue(strategy = GenerationType.IDENTITY)
```

Now manually add the relationship - in the User class:
```java
    private Set<Role> roles = new HashSet<>(); //this was added manually
    
    //this was added manually
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
        return roles;
    }
```

Like this we made the connection using the jon table user_roles.

We want to modify one more thing: Role.name is a String type but we want it to be ENUM - to only allow specific values.

Create ENUM ERole
```java
public enum ERole {
	ROLE_CUSTOMER,
    ROLE_EMPLOYEE,
    ROLE_ADMIN
}
```

Now modify Role class: Change String to ERole:
```java
    private ERole name;

    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false, length = 20)
    public ERole getName() {
        return name;
    }
```




### create repositories
- RoleRepository
- UserRepository

```java
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(String name);
}

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
}
```

Now you can use your DATA-REST dependency to check if the mapping works. You dont need to create services and controllers yet.
DATA-REST uses the repositories to generate the APIs.
Open this: http://localhost:5552/users

```json
{
  "_embedded": {
    "users": [
      {
        "email": "pesekt@gmail.com",
        "password": "$2a$10$SO5VnukziSsvLghe62jd5uyNFmfpKInZtzR58mWRbGZCZpGW9ao5u",
        "username": "pesekt",
        "_links": {
          "self": {
            "href": "http://localhost:5552/users/1"
          },
          "user": {
            "href": "http://localhost:5552/users/1"
          },
          "roles": {
            "href": "http://localhost:5552/users/1/roles"
          }
        }
      },
```

check roles on user 3: http://localhost:5552/users/3/roles

```json
  "_embedded": {
    "roles": [
      {
        "name": "ROLE_EMPLOYEE",
        "_links": {
          "self": {
            "href": "http://localhost:5552/roles/3"
          },
          "role": {
            "href": "http://localhost:5552/roles/3"
          }
        }
      },
      {
        "name": "ROLE_CUSTOMER",
        "_links": {
          "self": {
            "href": "http://localhost:5552/roles/1"
          },
          "role": {
            "href": "http://localhost:5552/roles/1"
          }
        }
      }
```

It works...

### maven dependency
Add maven dependency:
```xml
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
```

### services
we need to implement 2 interfaces coming from spring security:
- UserDetails
- UserDetailsService

check out my code...

### maven dependency for JWT - json web token

```xml
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt</artifactId>
            <version>0.9.1</version>
        </dependency>
```

### application.properties
Add these properties to application.properties:
```
################ app properties ################################################
#
# secret key is for password encryption - registration / login features
app.secretkey = ${SECRET_KEY}

# JWT(Json Web Token) expiration time: 86400000ms = 24h
app.jwtExpirationMs= 86400000
```
set up SECRET_KEY in your environment variables - some string

### extra classes needed
Now we need many different classes - they are in packages:
- jwt
- payload

### Config
We need some configuration classes:
package config:
- WebSecurityConfig

```java
...
...
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests().antMatchers("/api/auth/**").permitAll()
			.antMatchers("/api/test/**","/extra/**").permitAll()
			//.antMatchers("/**").permitAll() //disabling the spring authentication
			.anyRequest().authenticated();

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
```
- We give permitAll to api/auth... and to api/test and /extra endpoints - no authentication needed.
- If you want to deactivate the spring security, use .antMatchers("/**").permitAll() , this means permitAll on all endpoints.


### Controllers
- AuthController:
  - registration - register new user
  - login - login with credentials, if successful, it provides JWT to the client
- TestController - just for testing

- Test locally, 
- then deploy to heroku and test there: you need to create SECRET_KEY variable in github secrets and also in heroku config vars. And in maven.yml:
```yaml
      env:
        DATABASE_URL: ${{secrets.DATABASE_URL}}
        SECRET_KEY: ${{secrets.SECRET_KEY}}
```

### http file to test signup and login:
You can do the same with postman but this is for IntelliJ.

register a user:
```http request
### registration of a new user -> users table
POST http://{{host}}/api/auth/signup
Content-Type: application/json

{
  "username": "pesekt100",
  "email": "pesekt100@gmail.com",
  "password": "12345678"
}
```

login with this user:
```java

### login with credentials, we get a JWT and save it in client.global - session variable auth_token
POST http://{{host}}/api/auth/signin
Content-Type: application/json

{
  "username": "pesekt",
  "password": "12345678"
}

> {% client.global.set("auth_token", response.body.accessToken);
client.test("Request executed successfully", function() {
  client.assert(response.status === 200, "Response status is not 200");
});
client.test("JWT acquired successfully", function() {
  client.assert(response.body.accessToken != null, "JWT not acquired");
});
%}
```

try tutorials endpoint, but now as a logged-in user, we provide the jwt - json web token:

```java
### request with JWT authorizaton, we need to login and then use the JWT
GET http://{{host}}//api/tutorials HTTP/1.1
Authorization: Bearer {{auth_token}}

> {%
client.test("Request executed successfully", function() {
  client.assert(response.status === 200, "Response status is not 200");
});
%}
```

### set roles for endpoints:

Use @PreAuthorize - you can use it for the whole controller class or for each method:
https://www.baeldung.com/spring-security-method-security

```java
@PreAuthorize("hasRole('ROLE_ADMIN')")

@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE')")
```

Now create accounts for customer, employee and admin and test that it works.