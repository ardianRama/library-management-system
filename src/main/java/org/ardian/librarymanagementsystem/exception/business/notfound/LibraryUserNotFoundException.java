package org.ardian.librarymanagementsystem.exception.business.notfound;

import lombok.Getter;

@Getter
public class LibraryUserNotFoundException extends NotFoundException {

    private final String email;

    public LibraryUserNotFoundException(String email) {
        super("User not found with email: " + email);
        this.email = email;
    }
}
