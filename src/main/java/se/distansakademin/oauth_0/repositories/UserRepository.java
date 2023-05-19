package se.distansakademin.oauth_0.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import se.distansakademin.oauth_0.models.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    public User findByUsername(String username);

    @Override
    public User save(User user);
}
