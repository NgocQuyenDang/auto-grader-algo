package com.leetcode.leetcode.controller;

import com.leetcode.leetcode.service.LogInService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LogInController {
    @Autowired
    LogInService logInService;

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String checkLogIn(@RequestParam String username, @RequestParam String password, HttpSession session) {
        if (logInService.verify(username, password)) {
            session.setAttribute("user", username);
            return "redirect:/home";
        } else {
            return "redirect:/login?error=true";
        }
    }
}
