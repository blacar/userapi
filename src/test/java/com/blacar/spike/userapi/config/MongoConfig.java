package com.blacar.spike.userapi.config;

import java.io.IOException;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;

// @checkstyle DesignForExtension (20 lines)
@Configuration
public class MongoConfig {
    private static final String MONGO_DB_URL = "localhost";
    private static final String MONGO_DB_NAME = "embeded_db";

    @Bean
    public MongoTemplate mongoTemplate() throws IOException {
        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
        mongo.setBindIp(MONGO_DB_URL);
        return new MongoTemplate(
            Optional.ofNullable(
                mongo.getObject()
            ).orElseThrow(
                () -> new IllegalArgumentException("Missing mongo isntance.")
            ),
            MONGO_DB_NAME
        );
    }
}
