package com.rpeyrichoux.guidelisterAPI.view;

import com.rpeyrichoux.guidelisterAPI.model.User;
import com.rpeyrichoux.guidelisterAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserView {
    private final UserRepository userRepo;

    @Autowired
    public UserView(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }
}
