package com.example.sudoswap.controller;

import com.example.sudoswap.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@RequestParam(name = "firstName") String firstName,
                                  @RequestParam(name = "lastName") String lastName,
                                  @RequestParam(name = "username") String username,
                                  @RequestParam(name = "password") String password,
                                  Model model) {
        try {
            userService.registerNewUser(firstName, lastName, username, password);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "register";
        }
        return "redirect:/login";
    }
}
