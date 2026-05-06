package org.ardian.librarymanagementsystem.exception.business.notfound;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException() {
        super("User not found");
    }
}
