package org.ardian.librarymanagementsystem.exception.validation;

public class InvalidBookUpdateException extends ValidationException {

    public InvalidBookUpdateException(int borrowed, int totalCopies) {
        super("Total copies cannot be less than borrowed copies. " +
                "Borrowed: " + borrowed + ", total copies: " + totalCopies);
    }
}
