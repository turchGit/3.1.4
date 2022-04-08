package com.example.boot3_1_1.controller;

import com.example.boot3_1_1.model.Role;
import com.example.boot3_1_1.model.User;
import com.example.boot3_1_1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getUsers(Model model) {


        model.addAttribute("users", userService.getUsers());
        return "users";
    }

    @GetMapping("user/{id}")
    public String getUser(@PathVariable("id") Long id, Model model, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        if (!currentUser.getRoles().contains(Role.ADMIN) && currentUser.getId() != id) {
            return "redirect:/user/" + currentUser.getId();
        }
        model.addAttribute("user", userService.getUserById(id));
        return "user";

    }

    @GetMapping("admin/adduser")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "userForm";
    }

    @PostMapping("admin/adduser")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        user.setActive(true);
        if (userService.getUsers().stream().anyMatch(user1 -> user1.getLogin().equals(user.getLogin()))) {
            return "redirect:/admin";
        }

        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        if (user.getRole() == Role.ADMIN) {
            roles.add(Role.ADMIN);
        }
        user.setRoles(roles);

        userService.addUser(user);
        return "redirect:/admin";

    }

    @GetMapping("admin/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(@PathVariable("id") Long id,Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "userDeleteConfirmForm";
    }

    @PostMapping("admin/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.removeUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("admin/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));


        return "userUpdateForm";
    }

    @PostMapping("admin/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String confirmUserUpdate(@ModelAttribute("user") User user) {
        if (userService.getUsers().stream().anyMatch(user1 -> user1.getLogin().equals(user.getLogin()))&&
        !userService.getUserById(user.getId()).getLogin().equals(user.getLogin())) {
            return "redirect:/admin";
        }
        user.setActive(true);
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        if (user.getRole() == Role.ADMIN) {
            roles.add(Role.ADMIN);
        }
        user.setRoles(roles);
        userService.updateUser(user);
        return "redirect:/admin";
    }
}
