package org.ardian.librarymanagementsystem.exception;

/**
 * Exception used to wrap errors that occur when communicating with the OpenLibrary API,
 * such as network issues.
 */

public class OpenLibraryException extends RuntimeException {

    public OpenLibraryException(String message) {
        super(message);
    }

  public OpenLibraryException(String message, Throwable cause) {
    super(message, cause);
  }
}
