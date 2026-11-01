package com.leetcode.leetcode.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Chạy javac/java trong container, không trên process Spring.
 * Timeout/memory vẫn cần — chúng là hạn mức, sandbox mới là ranh giới quyền.
 */
@Component
public class DockerSandbox {

    @Value("${judge.docker.image:eclipse-temurin:21-jdk}")
    private String image;

    @Value("${judge.timeout-seconds:5}")
    private int timeoutSeconds;

    public String compile(Path workDir) throws IOException, InterruptedException {
        return exec(workDir, null, timeoutSeconds + 10, List.of("javac", "Main.java"));
    }

    public String runProgram(Path workDir, String input) throws IOException, InterruptedException {
        return exec(workDir, input, timeoutSeconds, List.of("java", "-Xmx64m", "-Xms16m", "Main"));
    }

    private String exec(Path workDir, String stdin, int waitSeconds, List<String> command)
            throws IOException, InterruptedException {
        List<String> args = new ArrayList<>();
        args.add("docker");
        args.add("run");
        args.add("--rm");
        args.add("-i");
        // Cô lập: không mạng, không thấy ổ máy trừ /work, siết RAM/CPU/số process.
        args.add("--network=none");
        args.add("--memory=128m");
        args.add("--memory-swap=128m");
        args.add("--cpus=0.5");
        args.add("--pids-limit=64");
        args.add("--read-only");
        args.add("--tmpfs");
        args.add("/tmp:rw,exec,size=32m");
        args.add("--cap-drop=ALL");
        args.add("--security-opt");
        args.add("no-new-privileges:true");
        args.add("--workdir=/work");
        args.add("-v");
        args.add(workDir.toAbsolutePath() + ":/work");
        args.add(image);
        args.addAll(command);

        ProcessBuilder pb = new ProcessBuilder(args);
        pb.redirectErrorStream(false);
        Process process;
        try {
            process = pb.start();
        } catch (IOException e) {
            throw new IOException(
                    "Docker chưa chạy. Cài Docker Desktop, mở app đó, rồi restart IDE. Chi tiết: " + e.getMessage(),
                    e
            );
        }

        if (stdin != null) {
            process.getOutputStream().write(stdin.getBytes(StandardCharsets.UTF_8));
        }
        process.getOutputStream().close();

        boolean finished = process.waitFor(waitSeconds, TimeUnit.SECONDS);
        if (!finished) {
            process.destroyForcibly();
            return "Time Limit Exceeded";
        }

        String stdout = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        String stderr = new String(process.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);
        if (process.exitValue() != 0 && !stderr.isBlank()) {
            return stderr;
        }
        return stdout;
    }
}
