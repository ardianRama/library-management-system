package org.ardian.librarymanagementsystem.exception.validation;

public class InvalidBookUpdateException extends ValidationException {

    public InvalidBookUpdateException() {
        super("Total copies cannot be less than borrowed copies");
    }
}
