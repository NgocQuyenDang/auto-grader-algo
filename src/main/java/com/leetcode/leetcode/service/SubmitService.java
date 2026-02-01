package com.leetcode.leetcode.service;

import com.leetcode.leetcode.entity.Problem;
import com.leetcode.leetcode.entity.Submission;
import com.leetcode.leetcode.entity.User;
import com.leetcode.leetcode.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubmitService {
    @Autowired
    SubmissionRepository submissionRepository;

    public Submission submit(String source, User user, Problem problem) {
        Submission submission = new Submission();
        submission.setSourceCode(source);
        submission.setUser(user);
        submission.setProblem(problem);

        return submissionRepository.save(submission);
    }
}
