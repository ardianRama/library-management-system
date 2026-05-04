package org.ardian.librarymanagementsystem.exception.business.conflict;

public class UserHasActiveLoansException extends ConflictException {

    public UserHasActiveLoansException(Long id) {
        super("User with id " + id + " has active loans and cannot be deleted");
    }
}
