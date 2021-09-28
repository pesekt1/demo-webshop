package swc3.demowebshop.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//controller used in the beginning of the project to make sure it works.
@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public String hello(@RequestParam(required = false, value = "name", defaultValue = "World") String name) {
        return String.format("Hello, %s!", name);
    }
}
