package db.blindauction.userservice.sevice;

import db.blindauction.userservice.model.User;
import db.blindauction.userservice.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUser(String token) {
        return userRepository.findByToken(token);
    }

    @PostConstruct
    @Transactional
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

            User user4 = new User();
            user4.setName("Delta");
            user4.setToken("token4");

            User user5 = new User();
            user5.setName("Hexa");
            user5.setToken("token5");

            User user6 = new User();
            user6.setName("Theta");
            user6.setToken("token6");

            User user7 = new User();
            user7.setName("Eta");
            user7.setToken("token7");

            User user8 = new User();
            user8.setName("Zeta");
            user8.setToken("token8");

            User user9 = new User();
            user9.setName("Iota");
            user9.setToken("token9");

            User user10 = new User();
            user10.setName("Kappa");
            user10.setToken("token10");

            User user11 = new User();
            user11.setName("Lambda");
            user11.setToken("token11");

            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);
            userRepository.save(user5);
            userRepository.save(user6);
            userRepository.save(user7);
            userRepository.save(user8);
            userRepository.save(user9);
            userRepository.save(user10);
            userRepository.save(user11);
        }
    }
}
