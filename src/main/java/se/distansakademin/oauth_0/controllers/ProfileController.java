package se.distansakademin.oauth_0.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {


    @GetMapping("/")
    public String showHomePage() {

        return "profile/home";
    }
}