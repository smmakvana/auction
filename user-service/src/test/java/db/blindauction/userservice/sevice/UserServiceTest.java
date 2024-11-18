package db.blindauction.userservice.sevice;

import db.blindauction.userservice.model.User;
import db.blindauction.userservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetUserValidToken() {
        MockitoAnnotations.openMocks(this);
        User mockUser = new User();
        mockUser.setName("Alfa");
        mockUser.setToken("token1");
        when(userRepository.findByToken("token1")).thenReturn(Optional.of(mockUser));
        Optional<User> user = userService.getUser("token1");
        assertTrue(user.isPresent());
        assertTrue(user.get().getName().equals("Alfa"));
    }

    @Test
    void testGetUserInvalidToken() {
        MockitoAnnotations.openMocks(this);
        Optional<User> user = userService.getUser("token2");
        assertFalse(user.isPresent());
    }
}
