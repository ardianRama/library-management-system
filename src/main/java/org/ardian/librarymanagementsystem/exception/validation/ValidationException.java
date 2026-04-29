package org.ardian.librarymanagementsystem.exception.validation;

public abstract class ValidationException extends RuntimeException {

    protected ValidationException(String message) {
        super(message);
    }
}
