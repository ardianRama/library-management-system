package org.ardian.librarymanagementsystem.exception.business.notFoundException;

import lombok.Getter;

@Getter
public class LibraryUserNotFoundException extends NotFoundException {

    private final Long userId;

    public LibraryUserNotFoundException(Long userId) {
        super("Library user with id " + userId + " does not exist");
        this.userId = userId;
    }
}
