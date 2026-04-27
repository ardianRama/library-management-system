package org.ardian.librarymanagementsystem.service;

import org.ardian.librarymanagementsystem.dto.BookDetailedDto;
import org.ardian.librarymanagementsystem.dto.BookDto;
import org.ardian.librarymanagementsystem.dto.LibraryBookDto;
import org.ardian.librarymanagementsystem.model.Book;

import java.util.List;

public interface BookService {

    List<BookDto> searchBooksFromApi(String query);

    Book addBook(BookDto dto, int totalCopies);

    Book updateTotalCopies(String externalId, int totalCopies);

    List<LibraryBookDto> getAllBooks();

    List<LibraryBookDto> searchBooksInLibrary(String query);

    List<BookDetailedDto> getAllDetailedBooksFromLibrary();
}
