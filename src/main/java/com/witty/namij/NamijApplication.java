package com.witty.namij;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NamijApplication {

    public static void main(String[] args) {
        SpringApplication.run(NamijApplication.class, args);
    }

}
