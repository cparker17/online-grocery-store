package com.parker.clientapplication.controllers;

import com.parker.clientapplication.models.Cart;
import com.parker.clientapplication.models.security.User;
import com.parker.clientapplication.services.CartService;
import com.parker.clientapplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SecurityController {
    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @GetMapping("/register")
    public String viewRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/new-user")
    public String registerAccount(@ModelAttribute(name="user") User user) {
        user = userService.registerAccountUser(user);
        Cart cart = new Cart();
        cart.setUserId(user.getId());
        user.setCart(cartService.saveCart(cart));
        userService.updateUser(user);
        return "register-success";
    }

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping("/sign-in")
    public String viewSignInPage(Model model) {
        model.addAttribute("securityUser", new User());
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("message", "Invalid username and password.");
        return "login-error";
    }
}
