package com.techit.withus.common;

import java.util.Map;
import java.util.stream.Collectors;

import com.techit.withus.web.feeds.exception.FeedException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.techit.withus.common.exception.AuthenticationException;
import com.techit.withus.common.exception.AuthorizationException;
import com.techit.withus.common.exception.BusinessException;
import com.techit.withus.common.exception.EntityNotFoundException;
import com.techit.withus.common.exception.ErrorCode;
import com.techit.withus.common.exception.FileProcessingException;
import com.techit.withus.common.exception.InvalidStateException;
import com.techit.withus.common.exception.InvalidValueException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<ErrorResponse> handleInvalidValueException(InvalidValueException ex){
        var response = ErrorResponse.of(ex.getErrorCode());
        log.debug("Invalid request has been accepted : {}", ex.getMessage());
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(InvalidStateException.class)
    public ResponseEntity<ErrorResponse> handleInvalidStateException(InvalidStateException ex){
        var response = ErrorResponse.of(ex.getErrorCode());
        log.debug("Invalid state has been detected : {}", ex.getMessage());
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex){
        var response = ErrorResponse.of(ex.getErrorCode(), "인증에 실패했습니다.");
        log.debug("Authentication failure : {}", ex.getMessage());
        return new ResponseEntity<>(response, response.getStatus());
    }
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationException(AuthorizationException ex){
        var response = ErrorResponse.of(ex.getErrorCode(), "권한이 없습니다.");
        log.debug("Authorization failure : {}", ex.getMessage());
        return new ResponseEntity<>(response, response.getStatus());
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex){
        var response = ErrorResponse.of(ex.getErrorCode(), "회원을 찾을 수 없습니다.");
        log.debug("Entity does not exist : {}", ex.getMessage());
        return new ResponseEntity<>(response, response.getStatus());
    }
    @ExceptionHandler(FileProcessingException.class)
    public ResponseEntity<ErrorResponse> handleFileProcessingException(FileProcessingException ex){
        var response = ErrorResponse.of(ex.getErrorCode());
        log.error("File IO failure : {}", ex.getMessage());
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        var response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, ex.getMessage());
        log.debug("Input type is not valid : {}", ex.getMessage());
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodAugumentNotValidException(MethodArgumentNotValidException ex){
        Map<String, String> fieldErrors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(FieldError::getField,
                error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : ""));
        var response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, fieldErrors);
        log.debug("Argument is not valid : {}", ex.getMessage());
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        var response = ErrorResponse.of(ex.getErrorCode(), ex.getMessage());
        log.error("Unexpected error has occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleExceptionHandler(Exception ex) {
        ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
