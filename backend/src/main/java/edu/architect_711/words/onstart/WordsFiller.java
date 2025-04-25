package edu.architect_711.words.onstart;

import edu.architect_711.words.controller.service.WordService;
import edu.architect_711.words.entities.dto.WordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component @RequiredArgsConstructor @Profile("dev")
public class WordsFiller implements ApplicationRunner {
    private final WordService service;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (Objects.requireNonNull(service.read(20, 0).getBody()).size() >= 5)
            return;

        for (byte i = 0; i < 5; i++)
            service.create(new WordDto(
                    null,
                    "title" + LocalDateTime.now(),
                    "translation",
                    "description",
                    List.of(),
                    "Russian",
                    null
            ));
    }
}
