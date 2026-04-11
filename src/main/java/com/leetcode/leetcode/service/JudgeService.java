package com.leetcode.leetcode.service;

import com.leetcode.leetcode.entity.Testcase;
import com.leetcode.leetcode.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class JudgeService {
    @Autowired
    TestCaseRepository testcaseRepository;
    public String run(String sourceCode, String input) {
        try {
            Path dir = Files.createTempDirectory("judge");

            Path codeFile = dir.resolve("Main.java");
            Files.writeString(codeFile, sourceCode);

            //Compile
            ProcessBuilder compile = new ProcessBuilder("javac", "Main.java");
            compile.directory(dir.toFile());
            Process process = compile.start();
            int compileResult = process.waitFor();

            if (compileResult != 0) {
                String error = new String(process.getErrorStream().readAllBytes());

                return error;
            }

            //Run code
            ProcessBuilder run = new ProcessBuilder("java", "Main");
            run.directory(dir.toFile());
            Process runProcess = run.start();
            // Send input to program
            runProcess.getOutputStream().write(input.getBytes());
            runProcess.getOutputStream().flush();
            runProcess.getOutputStream().close();

            // Time limit
            boolean finished = runProcess.waitFor(5, TimeUnit.SECONDS);

            if (!finished) {
                runProcess.destroyForcibly();

                return "Time Limit Exceeded";
            }

            String output = new String(runProcess.getInputStream().readAllBytes());

            return output;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String judge(Long problemId, String sourceCode) {
        List<Testcase> test = testcaseRepository.findByProblemId(problemId);

        for (Testcase testcase : test) {
            String output = run(sourceCode, testcase.getInput());

            if (output.equals("TLE")) {
                return "TLE";
            }
            if (!normalize(output).equals(testcase.getExpectedOutput())) {
                return "WA";
            }
        }
        return "AC";
    }

    private String normalize(String s) {
        return s.trim().replace("\r\n", "\n");
    }
}
