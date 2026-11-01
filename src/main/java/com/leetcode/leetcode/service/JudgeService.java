package com.leetcode.leetcode.service;

import com.leetcode.leetcode.entity.SubmissionResult;
import com.leetcode.leetcode.entity.Testcase;
import com.leetcode.leetcode.entity.TestcaseResult;
import com.leetcode.leetcode.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class JudgeService {

    @Autowired
    TestCaseRepository testcaseRepository;

    @Autowired
    DockerSandbox dockerSandbox;

    private static final List<String> BLACKLIST = List.of(
            "Runtime", "ProcessBuilder", "Process",
            "System.exit", "System.getenv", "System.getProperty",
            "Files.delete", "File.delete", "Files.walk",
            "FileWriter", "FileOutputStream",
            "ServerSocket", "Socket", "URL",
            "Thread", "Executors"
    );

    private String checkBlacklist(String sourceCode) {
        for (String banned : BLACKLIST) {
            if (sourceCode.contains(banned)) {
                return "Forbidden: " + banned;
            }
        }
        return null;
    }

    public String run(String sourceCode, String input) {
        Path dir = null;
        try {
            dir = Files.createTempDirectory("judge");

            Path codeFile = dir.resolve("Main.java");
            Files.writeString(codeFile, sourceCode);

            String compileOutput = dockerSandbox.compile(dir);
            if (compileOutput != null && !compileOutput.isBlank()) {
                return compileOutput;
            }

            return dockerSandbox.runProgram(dir, input);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // Luôn xóa thư mục tạm dù có lỗi hay không
            if (dir != null) {
                try {
                    Files.walk(dir)
                            .sorted(Comparator.reverseOrder())
                            .forEach(p -> p.toFile().delete());
                } catch (IOException ignored) {}
            }
        }
    }

    public SubmissionResult judge(Long problemId, String sourceCode) {

        String forbidden = checkBlacklist(sourceCode);
        if (forbidden != null) {
            return new SubmissionResult(forbidden, List.of());
        }

        List<Testcase> tests = testcaseRepository.findByProblemId(problemId);
        List<TestcaseResult> results = new ArrayList<>();

        boolean hasWA = false;
        boolean hasTLE = false;

        int index = 1;

        for (Testcase testcase : tests) {

            String output = run(sourceCode, testcase.getInput());

            if (output.trim().contains("Time Limit Exceeded")) {
                hasTLE = true;

                results.add(new TestcaseResult(
                        index,
                        "TLE",
                        testcase.getInput(),
                        testcase.getExpectedOutput(),
                        "Time Limit Exceeded"
                ));
            } else if (!normalize(output).equals(normalize(testcase.getExpectedOutput()))) {
                hasWA = true;

                results.add(new TestcaseResult(
                        index,
                        "Failed",
                        testcase.getInput(),
                        testcase.getExpectedOutput(),
                        output
                ));

            } else {
                results.add(new TestcaseResult(
                        index,
                        "Passed",
                        testcase.getInput(),
                        testcase.getExpectedOutput(),
                        output
                ));
            }

            index++;
        }

        String finalResult = hasTLE ? "TLE" : (hasWA ? "WA" : "AC");

        return new SubmissionResult(finalResult, results);
    }

    private String normalize(String s) {
        return s.trim().replace("\r\n", "\n");
    }
}