package com.rpeyrichoux.guidelisterAPI.controller;

import com.rpeyrichoux.guidelisterAPI.model.User;
import com.rpeyrichoux.guidelisterAPI.view.UserView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserView UserView;

    public UserController(UserView UserView) {
        this.UserView = UserView;
    }

    @GetMapping
    public List<User> getUsers() {
        return UserView.getUsers();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        UserView.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User created = UserView.addUser(user);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/admin")
    public ResponseEntity<User> addAdmin(@RequestBody User user) {
        User created = UserView.addAdmin(user);
        return ResponseEntity.ok(created);
    }
}
