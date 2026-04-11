package com.leetcode.leetcode.repository;

import com.leetcode.leetcode.entity.Problem;
import com.leetcode.leetcode.entity.Testcase;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TestCaseRepository extends JpaRepository<Testcase, Long> {

    List<Testcase> findByProblem(Problem problem);
    List<Testcase> findByProblemId(Long problemId);

}