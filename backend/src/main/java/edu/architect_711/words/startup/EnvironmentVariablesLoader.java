package edu.architect_711.words.startup;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Component
public class EnvironmentVariablesLoader implements ApplicationListener<ApplicationStartingEvent> {
    private final static String DEFAULT_ENV_FILE_NAME = ".env";
    private final static String DEFAULT_ENV_FILE_LOCATION = ".."; // upper folder

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        loadEnvVarsToSystem();
    }

    public void loadEnvVarsToSystem() {
        loadEnvVars(this::loadVar);
    }

    /**
     * You may need to either to System or to Environment, so decide for yourself.
     * */
    public void loadEnvVars(final Consumer<String> setter) {
        try (final Stream<String> lines = Files.lines(Paths.get(getEnvPath().toString()))) {
            lines.forEach(setter);
        } catch (final Exception exception) {
            throw new IllegalStateException("Couldn't load all env vars:/");
        }
    }

    /**
     * For some reason it depends on the way you run it.
     * </br></br>
     * If you run from Intellij, the path (.env because you open it from the root: words-app)
     * isn't the same as if you would do the same from terminal (the backend folder where ./gradlew locates)
     * */
    private Path getEnvPath() {
        return new File(DEFAULT_ENV_FILE_NAME).exists() ? Paths.get(DEFAULT_ENV_FILE_NAME) : Paths.get(DEFAULT_ENV_FILE_LOCATION, DEFAULT_ENV_FILE_NAME);
    }

    public void loadVar(final String ENV_FILE_LINE) {
        if (ENV_FILE_LINE.trim().isEmpty() || ENV_FILE_LINE.startsWith("#"))
            return;
        final String[] entry = ENV_FILE_LINE.split("=");

        System.setProperty(entry[0], entry[1]);
    }

}