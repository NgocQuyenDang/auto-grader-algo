package com.leetcode.leetcode;

import com.leetcode.leetcode.service.JudgeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class JudgeServiceTest {

    @Autowired
    private JudgeService judgeService;

    @Test
    void debugSpecificTestcase() {
        String code = "public class Main { public static void main(String[] args) { while(true); } }"; // Code gây TLE
        String input = "1 2 3";
        
        // Chạy trực tiếp hàm run để xem nó trả về gì
        String result = judgeService.run(code, input);
        
        System.out.println("DEBUG OUTPUT: " + result);
        assertEquals("Time Limit Exceeded", result);
    }
}