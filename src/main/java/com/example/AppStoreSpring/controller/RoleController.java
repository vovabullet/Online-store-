package com.example.AppStoreSpring.controller;

import com.example.AppStoreSpring.model.User;
import com.example.AppStoreSpring.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RoleController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        if (userService.usernameExists(user.getUsername())) {
            result.rejectValue("username", "error.user", "Username is already taken");
            return "register";
        }
        userService.saveWithDefaultRole(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

    @GetMapping("/admin/home")
    public String adminHome() {
        return "admin/adminHome";
    }

    @GetMapping("/storekeeper/home")
    public String storekeeperHome() {
        return "storekeeper/storekeeperHome";
    }

    @GetMapping("/user/home")
    public String userHome() {
        return "user/userHome";
    }
}
