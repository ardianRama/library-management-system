package org.ardian.librarymanagementsystem.exception.business.notFoundException;

public abstract class NotFoundException extends RuntimeException {

    protected NotFoundException(String message) {
        super(message);
    }
}
