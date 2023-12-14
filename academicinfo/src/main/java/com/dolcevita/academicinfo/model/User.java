package com.dolcevita.academicinfo.model;

import com.dolcevita.academicinfo.dto.StudentDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", indexes = {
        @Index(name = "email_index", columnList = "email ASC", unique = true),
        @Index(name = "uuid_index", columnList = "uuid ASC", unique = true),
        @Index(name = "registrationNumber_index", columnList = "registrationNumber ASC", unique = true),
        @Index(name = "registerToken_index", columnList = "registerToken ASC", unique = true),
})
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private int id;

    @Column(name = "uuid", unique = true)
    private String uuid;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "registerToken")
    private String registerToken;

    @Column(name = "registerTokenExpiration")
    private LocalDateTime registerTokenExpiration;

    @Column(name = "isMailConfirmed")
    private boolean isMailConfirmed;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

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

    private Integer semiGroup;

    @Enumerated(EnumType.STRING)
    private Funding funding;

    @ManyToMany
    @JoinTable(
            name = "student_subject",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private List<Subject> subjects;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @PrePersist
    public void prePersist() {
        this.uuid = java.util.UUID.randomUUID().toString();
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

    @Getter
    @AllArgsConstructor
    public enum Language {
        ROMANIAN("ro"),
        ENGLISH("en"),
        GERMAN("de");
        //baszd meg ez a magyarul nem mukodik
        private final String name;
    }

    public enum Funding {
        BUDGET,
        TAX
    }

    public enum Role {
        STUDENT,
        ADMIN
    }
}
