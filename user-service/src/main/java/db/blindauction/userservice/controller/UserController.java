package db.blindauction.userservice.controller;

import db.blindauction.userservice.model.User;
import db.blindauction.userservice.sevice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("{token}")
    public ResponseEntity<User> getUser(@PathVariable String token){
        return userService.getUser(token).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}
