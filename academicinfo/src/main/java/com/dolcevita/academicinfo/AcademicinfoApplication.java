package com.dolcevita.academicinfo;

import com.dolcevita.academicinfo.config.AcademicConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableConfigurationProperties(value = {AcademicConfig.class})
@EnableAsync
@SpringBootApplication
public class AcademicinfoApplication {
    public static void main(String[] args) {
        SpringApplication.run(AcademicinfoApplication.class, args);
    }
}
