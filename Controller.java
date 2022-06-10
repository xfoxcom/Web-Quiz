package engine;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/api/quiz")
    public Quiz getQ () {
        return new Quiz("The Java Logo", "What is depicted on the Java logo?", new String[]{"Robot","Tea leaf","Cup of coffee","Bug"});
    }
    @PostMapping("api/quiz")
    public Response postQ (@RequestParam int answer) {
if (answer == 2) {
    return new Response(true, "Congratulations, you're right!");
} else return new Response(false, "Wrong answer! Please, try again.");
    }
}
