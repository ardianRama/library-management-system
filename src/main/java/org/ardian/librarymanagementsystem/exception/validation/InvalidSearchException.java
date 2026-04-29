package org.ardian.librarymanagementsystem.exception.validation;

/**
 * Exception thrown when the search input from the user is invalid,
 * for example when the query is null or blank.
 */

public class InvalidSearchException extends ValidationException  {

    public InvalidSearchException() {
        super("Search query cannot be empty");
    }
}
