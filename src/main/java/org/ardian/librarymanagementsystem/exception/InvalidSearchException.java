package org.ardian.librarymanagementsystem.exception;

/**
 * Exception thrown when the search input from the user is invalid,
 * for example when the query is null or blank.
 */

public class InvalidSearchException extends RuntimeException {

    public InvalidSearchException() {
        super("Search query cannot be empty");
    }
}
