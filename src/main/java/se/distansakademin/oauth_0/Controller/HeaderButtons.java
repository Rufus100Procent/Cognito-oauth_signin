package se.distansakademin.oauth_0.Controller;

import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HeaderButtons {

    @GetMapping("/authh")
    public String getRequest(Principal User) {
        return "welcome";
    }
    @GetMapping("/")
    public String home(){
        return "redirect:/auth";
    }

    @GetMapping("/workouts")
    public String work(){
        return "workout/home";
    }

    @GetMapping("/details")
    public String workoutDetails(Model model) {
        return "workout/details";
    }
    @GetMapping("/features")
    public String showFeatures( User user) {
        return "info/features";
    }

    @GetMapping("/future")
    public String showFuture() {
        return "info/future";
    }

    @GetMapping("/history")
    public String showHistory() {
        return "info/history";
    }
    @GetMapping("/profile")
    public String settings(){
        return "setting";
    }
}
