package org.ardian.librarymanagementsystem.exception.integration;

public abstract class IntegrationException extends RuntimeException {

    protected IntegrationException(String message) {
        super(message);
    }

    protected IntegrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
