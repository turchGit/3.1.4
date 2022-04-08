package com.example.boot3_1_1.service;

import com.example.boot3_1_1.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> getUsers();

    User getUserById(Long id);

    void removeUserById(Long id);

    void addUser(User user);

    void updateUser(User user);
}
