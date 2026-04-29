package org.ardian.librarymanagementsystem.exception.business.conflict;

import lombok.Getter;

@Getter
public class BookDeletionException extends ConflictException {

    private final Long bookId;

    public BookDeletionException(Long bookId) {
        super("Book with id " + bookId + " cannot be deleted because it is currently borrowed");
        this.bookId = bookId;
    }
}
