package db.blindauction.userservice.sevice;

import db.blindauction.userservice.model.User;
import db.blindauction.userservice.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUser(String token) {
        return userRepository.findByToken(token);
    }

    @PostConstruct
    public void initialiseFewUsers() {
        if (userRepository.count() == 0) {
            User user1 = new User();
            user1.setName("Alfa");
            user1.setToken("token1");

            User user2 = new User();
            user2.setName("Beta");
            user2.setToken("token2");

            User user3 = new User();
            user3.setName("Gamma");
            user3.setToken("token3");

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
        }
    }
}
