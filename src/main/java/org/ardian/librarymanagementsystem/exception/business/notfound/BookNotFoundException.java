package org.ardian.librarymanagementsystem.exception.business.notfound;

public class BookNotFoundException extends NotFoundException {

    public BookNotFoundException(Long bookId) {
        super("Book with id " + bookId + " does not exist");
    }
}
