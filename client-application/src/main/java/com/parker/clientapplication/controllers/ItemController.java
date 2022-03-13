package com.parker.clientapplication.controllers;

import com.parker.clientapplication.models.Item;
import com.parker.clientapplication.services.ItemService;
import com.parker.clientapplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/items")
public class ItemController {
    @Autowired
    ItemService itemService;

    @Autowired
    UserService userService;

    @GetMapping
    public String displayItems(Authentication auth, Model model) {
        if (auth != null) {
            model.addAttribute("user", userService.getUser(auth.getName()));
        }
        model.addAttribute("items", itemService.getAllItems());
        return "item-list";
    }

    @GetMapping("/{id}")
    public String viewItem(@PathVariable(name="id") Long id, Authentication auth, Model model) {
        if (auth != null) {
            model.addAttribute("user", userService.getUser(auth.getName()));
        }
        model.addAttribute("items", itemService.getItemById(id));
        return "item-list";
    }

    @GetMapping("/new-item")
    public String displayNewItemForm(Authentication auth, Model model) {
        if (auth != null) {
            model.addAttribute("user", userService.getUser(auth.getName()));
        }
        model.addAttribute("item", new Item());
        return "new-item";
    }

    @PostMapping("/add")
    public String addNewItem(@ModelAttribute Item item, Authentication auth, Model model) {
        if (auth != null) {
            model.addAttribute("user", userService.getUser(auth.getName()));
        }
        model.addAttribute("items", itemService.newItem(item));
        return "item-list";
    }

    @RequestMapping("/item-form/{id}")
    public String displayUpdateItemForm(@PathVariable(name="id") Long id, Authentication auth, Model model) {
        if (auth != null) {
            model.addAttribute("user", userService.getUser(auth.getName()));
        }
        model.addAttribute("item", itemService.getItemById(id));
        return "item-form";
    }

    @RequestMapping("/update")
    public String updateItem(@ModelAttribute Item item, Authentication auth, Model model) {
        if (auth != null) {
            model.addAttribute("user", userService.getUser(auth.getName()));
        }
        model.addAttribute("items", itemService.updateItem(item));
        return "item-list";
    }

    @RequestMapping("/delete/{id}")
    public String deleteItem(@PathVariable(name="id") Long id, Authentication auth, Model model) {
        if (auth != null) {
            model.addAttribute("user", userService.getUser(auth.getName()));
        }
        model.addAttribute("items", itemService.deleteItem(id));
        return "item-list";
    }
}
