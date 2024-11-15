package edu.architect_711.words;

import edu.architect_711.words.startup.EnvConfigurationLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WordsApplication {
	public static void main(String[] args) {
		EnvConfigurationLoader.loadEnvConfiguration(); // IMPORTANT!!!!! Look in docs why

		SpringApplication.run(WordsApplication.class, args);
	}
}
