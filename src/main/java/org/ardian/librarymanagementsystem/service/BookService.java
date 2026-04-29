package org.ardian.librarymanagementsystem.service;

import org.ardian.librarymanagementsystem.dto.BookDetailedDto;
import org.ardian.librarymanagementsystem.dto.BookDto;
import org.ardian.librarymanagementsystem.dto.LibraryBookDto;

import java.util.List;

public interface BookService {

    List<BookDto> searchBooksFromApi(String query);

    BookDetailedDto addBook(BookDto dto, int totalCopies);

    BookDetailedDto updateTotalCopies(Long bookId, int totalCopies);

    List<LibraryBookDto> getAllBooks();

    List<LibraryBookDto> searchBooksInLibrary(String query);

    List<BookDetailedDto> getAllDetailedBooksFromLibrary();

    void deleteBook(Long bookId);
}
