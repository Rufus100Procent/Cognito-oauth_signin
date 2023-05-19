package se.distansakademin.oauth_0.services;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import se.distansakademin.oauth_0.models.Login;
import se.distansakademin.oauth_0.models.Registration;
import se.distansakademin.oauth_0.models.User;
import se.distansakademin.oauth_0.repositories.UserRepository;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Service
public class AuthService {
    @Autowired
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean registerUser(Registration registration, Model model) {
        if (!registration.passwordsMatch()) {
            model.addAttribute("error", "Passwords didn't match");
            return false;
        }

        if (userRepository.findByUsername(registration.getUsername()) != null) {
            model.addAttribute("error", "Username already exists");
            return false;
        }

        var user = new User(registration.getUsername(), registration.getPassword());
        userRepository.save(user);

        return true;
    }

    public void loginUser(Login login, HttpSession session, Model model) {
        User user = userRepository.findByUsername(login.getUsername());
        if (user != null && user.getPassword().equals(login.getPassword())) {
            session.setAttribute("user", user);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user, user.getPassword(), user.getAuthorities()
            );

            getContext().setAuthentication(authentication);
        } else {
            model.addAttribute("error", "Invalid credentials");
        }
    }

    public void handleGoogleLoginSuccess(OAuth2AuthenticationToken token) {
        var user = new User(token);
        var username = user.getUsername();

        if (userRepository.findByUsername(username) == null) {
            userRepository.save(user);
        }
    }
}