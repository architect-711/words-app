package edu.architect_711.words.unit.configuration;

import edu.architect_711.words.startup.EnvironmentVariablesLoader;

public abstract class BaseUnitTest {
    public BaseUnitTest() {
        loadEnv();
    }

    protected void loadEnv() {
        new EnvironmentVariablesLoader().loadEnvVarsToSystem();
    }

}
