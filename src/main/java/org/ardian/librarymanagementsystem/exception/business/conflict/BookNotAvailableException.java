package org.ardian.librarymanagementsystem.exception.business.conflict;

public class BookNotAvailableException extends ConflictException {

    public BookNotAvailableException() {
        super("No available copies for this book");
    }
}
