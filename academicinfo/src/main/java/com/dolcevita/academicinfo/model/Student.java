package com.dolcevita.academicinfo.model;

import com.dolcevita.academicinfo.dto.StudentDto;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("student")
public class Student extends User {

    @Column(name = "registrationNumber", unique = true)
    private Integer registrationNumber;

    private String firstName;

    private String surname;

    @Enumerated(EnumType.STRING)
    private Specialization specialization;

    @Enumerated(EnumType.STRING)
    private Language language;

    private Integer yearOfStudy;

    private Integer groupNumber;

    private Funding funding;

    public static class StudentBuilder extends UserBuilder {
        private StudentBuilder() {
        }
    }

    public StudentDto toDto() {
        return StudentDto.builder()
                .uuid(this.getUuid())
                .email(this.getEmail())
                .registrationNumber(this.getRegistrationNumber())
                .firstName(this.getFirstName())
                .surname(this.getSurname())
                .specialization(this.getSpecialization())
                .language(this.getLanguage())
                .yearOfStudy(this.getYearOfStudy())
                .groupNumber(this.getGroupNumber())
                .funding(this.getFunding())
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .build();
    }

    public enum Specialization {
        COMPUTER_SCIENCE,
        MATHEMATICS_AND_INFORMATICS,
        MATHEMATICS
    }

    public enum Language {
        ROMANIAN,
        ENGLISH,
        GERMAN
        // Damiane daca te mananca sa adaugi ceva aici sa te fut in cur :)))
    }

    public enum Funding {
        BUDGET,
        TAX
    }
}
