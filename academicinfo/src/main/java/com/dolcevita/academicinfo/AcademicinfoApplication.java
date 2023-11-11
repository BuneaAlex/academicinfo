package com.dolcevita.academicinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AcademicinfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcademicinfoApplication.class, args);
    }
}
