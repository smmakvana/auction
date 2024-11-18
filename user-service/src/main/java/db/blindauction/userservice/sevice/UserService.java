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


}
