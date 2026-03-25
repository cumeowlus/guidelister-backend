package com.rpeyrichoux.guidelisterAPI.view;

import com.rpeyrichoux.guidelisterAPI.model.User;
import com.rpeyrichoux.guidelisterAPI.model.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserView {
    private final UserRepository userRepo;

    public UserView(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    public User addUser(User user) {
        user.setAdmin(false);
        return userRepo.save(user);
    }

    public User addAdmin(User user) {
        user.setAdmin(true);
        return userRepo.save(user);
    }
}
