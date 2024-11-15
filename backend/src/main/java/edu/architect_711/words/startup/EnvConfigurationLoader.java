package edu.architect_711.words.startup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EnvConfigurationLoader {
    private final static String DEFAULT_ENV_FILE_NAME = ".env";
    private final static String DEFAULT_ENV_FILE_LOCATION = ".."; // upper folder

    public static void loadEnvConfiguration() {
        final String ENV_FILE_PATH = getEnvPath().toString();

        try (BufferedReader br = new BufferedReader(new FileReader(ENV_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null)
                loadEnvVariable(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * For some reason it depends of the way you run it.
     * </br>
     * If you run from Intellij the path (.env because you open it from the root: words-app)
     * isn't the same as if you would do the same from terminal (the backend folder where ./gradlew locates)
     * */
    private static Path getEnvPath() {
        return new File(DEFAULT_ENV_FILE_NAME).exists() ? Paths.get(DEFAULT_ENV_FILE_NAME) : Paths.get(DEFAULT_ENV_FILE_LOCATION, DEFAULT_ENV_FILE_NAME);
    }

    private static void loadEnvVariable(final String ENV_FILE_LINE) {
        if (ENV_FILE_LINE.trim().isEmpty() || ENV_FILE_LINE.startsWith("#"))
            return;

        final String[] parts = ENV_FILE_LINE.split("=", 2);

        String key = parts[0].trim();
        String value = parts[1].trim();

        System.setProperty(key, value);
    }

}