package org.ardian.librarymanagementsystem.exception;

import lombok.Getter;

/**
 * Exception used to wrap errors that occur when communicating with the OpenLibrary API,
 * such as network issues.
 */

@Getter
public class OpenLibraryException extends RuntimeException {

    private final String query;

    public OpenLibraryException(String query, String message) {
        super(message);
        this.query = query;
    }

    public OpenLibraryException(String query, String message, Throwable cause) {
        super(message, cause);
        this.query = query;
    }
}
