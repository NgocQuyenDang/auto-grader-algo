package com.leetcode.leetcode.controller;

import com.leetcode.leetcode.entity.Problem;
import com.leetcode.leetcode.entity.Submission;
import com.leetcode.leetcode.entity.User;
import com.leetcode.leetcode.repository.ProblemRepository;
import com.leetcode.leetcode.repository.SubmissionRepository;
import com.leetcode.leetcode.service.SubmitService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SubmitController {
    @Autowired
    SubmissionRepository submit;

    @Autowired
    SubmitService submitService;

    @Autowired
    ProblemRepository problemRepository;

    @PostMapping("/problem/{id}/submit")
    public String submit(@PathVariable("id") long id, @RequestParam("sourceCode") String source, HttpSession session) {
        User cur = (User) session.getAttribute("user");
        Problem problem = problemRepository.findById(id).orElseThrow(() -> new RuntimeException("Problem not found"));


        Submission submission = submitService.submit(source, cur, problem);

        session.setAttribute("submission", submission);

        return "redirect:/result";
    }

    @GetMapping("/result")
    public String showResult(Model model, HttpSession session) {
        Submission submission = (Submission) session.getAttribute("submission");
        model.addAttribute("submission", submission);
        return "result";
    }
}
