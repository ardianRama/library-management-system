package org.ardian.librarymanagementsystem.exception;

import lombok.Getter;

@Getter
public class BookNotFoundException extends RuntimeException {

    private final Long bookId;
    private final String externalBookId;

    public BookNotFoundException(Long bookId) {
        super("Book with id " + bookId + " does not exist");
        this.bookId = bookId;
        this.externalBookId = null;
    }
}
