package org.ardian.librarymanagementsystem.exception.business.conflict;

public class BookNotAvailableException extends ConflictException {

    public BookNotAvailableException(Long bookId) {
        super("Book with id " + bookId + " has no available copies");
    }
}
