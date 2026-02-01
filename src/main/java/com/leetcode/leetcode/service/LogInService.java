package com.leetcode.leetcode.service;

import com.leetcode.leetcode.entity.User;
import com.leetcode.leetcode.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service
public class LogInService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public boolean verify(String username, String password) {
        Optional<User> user = userRepository.findByName(username);
        if (user.isPresent() &&  passwordEncoder.matches(password, user.get().getPassword())) {
            return true;
        }
        return false;
    }
}
