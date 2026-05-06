package org.ardian.librarymanagementsystem.exception.business.conflict;

public class UserHasActiveLoansException extends ConflictException {

    public UserHasActiveLoansException() {
        super("User has active loans and cannot be deleted");
    }
}
