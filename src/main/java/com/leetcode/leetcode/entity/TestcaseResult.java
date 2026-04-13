package com.leetcode.leetcode.entity;

public class TestcaseResult {
    private int index;
    private String status; // Passed / Failed / TLE
    private String input;
    private String expectedOutput;
    private String actualOutput;

    // constructor + getter

    public TestcaseResult(int index, String status, String input, String expectedOutput, String actualOutput) {
        this.index = index;
        this.status = status;
        this.input = input;
        this.expectedOutput = expectedOutput;
        this.actualOutput = actualOutput;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public String getActualOutput() {
        return actualOutput;
    }

    public void setActualOutput(String actualOutput) {
        this.actualOutput = actualOutput;
    }
}