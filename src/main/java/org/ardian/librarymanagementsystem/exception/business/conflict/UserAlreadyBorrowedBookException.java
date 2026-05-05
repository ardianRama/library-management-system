package org.ardian.librarymanagementsystem.exception.business.conflict;

public class UserAlreadyBorrowedBookException extends ConflictException {

  public UserAlreadyBorrowedBookException(Long bookId) {
    super("User has already borrowed this book (bookId = " + bookId + ")");
  }
}
