package com.dolcevita.academicinfo.controller;

import com.dolcevita.academicinfo.dto.AuthenticationResponse;
import com.dolcevita.academicinfo.dto.RegisterResponse;
import com.dolcevita.academicinfo.exceptions.InvalidEmailException;
import com.dolcevita.academicinfo.exceptions.NotConfirmedException;
import com.dolcevita.academicinfo.service.AuthenticationService;
import com.dolcevita.academicinfo.dto.LoginRequest;
import com.dolcevita.academicinfo.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) throws NotConfirmedException, InvalidEmailException {
        return ResponseEntity.ok(service.login(request));
    }
}
