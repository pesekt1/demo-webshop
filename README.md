# demo web shop server

## CORS

To allow web clients to communicate with our web server via http protocol, we need to implement WebMvcConfigurer:

We need to override method addCorsMappings.

allowedOrigins will be the urls from where http requests are accepted.


```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins(
                        "http://localhost:8081",
                        "https://swc3-react-frontend.herokuapp.com")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH")
                .allowCredentials(true)
                .maxAge(3600); //1 hour
    }
```