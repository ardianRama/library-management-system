package org.ardian.librarymanagementsystem.exception.business.conflict;

import lombok.Getter;

@Getter
public class UserAlreadyBorrowedBookException extends ConflictException {

  private final Long bookId;

  public UserAlreadyBorrowedBookException(Long bookId) {
    super("User has already borrowed this book (bookId = " + bookId + ")");
    this.bookId = bookId;
  }
}
