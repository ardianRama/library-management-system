package org.ardian.librarymanagementsystem.exception.business.notfound;

public class LibraryUserNotFoundException extends NotFoundException {

    public LibraryUserNotFoundException(Long userId) {
        super("User not found with id: " + userId);
    }

    public LibraryUserNotFoundException(String email) {
        super("User not found with email: " + email);
    }
}
