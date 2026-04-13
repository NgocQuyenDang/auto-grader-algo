package com.leetcode.leetcode.controller;

import com.leetcode.leetcode.repository.ProblemRepository;
import com.leetcode.leetcode.service.SubmitService;
import org.springframework.ui.Model;
import com.leetcode.leetcode.entity.Problem;
import com.leetcode.leetcode.entity.Submission;
import com.leetcode.leetcode.entity.SubmissionResult;
import com.leetcode.leetcode.entity.User;
import com.leetcode.leetcode.service.JudgeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JudgeController {
    @Autowired
    SubmitService submitService;
    @Autowired
    ProblemRepository pr;
    @PostMapping("/problem/{id}/submit")
    public String judge(@PathVariable("id") long id, @RequestParam("sourceCode") String sourceCode, HttpSession session,
                        Model model) {
        User cur = (User) session.getAttribute("user");
        if (cur == null) {
            return "redirect:/login";
        }
        Problem problem = pr.findById(id)
                .orElseThrow(() -> new RuntimeException("Problem not found"));
        SubmissionResult result = submitService.submit(sourceCode, cur, problem);

        model.addAttribute("result", result);
        model.addAttribute("problem", problem);
        model.addAttribute("sourceCode", sourceCode);

        return "result";
    }
}
