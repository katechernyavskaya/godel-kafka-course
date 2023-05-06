package com.godel.kafka.course.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories({
    "com.godel.kafka.course.*"
})
@EntityScan({
    "com.godel.kafka.course.*"
})
@ComponentScan("com.godel.kafka.course")
public class JpaConfig {

}
