package se.distansakademin.oauth_0.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import se.distansakademin.oauth_0.models.Login;
import se.distansakademin.oauth_0.models.User;
import se.distansakademin.oauth_0.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public boolean checkIfUserExists(String username) {
        User user = userRepository.findByUsername(username);
        return user != null;
    }

    public void saveUser(User user) {

        if(user.getPassword() != null){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
        }

        userRepository.save(user);
    }

    public boolean authenticateUser(Login login) {
        User user = userRepository.findByUsername(login.getUsername());

        if (user != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.matches(login.getPassword(), user.getPassword());
        }

        return false;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByUsername(username);
    }
}

