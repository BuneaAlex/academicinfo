package com.dolcevita.academicinfo.exceptions.handlers;

import com.dolcevita.academicinfo.controller.AuthenticationController;
import com.dolcevita.academicinfo.dto.ApiError;
import com.dolcevita.academicinfo.exceptions.EmailAlreadyExistsException;
import com.dolcevita.academicinfo.exceptions.InvalidEmailException;
import com.dolcevita.academicinfo.exceptions.NotConfirmedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackageClasses = {AuthenticationController.class})
public class AuthenticationControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotConfirmedException.class)
    public ResponseEntity<Object> handleValidationException(NotConfirmedException e) {
        return buildErrorResponse(new ApiError(HttpStatus.BAD_REQUEST, "Mail not confirmed", e));
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<Object> handleInvalidEmailException(InvalidEmailException e) {
        return buildErrorResponse(new ApiError(HttpStatus.BAD_REQUEST, "User with this email not found", e));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Object> handleCustomNotFoundException(EmailAlreadyExistsException e) {
        return buildErrorResponse(new ApiError(HttpStatus.BAD_REQUEST, "User with this email already exists", e));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleCustomNotFoundException(ExpiredJwtException e) {
        return buildErrorResponse(new ApiError(HttpStatus.FORBIDDEN, "Token expired", e));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Object> handleCustomNotFoundException(MissingRequestHeaderException e) {
        return buildErrorResponse(new ApiError(HttpStatus.FORBIDDEN, "Token header not present", e));
    }

    @ExceptionHandler({SignatureException.class, MalformedJwtException.class})
    public ResponseEntity<Object> handleCustomNotFoundException(SignatureException e) {
        return buildErrorResponse(new ApiError(HttpStatus.FORBIDDEN, "Invalid token", e));
    }

    private static ResponseEntity<Object> buildErrorResponse(ApiError apiError) {
        return new ResponseEntity<>(apiError, HttpStatusCode.valueOf(apiError.getStatus()));
    }
}
