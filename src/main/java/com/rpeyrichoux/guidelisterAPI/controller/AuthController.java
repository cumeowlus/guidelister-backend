package com.rpeyrichoux.guidelisterAPI.controller;

import com.rpeyrichoux.guidelisterAPI.model.User;
import com.rpeyrichoux.guidelisterAPI.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    public static final String SESSION_USER_ID = "USER_ID";
    public static final String SESSION_IS_ADMIN = "IS_ADMIN";

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body, HttpSession session) {
        String email = body.get("email");
        String password = body.get("password");
        if (email == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "email and password required"));
        }
        return authService.authenticate(email, password)
                .map(user -> {
                    session.setAttribute(SESSION_USER_ID, user.getId());
                    session.setAttribute(SESSION_IS_ADMIN, user.isAdmin());
                    return ResponseEntity.ok(Map.of(
                            "id", user.getId(),
                            "email", user.getEmail(),
                            "isAdmin", user.isAdmin()
                    ));
                })
                .orElseGet(() -> ResponseEntity.status(401).body(Map.of("error", "invalid credentials")));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        authService.logout(session);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/me", produces = "application/json")
    public ResponseEntity<?> me(HttpSession session) {
        Object id = session.getAttribute(SESSION_USER_ID);
        Object admin = session.getAttribute(SESSION_IS_ADMIN);
        if (id == null) return ResponseEntity.status(401).body(Map.of("error", "not authenticated"));
        return ResponseEntity.ok(Map.of("id", id, "isAdmin", admin));
    }

    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> body, HttpSession session) {
        String email = body.get("email");
        String password = body.get("password");
        if (email == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "email and password required"));
        }
        User created = authService.register(email, password, false);
        // Optionally log the user in immediately:
        session.setAttribute(SESSION_USER_ID, created.getId());
        session.setAttribute(SESSION_IS_ADMIN, created.isAdmin());
        return ResponseEntity.ok(Map.of("id", created.getId(), "email", created.getEmail(), "isAdmin", created.isAdmin()));
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<?> registerAdmin(@RequestBody Map<String, String> body, HttpSession session) {
        String email = body.get("email");
        String password = body.get("password");
        if (email == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "email and password required"));
        }
        User created = authService.register(email, password, true);
        // Optionally log the admin in immediately:
        session.setAttribute(SESSION_USER_ID, created.getId());
        session.setAttribute(SESSION_IS_ADMIN, created.isAdmin());
        return ResponseEntity.ok(Map.of("id", created.getId(), "email", created.getEmail(), "isAdmin", created.isAdmin()));
    }
}
