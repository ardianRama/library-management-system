package org.ardian.librarymanagementsystem.exception.handler;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Validation-specific error response used when @Valid or input validation fails.
 * Used for MethodArgumentNotValidException to provide detailed feedback
 * about which fields failed validation.
 */

@Getter
public class ValidationErrorResponse {

    private LocalDateTime timestamp;
    private String message;
    private List<String> errors;

    public ValidationErrorResponse(LocalDateTime timestamp, String message, List<String> errors) {
        this.timestamp = timestamp;
        this.message = message;
        this.errors = errors;
    }
}
