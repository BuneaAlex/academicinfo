package com.dolcevita.academicinfo.service;

import com.dolcevita.academicinfo.dto.RequestCreateDto;
import com.dolcevita.academicinfo.dto.RequestDto;
import com.dolcevita.academicinfo.dto.StudentDto;
import com.dolcevita.academicinfo.exceptions.ResourceNotFoundException;
import com.dolcevita.academicinfo.model.Request;
import com.dolcevita.academicinfo.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;
    private final JwtService jwtService;
    private final StudentService studentService;

    public RequestDto createRequest(RequestCreateDto requestDto, String jwt) throws IOException {
        String extractedMail = jwtService.extractUsername(JwtService.jwtFromHeader(jwt));
        StudentDto student = null;
        try {
            student = studentService.getStudent(extractedMail);
        } catch (ResourceNotFoundException ignored) {
            student = null;
        }
        requestDto.setStudentRegistrationNumber(student.getRegistrationNumber());
        return requestRepository.save(requestDto.toRequest()).toDto();
    }

    public List<RequestDto> getRequest(String jwt) {
        String extractedMail = jwtService.extractUsername(JwtService.jwtFromHeader(jwt));
        StudentDto student = null;
        try {
            student = studentService.getStudent(extractedMail);
        } catch (ResourceNotFoundException ignored) {
            student = null;
        }
        return requestRepository.findAllByStudentRegistrationNumber(student.getRegistrationNumber())
                .stream()
                .map(Request::toDto)
                .toList();
    }
}
