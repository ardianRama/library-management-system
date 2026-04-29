package org.ardian.librarymanagementsystem.exception.handler;

import org.ardian.librarymanagementsystem.exception.business.conflict.ConflictException;
import org.ardian.librarymanagementsystem.exception.business.notfound.NotFoundException;
import org.ardian.librarymanagementsystem.exception.integration.IntegrationException;
import org.ardian.librarymanagementsystem.exception.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Global exception handler for the application.
 */

@ControllerAdvice
public class CustomizedExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException ex) {
        return buildError(ex, HttpStatus.NOT_FOUND, "Resource not found");
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException ex) {
        return buildError(ex, HttpStatus.CONFLICT, "Business rule violation");
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(ValidationException ex) {
        return buildError(ex, HttpStatus.BAD_REQUEST, "Validation error");
    }

    @ExceptionHandler(IntegrationException.class)
    public ResponseEntity<ErrorResponse> handleIntegration(IntegrationException ex) {
        return buildError(ex, HttpStatus.SERVICE_UNAVAILABLE, "External service error");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        return buildError(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex
    ) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        ValidationErrorResponse response = new ValidationErrorResponse(
                LocalDateTime.now(),
                "Validation failed",
                errors
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> buildError(
            Exception ex,
            HttpStatus status,
            String details) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                details
        );

        return new ResponseEntity<>(error, status);
    }
}
