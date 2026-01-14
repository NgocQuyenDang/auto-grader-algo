package com.leetcode.leetcode.service;

import com.leetcode.leetcode.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class LogInService {
    @Autowired
    UserRepository userRepository;

    public boolean verify(String username, String password) {
        if (!userRepository.findByName(username).isPresent()) {
            return false;
        }
        if (!userRepository.findByName(username).get().getPassword().equals(password)) {
            return false;
        }
        return true;
    }
}
