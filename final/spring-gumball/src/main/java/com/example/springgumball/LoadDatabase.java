package com.example.springgumball;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    @Bean
    CommandLineRunner initDatabase(GumballModelRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new GumballModel("2134998871109", "SB102927", 21)));
        };
    }
}