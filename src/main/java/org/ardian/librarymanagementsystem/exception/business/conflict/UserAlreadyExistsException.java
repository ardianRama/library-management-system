package org.ardian.librarymanagementsystem.exception.business.conflict;

public class UserAlreadyExistsException extends ConflictException {

    public UserAlreadyExistsException(String email) {
        super("Email '" + email + "' is already registered");
    }
}
