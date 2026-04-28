package org.ardian.librarymanagementsystem.exception;

import lombok.Getter;

@Getter
public class InvalidBookUpdateException extends RuntimeException {

    private final int borrowed;
    private final int totalCopies;

    public InvalidBookUpdateException(int borrowed, int totalCopies) {
        super("Total copies (" + totalCopies +
                ") cannot be less than borrowed copies (" + borrowed + ")");
        this.borrowed = borrowed;
        this.totalCopies = totalCopies;
    }
}
