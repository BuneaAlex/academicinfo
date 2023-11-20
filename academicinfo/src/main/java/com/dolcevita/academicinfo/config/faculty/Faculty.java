package com.dolcevita.academicinfo.config.faculty;

import jakarta.annotation.PostConstruct;
import lombok.Getter;

import java.util.Map;
import java.util.Optional;

public class Faculty {
    @Getter
    private final String name;
    private Integer groupSplitFactor;
    private final Map<String, Major> majors;

    public Faculty(String name, Integer groupSplitFactor, Map<String, Major> majors) {
        this.name = name;
        this.groupSplitFactor = groupSplitFactor;
        this.majors = majors;
    }

    @PostConstruct
    private void postConstruct() {
        if (groupSplitFactor == null) {
            groupSplitFactor = 2;
        }
    }

    public Optional<Major> getMajor(String identifier) {
        return Optional.ofNullable(majors.get(identifier));
    }
}
