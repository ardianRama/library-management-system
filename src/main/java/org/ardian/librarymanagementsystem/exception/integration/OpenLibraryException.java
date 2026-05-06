package org.ardian.librarymanagementsystem.exception.integration;

/**
 * Exception used to wrap errors that occur when communicating with the OpenLibrary API,
 * such as network issues.
 */

public class OpenLibraryException extends IntegrationException  {

    public OpenLibraryException(Throwable cause) {
        super("External book service unavailable", cause);
    }
}
