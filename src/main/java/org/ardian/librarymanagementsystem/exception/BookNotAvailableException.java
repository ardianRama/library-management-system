package org.ardian.librarymanagementsystem.exception;

import lombok.Getter;

@Getter
public class BookNotAvailableException extends RuntimeException {

    private final Long bookId;

    public BookNotAvailableException(Long bookId) {
        super("Book with id " + bookId + " has no available copies");
        this.bookId = bookId;
    }
}
