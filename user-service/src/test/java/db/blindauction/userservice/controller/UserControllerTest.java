package db.blindauction.userservice.controller;

import db.blindauction.userservice.model.User;
import db.blindauction.userservice.sevice.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService; // Mock the service

    @Test
    void testGetUser_Found() throws Exception {
        // Arrange
        String token = "token1";
        User user = new User();
        user.setName("Alfa");
        user.setToken(token);

        when(userService.getUser(token)).thenReturn(Optional.of(user));

        // Act & Assert
        mockMvc.perform(get("/api/users/{token}", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Alfa"))
                .andExpect(jsonPath("$.token").value("token1"));
    }

    @Test
    void testGetUser_NotFound() throws Exception {
        // Arrange
        String token = "invalid";

        when(userService.getUser(token)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/users/{token}", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
