package org.ardian.librarymanagementsystem.exception.business.conflict;

public class UserHasActiveLoansException extends ConflictException {

  public UserHasActiveLoansException(String message) {
    super(message);
  }
}
