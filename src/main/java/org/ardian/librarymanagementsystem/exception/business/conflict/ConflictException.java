package org.ardian.librarymanagementsystem.exception.business.conflict;

public abstract class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
