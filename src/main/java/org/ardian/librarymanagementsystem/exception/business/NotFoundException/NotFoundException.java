package org.ardian.librarymanagementsystem.exception.business.NotFoundException;

public abstract class NotFoundException extends RuntimeException {

    protected NotFoundException(String message) {
        super(message);
    }
}
