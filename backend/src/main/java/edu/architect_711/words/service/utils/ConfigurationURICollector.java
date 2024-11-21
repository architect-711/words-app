package edu.architect_711.words.service.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Image the case when you need to get access to specific sprout of endpoint.
 * You will write tons of code to get it from environment, when assign to variable...
 * </br> </br>
 * This class solves that problem! You have to just specify the end of endpoint
 * and it will automatically build the full URI clinging previous `root_path`s
 * </br></br>
 * Example
 * <code>
 *     ConfigurationURICollector.buildURI("api.endpoints.words.sprouts.create"); // returns: api/words/create
 * </code>
 * */
@Component @RequiredArgsConstructor
public class ConfigurationURICollector {
    private final Environment environment;

    private final String URI_BEGINNING = "api"; // I don't think that will be changed, so leave it here.
    private final String ROOT_PATH_KEY = "root_path";
    private final String REPLACEMENT_ANCHOR_REGEXP = "\\.sprouts\\.[a-z]+";

    public String buildURI(final String ENDPOINT_END_KEY) {
        final String ENDPOINT_END = environment.getProperty(ENDPOINT_END_KEY);

        if (ENDPOINT_END == null)
            throw new IllegalArgumentException("There is no such key.");

        final String ROOT_PATH = environment.getProperty(ENDPOINT_END_KEY.replaceFirst(REPLACEMENT_ANCHOR_REGEXP, "." + ROOT_PATH_KEY));

        if (ROOT_PATH == null)
            throw new IllegalArgumentException("Couldn't build URI, some properties are not found.");

        return String.format(
                "/%s/%s/%s",
                URI_BEGINNING,
                ROOT_PATH,
                ENDPOINT_END
        );
    }
}
