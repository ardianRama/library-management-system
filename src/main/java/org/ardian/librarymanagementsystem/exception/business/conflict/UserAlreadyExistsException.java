package org.ardian.librarymanagementsystem.exception.business.conflict;

public class UserAlreadyExistsException extends ConflictException {

    public UserAlreadyExistsException() {
        super("User with this email already exists");
    }
}
