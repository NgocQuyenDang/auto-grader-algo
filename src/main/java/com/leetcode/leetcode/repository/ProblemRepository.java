package com.leetcode.leetcode.repository;

import com.leetcode.leetcode.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    Optional<Problem> findByTitle(String title);
    Optional<Problem> findByDescription(String description);
    Optional<Problem> findById(long id);
}
