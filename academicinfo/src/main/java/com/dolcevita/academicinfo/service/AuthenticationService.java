package com.dolcevita.academicinfo.service;

import com.dolcevita.academicinfo.dto.*;
import com.dolcevita.academicinfo.exceptions.EmailAlreadyExistsException;
import com.dolcevita.academicinfo.exceptions.InvalidEmailException;
import com.dolcevita.academicinfo.exceptions.NotConfirmedException;
import com.dolcevita.academicinfo.exceptions.ResourceNotFoundException;
import com.dolcevita.academicinfo.model.User;
import com.dolcevita.academicinfo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final StudentService studentService;
    //private final EmailService emailService;

    public RegisterResponse register(RegisterRequest request) {
        userRepository.findByEmail(request.getEmail()).ifPresent(user -> {throw new EmailAlreadyExistsException("User with this email already exists");});
        String registerToken = UUID.randomUUID().toString();
        var user = User.builder()
                .role(User.Role.STUDENT)
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .registerToken(registerToken)
                .registerTokenExpiration(LocalDateTime.now().plusDays(1))
                .isMailConfirmed(false)
                .build();
        userRepository.save(user);
        //emailService.sendConfirmationEmail(request.getEmail(), request.getEmail(), "Confirm your email", "We want to ensure this is your email", "http://localhost:8080/auth/confirm?token=" + registerToken, "Confirm email", "Thank you for registering!");
        return new RegisterResponse("Registered");
    }

    public AuthenticationResponse login(LoginRequest request) throws NotConfirmedException, InvalidEmailException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new InvalidEmailException("User with this email not found"));
//        if (!user.isMailConfirmed())
//            throw new NotConfirmedException("Mail not confirmed");
        var jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

    public void confirm(String token) {
        User user = userRepository.findByRegisterToken(token).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (user.getRegisterTokenExpiration().isBefore(LocalDateTime.now())) {
            userRepository.delete(user);
            throw new ResourceNotFoundException("Token expired");
        }
        user.setRegisterToken(null);
        user.setMailConfirmed(true);
        userRepository.save(user);
    }

    public Optional<StudentDto> confirmStudentByToken(final String jwt) {
        String extractedMail = jwtService.extractUsername(JwtService.jwtFromHeader(jwt));
        try {
            return Optional.of(studentService.getStudent(extractedMail));
        } catch (ResourceNotFoundException ignored) {
            return Optional.empty();
        }
    }

    public Optional<User> confirmUserByToken(final String jwt) {
        val extractedMail = jwtService.extractUsername(JwtService.jwtFromHeader(jwt));
        return userRepository.findByEmail(extractedMail);
    }
}
