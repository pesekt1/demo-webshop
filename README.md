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


UserDetailsImpl:
```java
public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;
	private final Long id;
	private final String username;
	private final String email;

	@JsonIgnore
	private final String password;

	private final Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Long id, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());

		return new UserDetailsImpl(
				user.getId(), 
				user.getUsername(), 
				user.getEmail(),
				user.getPassword(), 
				authorities);
	}
```

UserDetailsServiceImpl:
```java
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}
}
```

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
Now we need a few classes - they are in packages:
- jwt:
  
JwtUtils:
```java
@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${app.secretkey}")
	private String jwtSecret;

	@Value("${app.jwtExpirationMs}")
	private int jwtExpirationMs;

	public String generateJwtToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		//here we can build the JWT structure
		return Jwts.builder()
				//.setSubject((userPrincipal.getUsername() + ' ' +  userPrincipal.getEmail()))
				.setSubject((userPrincipal.getUsername()))
				.setId(userPrincipal.getId().toString())
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}
```

AuthTokenFilter

```java
public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = parseJwt(request);
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				String username = jwtUtils.getUserNameFromJwtToken(jwt);

				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			logger.error("Cannot set user authentication: {_}",e);
		}

		filterChain.doFilter(request, response);
	}

	private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7);
		}

		return null;
	}
}
```

AuthEntryPoint
```java
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
		logger.error("Unauthorized error: {}", authException.getMessage());
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
	}

}
```

- payload
  - request
      - LoginRequest
      - SignupRequest
  - response
      - JwtResponse
      - MessageResponse
  
... look in the source code.
        

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