package org.ardian.librarymanagementsystem.exception;

import lombok.Getter;

@Getter
public class BookAlreadyExistsException extends RuntimeException {

    private final String externalId;

    public BookAlreadyExistsException(String externalId) {
        super("Book with externalId " + externalId + " already exists");
        this.externalId = externalId;
    }
}
