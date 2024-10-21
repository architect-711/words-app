package edu.architect_711.words.startup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class EnvConfigurationLoader implements ApplicationRunner {
    private final static String DEFAULT_ENV_FILE_NAME = ".env";
    private final static String DEFAULT_ENV_FILE_LOCATION = ".."; // upper folder

    @Override
    public void run(ApplicationArguments args) throws Exception {
        loadEnvConfiguration();
    }

    private void loadEnvConfiguration() {
        final String ENV_FILE_PATH = Paths.get(DEFAULT_ENV_FILE_LOCATION, DEFAULT_ENV_FILE_NAME).toString(); 

        try (BufferedReader br = new BufferedReader(new FileReader(ENV_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null)
                loadEnvVariable(line);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadEnvVariable(final String ENV_FILE_LINE) {
        if (ENV_FILE_LINE.trim().isEmpty() || ENV_FILE_LINE.startsWith("#"))
            return;

        final String[] parts = ENV_FILE_LINE.split("=", 2);

        String key = parts[0].trim();
        String value = parts[1].trim();

        System.setProperty(key, value); 
    }
    
}
