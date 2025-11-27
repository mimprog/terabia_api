package com.terabia.terabia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import me.paulschwarz.springdotenv.DotenvPropertySource;

@Configuration
public class DotenvConfig {

    private final ConfigurableEnvironment environment;

    @Autowired
    public DotenvConfig(ConfigurableEnvironment environment) {
        this.environment = environment;
        DotenvPropertySource.addToEnvironment(environment);
    }
}