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

    private String driverClassName;
    private String url;
    private String username;
    private String password;

    @Profile("dev")
    @Bean
    public String devDatabaseConnection(){
        System.out.println("DB connection for dev - H2");
        System.out.println(driverClassName);
        System.out.println(url);
        return "DB connection for dev -H2";
    }

    @Profile("prod")
    @Bean
    public String prodDatabaseConnection(){
        System.out.println("DB connection for prod - PostgreSQL");
        System.out.println(driverClassName);
        System.out.println(url);
        return "DB connection for prod - PostgreSQL";
    }
}
