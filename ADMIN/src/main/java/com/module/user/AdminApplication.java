package com.module.user;

import com.module.user.configuration.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication(scanBasePackages = {"com.module.library", "com.module.user.*"})
@EnableMongoRepositories(value = "com.module.library.REPOS")
@EntityScan(value = {"com.module.library.MODELS.*"})
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
