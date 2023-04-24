package octopus.basic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeleafController {
    
    @GetMapping("/thymeleaf")
    public String hello() {
        return "thymeleaf";
    }
    
}