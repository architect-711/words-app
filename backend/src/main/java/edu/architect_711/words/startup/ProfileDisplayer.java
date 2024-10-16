package edu.architect_711.words.startup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component @Slf4j @RequiredArgsConstructor
public class ProfileDisplayer implements ApplicationRunner {
    private final Environment env;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Started with profiles: {}", Arrays.stream(env.getActiveProfiles()).toList());
    }
}
