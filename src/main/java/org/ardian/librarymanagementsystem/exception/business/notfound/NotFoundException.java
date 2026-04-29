package org.ardian.librarymanagementsystem.exception.business.notfound;

public abstract class NotFoundException extends RuntimeException {

    protected NotFoundException(String message) {
        super(message);
    }
}
