package com.example.dobrobytplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The type Dobrobyt plus application.
 */
@SpringBootApplication
@EnableScheduling
public class DobrobytPlusApplication {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(DobrobytPlusApplication.class, args);
    }

}
