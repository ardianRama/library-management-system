package org.ardian.librarymanagementsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * Global exception handler for the application.
 */

@ControllerAdvice
public class CustomizedExceptionHandler {

    @ExceptionHandler(OpenLibraryException.class)
    public ResponseEntity<ErrorResponse> handleOpenLibraryException(
            OpenLibraryException ex) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                "Error calling external API"
        );

        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(InvalidSearchException.class)
    public ResponseEntity<ErrorResponse> handleInvalidSearch(InvalidSearchException ex) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                "Invalid search input"
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleBookAlreadyExist(BookAlreadyExistsException ex) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                "Book already exists and cannot be added again"
        );

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookNotFound(BookNotFoundException ex) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                "Book not found"
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidBookUpdateException.class)
    public ResponseEntity<ErrorResponse> handleInvalidBookUpdate(InvalidBookUpdateException ex) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                "Invalid book update"
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleBookNotAvailableException(BookNotAvailableException ex) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                "Book not available"
        );

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LibraryUserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLibraryUserNotFound(LibraryUserNotFoundException ex) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                "Library user not found"
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                "Something went wrong",
                ex.getMessage()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
