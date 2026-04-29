package org.ardian.librarymanagementsystem.exception.business.conflictException;

public abstract class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
