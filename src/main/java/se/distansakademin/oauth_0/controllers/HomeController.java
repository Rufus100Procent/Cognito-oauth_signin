package se.distansakademin.oauth_0.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.distansakademin.oauth_0.models.User;

@Controller
public class HomeController {


    @GetMapping("/")
    public String showHomePage() {
        return "home/home";
    }

    @GetMapping("/features")
    public String showFeatures(@AuthenticationPrincipal User user) {
        return "home/features";
    }

    @GetMapping("/future")
    public String showFuture() {
        return "home/future";
    }

    @GetMapping("/history")
    public String showHistory() {
        return "home/history";
    }
}