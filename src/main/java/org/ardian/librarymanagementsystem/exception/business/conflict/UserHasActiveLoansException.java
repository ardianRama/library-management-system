package org.ardian.librarymanagementsystem.exception.business.conflict;

public class UserHasActiveLoansException extends ConflictException {

    public UserHasActiveLoansException(Long userId) {
        super("User with id " + userId + " has active loans and cannot be deleted");
    }
}
