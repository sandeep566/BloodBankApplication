package com.yp.BloodBankApplication.Configuration;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


/**
 * The {@code DBConfiguration} class represents the configuration for the database connection in the Blood Bank Application.
 * It provides methods to configure the database connection based on the active profile.
 */
@Data
@Configuration
@ConfigurationProperties("spring.datasource")
public class DBConfiguration {

    /**
     * Configures the database connection for the dev profile using H2 database.
     *
     * @return the string representing the dev database connection
     */
    @Profile("dev")
    @Bean
    public String devDatabaseConnection(){
        return "DB connection for dev -H2";
    }


    /**
     * Configures the database connection for the prod profile using PostgreSQL.
     *
     * @return the string representing the prod database connection
     */
    @Profile("prod")
    @Bean
    public String prodDatabaseConnection(){
        return "DB connection for prod - PostgresSQL";
    }
}
