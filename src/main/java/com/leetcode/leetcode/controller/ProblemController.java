package com.leetcode.leetcode.controller;

import com.leetcode.leetcode.entity.Problem;
import com.leetcode.leetcode.entity.Testcase;
import com.leetcode.leetcode.entity.User;
import com.leetcode.leetcode.repository.ProblemRepository;
import com.leetcode.leetcode.repository.TestCaseRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProblemController {

    @Autowired
    ProblemRepository probrepository;

    @Autowired
    TestCaseRepository testCaseRepository;

    @GetMapping("/problems")
    public String showProblems(Model model) {
        List<Problem> list = probrepository.findAll();
        model.addAttribute("problems", list);
        return "problems";
    }

    @GetMapping("/admin/add-problem")
    public String showAddProblem(HttpSession session) {
        String deny = denyUnlessAdmin(session);
        if (deny != null) {
            return deny;
        }
        return "add-problem";
    }

    @PostMapping("/admin/add-problem")
    @Transactional
    public String addProblem(@RequestParam String title,
                             @RequestParam String category,
                             @RequestParam String description,
                             @RequestParam(required = false) List<String> inputs,
                             @RequestParam(required = false) List<String> expectedOutputs,
                             HttpSession session) {
        String deny = denyUnlessAdmin(session);
        if (deny != null) {
            return deny;
        }
        if (probrepository.findByTitle(title).isPresent() || probrepository.findByDescription(description).isPresent()) {
            return "redirect:/admin/add-problem?errorMessage=duplicate";
        }

        List<Testcase> testcases = buildTestcases(inputs, expectedOutputs);
        if (testcases.isEmpty()) {
            return "redirect:/admin/add-problem?errorMessage=testcase";
        }

        Problem problem = probrepository.save(new Problem(title, category, description));
        for (Testcase testcase : testcases) {
            testcase.setProblem(problem);
        }
        testCaseRepository.saveAll(testcases);
        return "redirect:/problems";
    }

    private List<Testcase> buildTestcases(List<String> inputs, List<String> expectedOutputs) {
        List<Testcase> testcases = new ArrayList<>();
        if (inputs == null || expectedOutputs == null) {
            return testcases;
        }
        int n = Math.min(inputs.size(), expectedOutputs.size());
        for (int i = 0; i < n; i++) {
            String input = inputs.get(i) == null ? "" : inputs.get(i);
            String expected = expectedOutputs.get(i) == null ? "" : expectedOutputs.get(i);
            if (input.isBlank() && expected.isBlank()) {
                continue;
            }
            if (input.isBlank() || expected.isBlank()) {
                return List.of();
            }
            Testcase testcase = new Testcase();
            testcase.setInput(input);
            testcase.setExpectedOutput(expected);
            testcases.add(testcase);
        }
        return testcases;
    }

    /**
     * Cửa phân quyền: HTML chỉ ẩn nút; chỗ này mới quyết định có được vào/ghi DB không.
     */
    private String denyUnlessAdmin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        if (!user.isAdmin()) {
            return "redirect:/problems";
        }
        return null;
    }

    @GetMapping("/problem/{id}")
    public String solveProblem(@PathVariable long id, Model model) {
        Problem problem = probrepository.findById(id).orElseThrow(() -> new RuntimeException("Problem not found"));
        model.addAttribute("problem", problem);

        return "submit";
    }
}
