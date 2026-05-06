package org.ardian.librarymanagementsystem.exception.business.conflict;

public class BookAlreadyExistsException extends ConflictException {

    public BookAlreadyExistsException() {
        super("Book already exists");
    }
}
