package org.ardian.librarymanagementsystem.exception.business.ConflictException;

public abstract class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
