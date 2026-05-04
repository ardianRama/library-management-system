package org.ardian.librarymanagementsystem.exception.business.notfound;

import lombok.Getter;

@Getter
public class LibraryUserNotFoundException extends NotFoundException {

    private final String email;
    private final Long id;

    public LibraryUserNotFoundException(Long id) {
        super("User not found with id: " + id);
        this.id = id;
        this.email = null;
    }

    public LibraryUserNotFoundException(String email) {
        super("User not found with email: " + email);
        this.email = email;
        this.id = null;
    }
}
