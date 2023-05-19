package se.distansakademin.oauth_0.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import se.distansakademin.oauth_0.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Public routes, if you need to add a new "regular" sits then do it here.
        String[] publicRoutes = {
                "/", "/signin*", "/signup*", "/login**",
                "/logout*", "/features", "/future",
                "/history", "/public*", "/oauth/**",
                "/google-success", "/error"
        };


        http.authorizeRequests((authorize) -> authorize

                // Everyone has access to /resources) (images, css, etc)
                .requestMatchers("/resources/**").permitAll()

                // Authenticated (logged in) users have access to workouts
                .requestMatchers("/workouts/**").authenticated()

                .requestMatchers("/profile/**").authenticated()

                // Authenticated (logged in) users have access to workouts
                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")

                // All routes from publicRoutes-array are public
                .requestMatchers(publicRoutes).permitAll()

                // All other pages are forbidden
                .anyRequest().denyAll()
        );

        http.authenticationManager(customAuthenticationManager(http));

        http.oauth2Login()
                .defaultSuccessUrl("/google-success")
                .permitAll();

        http.formLogin()
                .loginPage("/signin")
                .loginProcessingUrl("/signin")
                .defaultSuccessUrl("/", true)
                .failureUrl("/signin?error=true");

        return http.build();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager(HttpSecurity http) throws Exception {

        var authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder());

        return authenticationManagerBuilder.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
