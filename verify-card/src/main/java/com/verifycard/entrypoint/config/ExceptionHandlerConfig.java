package com.verifycard.entrypoint.config;

import com.verifycard.core.exception.BadRequestException;
import com.verifycard.core.exception.BusinessLogicConflictException;
import com.verifycard.core.exception.NotFoundException;
import com.verifycard.core.exception.UnauthorisedException;
import com.verifycard.entrypoint.models.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = {"com.verifycard.entrypoint.controller"})
@Log4j2
public class ExceptionHandlerConfig {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<String>> handleBadRequestException(BadRequestException e) {
        log.info("error message: {}", e.getMessage());
        ApiResponse<String> apiResponse = new ApiResponse<>(e.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessLogicConflictException.class)
    public ResponseEntity<ApiResponse<String>> handleBusinessLogicConflictException(BusinessLogicConflictException e) {
        log.info("error message: {}", e.getMessage());
        ApiResponse<String> apiResponse = new ApiResponse<>(e.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNotFoundException(NotFoundException e) {
        log.info("error message: {}", e.getMessage());
        ApiResponse<String> apiResponse = new ApiResponse<>(e.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorisedException.class)
    public ResponseEntity<ApiResponse<String>> handleUnauthorisedExceptionException(UnauthorisedException e) {
        log.info("error message: {}", e.getMessage());
        ApiResponse<String> apiResponse = new ApiResponse<>(e.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }
}
