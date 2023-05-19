package se.distansakademin.oauth_0.controllers;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import se.distansakademin.oauth_0.models.Login;
import se.distansakademin.oauth_0.models.Registration;
import se.distansakademin.oauth_0.models.User;
import se.distansakademin.oauth_0.services.UserService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/signin")
    public String showRegistrationForm() {
        return "auth/signin";
    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute("registration") Registration registration, Model model) {

        if (!registration.passwordsMatch()) {
            model.addAttribute("error", "Passwords didn't match");
            return "auth/singup";
        }

        if (userService.checkIfUserExists(registration.getUsername())) {
            model.addAttribute("error", "Username already exists");
            return "auth/signup";
        }


        var user = new User(registration.getUsername(), registration.getPassword());

        userService.saveUser(user);

        var urlMessage = "";

        try {
            urlMessage = URLEncoder.encode("User created, please log in", StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/signin?success=" + urlMessage;
    }


    @GetMapping("/google-success")
    private String googleLoginSuccess(OAuth2AuthenticationToken token) {


        var user = new User(token);
        var username = user.getUsername();

        if(userService.checkIfUserExists(username)){
            userService.saveUser(user);
        }

        return "redirect:/";
    }

    @PostMapping("/signin")
    public String loginUser(@ModelAttribute("login") Login login, HttpSession session, Model model) {
        if (userService.authenticateUser(login)) {

            User user = userService.getUserByUsername(login.getUsername());
            session.setAttribute("user", user);

            Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return "redirect:/";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "auth/signin";
        }
    }

}