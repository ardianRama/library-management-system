package org.ardian.librarymanagementsystem.exception.business.conflict;

public class BookDeletionException extends ConflictException {

    public BookDeletionException() {
        super("Book cannot be deleted because copies are currently on loan");
    }
}
