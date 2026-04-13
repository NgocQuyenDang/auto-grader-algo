package com.leetcode.leetcode.entity;

import java.util.List;

public class SubmissionResult {
    private String result; // AC / WA / TLE
    private List<TestcaseResult> testcases;

    public SubmissionResult(String result, List<TestcaseResult> testcases) {
        this.result = result;
        this.testcases = testcases;
    }

    public String getResult() {
        return result;
    }

    public List<TestcaseResult> getTestcases() {
        return testcases;
    }
}