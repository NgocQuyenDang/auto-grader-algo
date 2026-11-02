package com.leetcode.leetcode.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.leetcode.leetcode.repository.UserRepository;
import com.leetcode.leetcode.entity.User;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Tắt để tránh lỗi khi nộp bài/đăng ký
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register").permitAll()
                        .anyRequest().permitAll());
        return http.build();
    }

    @Bean
public CommandLineRunner initAdmin(
        UserRepository userRepository,
        BCryptPasswordEncoder encoder,
        @Value("${app.admin.password}") String adminPassword) {
    return args -> {
        if (userRepository.findByName("admin").isEmpty()) {
            
            if (adminPassword == null || adminPassword.isBlank()) {
                System.out.println(">>> Chưa cấu hình ADMIN_PASSWORD, bỏ qua tạo admin.");
                return;
            }
            User admin = new User("admin", encoder.encode(adminPassword), User.ROLE_ADMIN);
            userRepository.save(admin);
            System.out.println(">>> Đã tạo admin từ biến môi trường thành công.");
        }
    };
}