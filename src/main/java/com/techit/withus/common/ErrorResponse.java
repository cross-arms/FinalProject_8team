package com.techit.withus.common;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import net.minidev.json.annotate.JsonIgnore;

import com.techit.withus.common.exception.ErrorCode;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse {
    private String code;
    private String message;
    private List<FieldError> fieldErrors;
    @JsonIgnore
    private HttpStatus status;


    @Builder
    private ErrorResponse(String code, String message, List<FieldError> fieldErrors, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.fieldErrors = fieldErrors;
        this.status = status;
    }

    public static ErrorResponse of(ErrorCode errorCode){
        return ErrorResponse.builder()
            .code(errorCode.getCode())
            .message(errorCode.getMessage())
            .status(errorCode.getStatus())
            .build();
    }

    public static ErrorResponse of(ErrorCode errorCode, String message){
        return ErrorResponse.builder()
            .code(errorCode.getCode())
            .status(errorCode.getStatus())
            .message(message)
            .build();
    }

    public static ErrorResponse of(ErrorCode errorCode, Map<String, String> fieldErrorMap){
        List<FieldError> fieldErrors = fieldErrorMap.entrySet()
            .stream()
            .map(entry -> new FieldError(entry.getKey(), entry.getValue()))
            .toList();
        return ErrorResponse.builder()
            .code(errorCode.getCode())
            .message(errorCode.getMessage())
            .status(errorCode.getStatus())
            .fieldErrors(fieldErrors)
            .build();
    }


    protected static class FieldError{
        private String field;
        private String message;

        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}
