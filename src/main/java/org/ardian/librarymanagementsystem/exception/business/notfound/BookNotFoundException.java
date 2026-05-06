package org.ardian.librarymanagementsystem.exception.business.notfound;

public class BookNotFoundException extends NotFoundException {

    public BookNotFoundException() {
        super("Book not found");
    }
}
