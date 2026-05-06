package org.ardian.librarymanagementsystem.exception.business.conflict;

public class UserAlreadyBorrowedBookException extends ConflictException {

  public UserAlreadyBorrowedBookException() {
    super("User has already borrowed this book");
  }
}
