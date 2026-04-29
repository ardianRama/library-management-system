package org.ardian.librarymanagementsystem.exception.business.conflict;

import lombok.Getter;

@Getter
public class BookAlreadyExistsException extends ConflictException {

    private final String externalId;

    public BookAlreadyExistsException(String externalId) {
        super("Book with externalId " + externalId + " already exists");
        this.externalId = externalId;
    }
}
