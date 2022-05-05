package com.example.boot3_1_1.controller;

import com.example.boot3_1_1.model.Role;
import com.example.boot3_1_1.model.User;
import com.example.boot3_1_1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;


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
    public User addUser(@RequestBody User user) {

        return userService.addUser(user);
    }

    @DeleteMapping("users/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(@PathVariable Long id) {
        userService.removeUserById(id);
        return "success";
    }


    @PutMapping("users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User updateUser(@RequestBody User user) {

        return userService.updateUser(user);
    }
}
