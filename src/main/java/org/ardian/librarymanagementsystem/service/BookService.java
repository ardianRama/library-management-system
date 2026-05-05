package org.ardian.librarymanagementsystem.service;

import org.ardian.librarymanagementsystem.dto.BookDetailedDto;
import org.ardian.librarymanagementsystem.dto.BookDto;
import org.ardian.librarymanagementsystem.dto.LibraryBookDto;

import java.util.List;

public interface BookService {

    List<BookDto> searchBooksFromApi(String query);

    BookDetailedDto addBook(BookDto dto, int totalCopies);

    BookDetailedDto updateTotalCopies(Long bookId, int totalCopies);

    void deleteBook(Long bookId);

    List<BookDetailedDto> getAllDetailedBooksFromLibrary();

    List<LibraryBookDto> searchBooksInLibrary(String query);

    List<LibraryBookDto> getAllBooks();
}
