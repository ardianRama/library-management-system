package org.ardian.librarymanagementsystem.exception.handler;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Standard error response used for application exceptions (business, integration, etc.).
 * Used for most custom exceptions such as NotFound, Conflict and Integration errors.
 */

@Getter
public class ErrorResponse {

    private LocalDateTime timestamp;
    private String message;
    private String details;

    public ErrorResponse(LocalDateTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}
