package com.parker.clientapplication.controllers;

import com.parker.clientapplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public String viewHomePage(Authentication auth, Model model) {
        if (auth != null) {
            model.addAttribute("user", userService.getUser(auth.getName()));
        }
        return "home";
    }
}
