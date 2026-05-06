package org.ardian.librarymanagementsystem.exception.business.notfound;

public class LibraryUserNotFoundException extends NotFoundException {

    public LibraryUserNotFoundException() {
        super("User not found");
    }
}
