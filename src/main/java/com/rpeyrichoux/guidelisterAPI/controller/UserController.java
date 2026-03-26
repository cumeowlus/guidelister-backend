package com.rpeyrichoux.guidelisterAPI.controller;

import com.rpeyrichoux.guidelisterAPI.model.User;
import com.rpeyrichoux.guidelisterAPI.view.UserView;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserView userView;

    public UserController(UserView userView) {
        this.userView = userView;
    }

    @GetMapping(produces = "application/json")
    public List<User> getUsers() {
        return userView.getUsers();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userView.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
