package org.ardian.librarymanagementsystem.exception.business.NotFoundException;

import lombok.Getter;

@Getter
public class BookNotFoundException extends NotFoundException {

    private final Long bookId;

    public BookNotFoundException(Long bookId) {
        super("Book with id " + bookId + " does not exist");
        this.bookId = bookId;
    }
}
