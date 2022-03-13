package com.parker.clientapplication.controllers;

import com.parker.clientapplication.exceptions.NoSuchCartException;
import com.parker.clientapplication.models.Item;
import com.parker.clientapplication.services.CartService;
import com.parker.clientapplication.models.security.User;
import com.parker.clientapplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CartController {
    @Autowired
    CartService cartService;

    @Autowired
    UserService userService;

    @GetMapping("/cart")
    public String displayCart(Authentication auth, Model model) {
        try {
            User user = userService.getUser(auth.getName());
            model.addAttribute("cart", cartService.getCartByUserId(user.getId()));
            model.addAttribute("user", user);
            return "cart-list";
        } catch (NoSuchCartException e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }

    @RequestMapping("/cart-add/{itemId}")
    public String addItemToCart(@PathVariable(name="itemId")Long itemId,  Authentication auth, Model model) {
        try {
            User user = userService.getUser(auth.getName());
            cartService.addItemToCart(new Item(itemId, user.getId()));
            return "redirect:/cart";
        } catch (NoSuchCartException e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }

    @RequestMapping("/cart-remove/{cartItemId}")
    public String removeItemFromCart(@PathVariable(name="cartItemId") Long cartItemId,
                                     Authentication auth, Model model) {
        try {
            User user = userService.getUser(auth.getName());
            model.addAttribute("cart", cartService.removeItemFromCart(user.getId(), cartItemId));
            model.addAttribute("user", user);
            return "cart-list";
        } catch (NoSuchCartException e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }

    @RequestMapping("/cart-update/{cartItemId}/{amount}")
    public String updateItemAmountInCart(@PathVariable(name="cartItemId") Long cartItemId,
                                         @PathVariable(name="amount") Integer amount,
                                         Authentication auth, Model model) {
        try {
            User user = userService.getUser(auth.getName());
            cartService.updateItemAmount(user.getId(), cartItemId, amount);
            model.addAttribute("cart", cartService.getCartByUserId(user.getId()));
            model.addAttribute("user", user);
            return "cart-list";
        } catch (NoSuchCartException e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }

}
