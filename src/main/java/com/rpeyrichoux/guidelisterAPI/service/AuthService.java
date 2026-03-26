package com.rpeyrichoux.guidelisterAPI.service;

import com.rpeyrichoux.guidelisterAPI.model.User;
import com.rpeyrichoux.guidelisterAPI.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    // Register: hashes password and saves user
    public User register(String email, String plainPassword, boolean asAdmin) {
        String hash = encoder.encode(plainPassword);
        User u = new User();
        u.setEmail(email);
        u.setPasswordHashed(hash);
        u.setAdmin(asAdmin);
        return userRepo.save(u);
    }

    // Authenticate: returns User if credentials valid
    public Optional<User> authenticate(String email, String plainPassword) {
        Optional<User> opt = userRepo.findByEmail(email);
        if (opt.isPresent()) {
            User u = opt.get();
            if (encoder.matches(plainPassword, u.getPassword())) {
                return Optional.of(u);
            }
        }
        return Optional.empty();
    }

    public void logout(HttpSession session) {
        if (session != null) session.invalidate();
    }
}
