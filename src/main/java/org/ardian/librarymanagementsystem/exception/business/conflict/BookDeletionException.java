package org.ardian.librarymanagementsystem.exception.business.conflict;

public class BookDeletionException extends ConflictException {

    public BookDeletionException(Long bookId) {
        super("Book with id " + bookId + " cannot be deleted because it is currently borrowed");
    }
}
