package org.ardian.librarymanagementsystem.exception.integration;

import lombok.Getter;

/**
 * Exception used to wrap errors that occur when communicating with the OpenLibrary API,
 * such as network issues.
 */

@Getter
public class OpenLibraryException extends IntegrationException  {

    private final String query;
    private static final String DEFAULT_MESSAGE =
            "External book service unavailable";

    public OpenLibraryException(String query, Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
        this.query = query;
    }
}
