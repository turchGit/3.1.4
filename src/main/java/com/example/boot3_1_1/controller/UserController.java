package com.example.boot3_1_1.controller;

import com.example.boot3_1_1.model.Role;
import com.example.boot3_1_1.model.User;
import com.example.boot3_1_1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("app")
    public String openApp(Model model,Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("user",user);
        if (user.getRoles().contains(Role.ADMIN)) {
            model.addAttribute("isAdmin", true);
        }
        model.addAttribute("userId",user.getId());

        return "appPage";
    }





}
