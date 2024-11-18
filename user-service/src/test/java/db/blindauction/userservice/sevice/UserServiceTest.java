package db.blindauction.userservice.sevice;

import db.blindauction.userservice.model.User;
import db.blindauction.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceIntegrationTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    void testInitialiseFewUsers() {
        assertEquals(3, userRepository.count());
    }
    @Test
    void testGetUser() {
        String validToken = "token1";
        Optional<User> user = userService.getUser(validToken);
        assertTrue(user.isPresent());
        assertEquals("Alfa", user.get().getName());
    }
    @Test
    void testGetUser_NotFound() {
        String invalidToken = "invalid";
        Optional<User> user = userService.getUser(invalidToken);
        assertFalse(user.isPresent());
    }
}
