package org.ardian.librarymanagementsystem.exception;

public class OpenLibraryException extends RuntimeException {

    public OpenLibraryException(String message) {
        super(message);
    }

  public OpenLibraryException(String message, Throwable cause) {
    super(message, cause);
  }
}
