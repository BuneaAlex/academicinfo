package com.dolcevita.academicinfo.controller;

import com.dolcevita.academicinfo.exceptions.ResourceNotFoundException;
import com.dolcevita.academicinfo.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ConfirmationController {

    private final AuthenticationService service;

    @GetMapping("auth/confirm")
    public String confirm(@RequestParam String token) {
        try {
            service.confirm(token);
        } catch (ResourceNotFoundException e) {
            return "verified-page-error.html";
        }
        return "verified-page";
    }
}
