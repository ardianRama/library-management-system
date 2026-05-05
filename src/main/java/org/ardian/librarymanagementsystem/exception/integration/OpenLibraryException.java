package org.ardian.librarymanagementsystem.exception.integration;

/**
 * Exception used to wrap errors that occur when communicating with the OpenLibrary API,
 * such as network issues.
 */

public class OpenLibraryException extends IntegrationException  {

    private static final String DEFAULT_MESSAGE =
            "External book service unavailable";

    public OpenLibraryException(String query, Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
