package com.leetcode.leetcode.controller;

import com.leetcode.leetcode.entity.User;
import com.leetcode.leetcode.repository.UserRepository;
import com.leetcode.leetcode.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    // Receive the data from the form and process
    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password,
                                 @RequestParam String confirmPassword) {
        if (registerService.register(username, password, confirmPassword)) {
            return "redirect:/login?success=true";
        } else {
            return "redirect:/register?error=true";
        }
    }
}
