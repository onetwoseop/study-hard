package com.studyhard.spring.global.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Map;
import org.springframework.http.HttpStatus;

/**
 * Single error body shape for every failure, including 401/403 written by the security filters.
 * {@code errors} carries per-field messages and is present only for input validation failures.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
    int status,
    String code,
    String message,
    Map<String, String> errors
) {
    public static ErrorResponse of(HttpStatus status, String message) {
        return new ErrorResponse(status.value(), status.name(), message, null);
    }

    public static ErrorResponse of(HttpStatus status, String message, Map<String, String> errors) {
        return new ErrorResponse(status.value(), status.name(), message, errors);
    }
}
