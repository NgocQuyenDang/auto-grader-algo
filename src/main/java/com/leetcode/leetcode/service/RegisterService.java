package com.leetcode.leetcode.service;

import com.leetcode.leetcode.entity.User;
import com.leetcode.leetcode.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean register(String username, String password, String confirmPassword) {
        if (userRepository.findByName(username).isPresent()) {
            return false;
        }

        if (!password.equals(confirmPassword)) {
            return false;
        }

        String encodedPassword = bCryptPasswordEncoder.encode(password);
        userRepository.save(new User(username, encodedPassword));

        return true;
    }
}
