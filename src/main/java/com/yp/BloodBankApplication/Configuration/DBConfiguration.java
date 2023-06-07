package com.yp.BloodBankApplication.Configuration;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Data
@Configuration
@ConfigurationProperties("spring.datasource")
public class DBConfiguration {

    @Profile("dev")
    @Bean
    public String devDatabaseConnection(){
        return "DB connection for dev -H2";
    }

    @Profile("prod")
    @Bean
    public String prodDatabaseConnection(){
        return "DB connection for prod - PostgresSQL";
    }
}
