package edu.architect_711.words.onstart;

import edu.architect_711.words.entities.dto.WordDto;
import edu.architect_711.words.service.word.DefaultWordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component @RequiredArgsConstructor @Profile("dev") @Slf4j
public class WordsFiller implements ApplicationRunner {
    private final DefaultWordService service;

    private final static int LIMIT = 5;

    private static WordDto GET_DUMMY_WORD() {
        return new WordDto(
                null,
                "title" + LocalDateTime.now(),
                List.of("translation"),
                "description",
                List.of(),
                "Russian",
                null,
                "some transcription"
        );
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (service.getAll(20, 0).size() >= 5) {
            log.debug("There is enough words, exiting filler");
            return;
        }

        log.debug("Filling words, will be added: " + LIMIT);

        for (byte i = 0; i < LIMIT; i++)
            service.save(GET_DUMMY_WORD());
    }
}
