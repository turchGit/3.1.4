package com.example.boot3_1_1.controller;

import com.example.boot3_1_1.model.Role;
import com.example.boot3_1_1.model.User;
import com.example.boot3_1_1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class UserRestController {

    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("users/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addUser(@RequestBody User user) {
        if (userService.getUsers().stream().anyMatch(user1 -> user1.getLogin().equals(user.getLogin())) &&
                !userService.getUserById(user.getId()).getLogin().equals(user.getLogin())) {
            return "login is already taken";
        }
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        if (user.getRole() == Role.ADMIN) {
            roles.add(Role.ADMIN);
        } else {
            roles.remove(Role.ADMIN);
        }
        user.setRoles(roles);
        userService.addUser(user);
        return "success";
    }

    @DeleteMapping("users/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(@PathVariable Long id) {
        userService.removeUserById(id);
        return "success";
    }


    @PutMapping("users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateUser(@RequestBody User user) {
        if (userService.getUsers().stream().anyMatch(user1 -> user1.getLogin().equals(user.getLogin())) &&
                !userService.getUserById(user.getId()).getLogin().equals(user.getLogin())) {
            return "login is already taken";
        }
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        if (user.getRole() == Role.ADMIN) {
            roles.add(Role.ADMIN);
        } else {
            roles.remove(Role.ADMIN);
        }
        user.setRoles(roles);
        userService.updateUser(user);
        return "success";
    }
}
