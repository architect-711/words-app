package edu.architect_711.words;

import edu.architect_711.words.startup.EnvironmentVariablesLoader;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class WordsApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(WordsApplication.class).listeners(new EnvironmentVariablesLoader()).run(args);
	}
}
