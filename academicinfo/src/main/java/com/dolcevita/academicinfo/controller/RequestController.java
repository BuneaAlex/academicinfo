package com.dolcevita.academicinfo.controller;

import com.dolcevita.academicinfo.dto.RequestCreateDto;
import com.dolcevita.academicinfo.dto.RequestDto;
import com.dolcevita.academicinfo.model.Request;
import com.dolcevita.academicinfo.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    public RequestDto createRequest(@RequestHeader("Authorization") String jwt,
                                    @RequestParam("file") MultipartFile file,
                                    @RequestParam("type")Request.RequestType type) throws IOException {
        return requestService.createRequest(new RequestCreateDto(0, type, file), jwt);
    }

    @GetMapping
    public List<RequestDto> getRequest(@RequestHeader("Authorization") String jwt) {
        return requestService.getRequest(jwt);
    }
}
