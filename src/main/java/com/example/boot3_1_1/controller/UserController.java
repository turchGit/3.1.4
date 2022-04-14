package com.example.boot3_1_1.controller;

import com.example.boot3_1_1.model.Role;
import com.example.boot3_1_1.model.User;
import com.example.boot3_1_1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "adminPage";
    }

    @GetMapping("user")
    public String getUser(Model model, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        model.addAttribute("user", currentUser);
        return "userPage";

    }

    @GetMapping("admin/adduser")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "newUser";
    }

    @PostMapping("admin/adduser")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String newUser(@ModelAttribute User user) {
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



    @PostMapping("admin/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(User user) {
        userService.removeUserById(user.getId());
        return "redirect:/admin";
    }

    @RequestMapping("admin/getUser/{id}")
    @ResponseBody
    public User getUser(@PathVariable Long id){
        System.out.println(userService.getUserById(id).getLogin());
        return userService.getUserById(id);
    }



    @PostMapping("admin/edit")
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
        }else {
            roles.remove(Role.ADMIN);
        }
        System.out.println(user.getRole());
        System.out.println(roles);
        user.setRoles(roles);
        userService.updateUser(user);
        return "redirect:/admin";
    }
}
