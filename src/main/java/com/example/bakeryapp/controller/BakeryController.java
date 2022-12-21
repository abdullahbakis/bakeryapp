package com.example.bakeryapp.controller;

import com.example.bakeryapp.entity.Bakery;
import com.example.bakeryapp.entity.User;
import com.example.bakeryapp.service.BakeryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class BakeryController {

    private static final Logger logger = LoggerFactory.getLogger(BakeryController.class);
    @Autowired
    private BakeryService bakeryService;

    @GetMapping("/")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        // check the username and password
        boolean loginSuccess = bakeryService.login(username, password);
        if (loginSuccess) {
            // log the user in and redirect to the bakeries page
            return "redirect:/bakeries";
        } else {
            // login failed, display an error message and stay on the login page
            model.addAttribute("errorMessage", "Invalid username or password");
            return "login";
        }
    }
    private boolean authenticate(String username, String password) {
        // perform the authentication and return a boolean result
        return true;
    }


    @GetMapping("/bakeries")
    public String getAllBakeries(Model model) {
        List<Bakery> bakeries = bakeryService.getAllBakeries();
        model.addAttribute("bakeries", bakeries);
        return "bakeries";
    }

    @GetMapping("/bakery/{id}")
    public String getBakeryById(@PathVariable Long id, Model model) {
        Bakery bakery = bakeryService.getBakeryById(id);
        if (bakery == null) {
            // bakery object is null, return an error view
            return "error";
        }
        model.addAttribute("bakery", bakery);
        return "bakery";
    }


    // additional methods for create, update, and delete bakery items


    @PostMapping("/bakeries")
    public String createBakery(@Valid Bakery bakery, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "bakeries/create";
        }
        // save the bakery
        return "redirect:/bakeries";
    }


    @PostMapping("/bakeries/{id}")
    public String updateBakery(@PathVariable Long id, @Valid Bakery bakery, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // handle validation errors
            return "bakery";
        }

        // log the bakery object before updating
        logger.info("Updating bakery: {}", bakery);

        bakeryService.updateBakery(bakery);

        // log the bakery object after updating
        logger.info("Updated bakery: {}", bakery);

        return "redirect:/bakeries";
    }
}
