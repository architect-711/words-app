package edu.architect_711.words;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import edu.architect_711.words.startup.EnvLoader;

@SpringBootApplication
public class WordsApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(WordsApplication.class).listeners(new EnvLoader()).run(args);
	}
}
