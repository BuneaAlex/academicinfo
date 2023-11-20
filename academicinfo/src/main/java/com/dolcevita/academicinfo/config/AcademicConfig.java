package com.dolcevita.academicinfo.config;

import com.dolcevita.academicinfo.config.faculty.Faculty;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties("academic")
public record AcademicConfig (Map<String, Faculty> faculties) {
}
