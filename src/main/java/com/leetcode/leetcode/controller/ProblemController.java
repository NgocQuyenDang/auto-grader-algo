package com.leetcode.leetcode.controller;

import com.leetcode.leetcode.entity.Problem;
import com.leetcode.leetcode.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class ProblemController {

    @Autowired
    ProblemRepository probrepository;

    @GetMapping("/problems")
    public String showProblems(Model model) {
        List<Problem> list = probrepository.findAll();
        model.addAttribute("problems", list);
        return "problems";
    }

    @GetMapping("/admin/add-problem")
    public String showAddProblem() {
        return "add-problem";
    }

    @PostMapping("/admin/add-problem")
    public String addProblem(@RequestParam String title, @RequestParam String category, @RequestParam String description) {
        if (probrepository.findByTitle(title).isPresent() || probrepository.findByDescription(description).isPresent()) {
            return "redirect:/admin/add-problem?errorMessage=true";
        } else {
            Problem problem = new Problem(title, category, description);
            probrepository.save(problem);
        }
        return "redirect:/problems";
    }

    @GetMapping("/problem/{id}")
    public String solveProblem(@PathVariable long id, Model model) {
        Problem problem = probrepository.findById(id).orElseThrow(() -> new RuntimeException("Problem not found"));
        model.addAttribute("problem", problem);

        return "submit";
    }
}
