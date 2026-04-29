package org.ardian.librarymanagementsystem.exception.business.conflict;

import lombok.Getter;

@Getter
public class UserAlreadyExistsException extends ConflictException {

    private final String email;

    public UserAlreadyExistsException(String email) {
        super("User with email " + email + " already exists");
        this.email = email;
    }
}
