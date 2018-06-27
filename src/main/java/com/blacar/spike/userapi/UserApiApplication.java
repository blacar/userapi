package com.blacar.spike.userapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

// @checkstyle HideUtilityClassConstructor (10 lines)
@SpringBootApplication
@EnableCaching
public class UserApiApplication {

    public static void main(final String[] args) {
        SpringApplication.run(UserApiApplication.class, args);
    }
}
