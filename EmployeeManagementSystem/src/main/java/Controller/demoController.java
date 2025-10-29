package Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class demoController {

    @GetMapping("/public/hello")
    public String publicHello() {
        return "Hello, Public! No login needed.";
    }

    @GetMapping("/private/hello")
    public String privateHello() {
        return "Hello, Authenticated User!";
    }
}//end of class
