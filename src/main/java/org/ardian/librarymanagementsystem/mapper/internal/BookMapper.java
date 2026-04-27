package org.ardian.librarymanagementsystem.mapper.internal;

import org.ardian.librarymanagementsystem.dto.BookDto;
import org.ardian.librarymanagementsystem.dto.LibraryBookDto;
import org.ardian.librarymanagementsystem.model.Book;

/**
 * Handles mapping between BookDto and Book entity for internal use.
 */

public class BookMapper {

    public static Book bookDtoToBookEntity(BookDto dto, int totalCopies) {
        return Book.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .firstPublishYear(dto.getFirstPublishYear())
                .coverUrl(dto.getCoverUrl())
                .externalId(dto.getExternalId())
                .totalCopies(totalCopies)
                .build();
    }

    public static LibraryBookDto bookEntityToLibraryBookDto(Book book) {
        return LibraryBookDto.builder().title(book.getTitle())
                .id(book.getId())
                .author(book.getAuthor())
                .firstPublishYear(book.getFirstPublishYear())
                .coverUrl(book.getCoverUrl())
                .availableCopies(book.getTotalCopies())
                .build();
    }
}
