package edu.architect_711.words.startup;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *     This is required to load the `.env` file's vars during the startup
 *     because I am fucking tired of making this stupid `.sh` scripts manually
 *     loading them
 * <p/>
 * <p>
 *     Just call the static {@link #load()} method to add the to {@link System}
 * </p>
 * <b>Work my tiny slut!</b>
 */
@Slf4j 
public class EnvLoader implements ApplicationListener<ApplicationStartingEvent> {

    private final static String DEFAULT_ENV_FILE_NAME = ".env";

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        load();
    }

    public static void load() {
        Path path = Paths.get("..", DEFAULT_ENV_FILE_NAME);

        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(line -> loadVarToSystem(line));
        } catch (Exception exception) {
            log.error("Couldn't load env vars:/");

            throw new RuntimeException(exception);
        }
    }

    private static void loadVarToSystem(String line) {
        if (line.isBlank() || line.startsWith("#"))
            return;
        String[] entry = line.split("=");

        System.setProperty(entry[0], entry[1]);
    }

}
