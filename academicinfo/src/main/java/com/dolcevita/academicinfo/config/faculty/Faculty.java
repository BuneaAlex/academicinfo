package com.dolcevita.academicinfo.config.faculty;

import jakarta.annotation.PostConstruct;
import lombok.Getter;

import java.util.Map;
import java.util.Optional;

public class Faculty {
    @Getter
    private final String name;
    private Integer groupSplitFactor;
    private final Map<String, Specialization> specializations;

    public Faculty(String name, Integer groupSplitFactor, Map<String, Specialization> specializations) {
        this.name = name;
        if (groupSplitFactor == null) {
            groupSplitFactor = 2;
        }
        this.groupSplitFactor = groupSplitFactor;
        this.specializations = specializations;
        this.specializations.values().forEach(specialization -> specialization.generateGroups(this.groupSplitFactor));
    }

    public Optional<Specialization> getSpecialization(String identifier) {
        return Optional.ofNullable(specializations.get(identifier));
    }
}
