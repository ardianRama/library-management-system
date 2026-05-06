package org.ardian.librarymanagementsystem.service;

import org.ardian.librarymanagementsystem.dto.BookDetailedDto;
import org.ardian.librarymanagementsystem.dto.BookDto;
import org.ardian.librarymanagementsystem.dto.LibraryBookDto;

import java.util.List;

public interface BookService {

    List<BookDto> searchExternalBooks(String query);

    BookDetailedDto createBook(BookDto dto, int totalCopies);

    BookDetailedDto updateTotalCopies(Long bookId, int totalCopies);

    void deleteBook(Long bookId);

    List<BookDetailedDto> getAllDetailedBooks();

    BookDetailedDto getDetailedBook(Long bookId);

    List<LibraryBookDto> searchLibraryBooks(String query);

    List<LibraryBookDto> getAllBooks();
}
