package org.ardian.librarymanagementsystem.exception.business.conflict;

public class BookAlreadyExistsException extends ConflictException {

    public BookAlreadyExistsException(String externalId) {
        super("Book with externalId " + externalId + " already exists");
    }
}
