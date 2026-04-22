package org.ardian.librarymanagementsystem.service;

import org.ardian.librarymanagementsystem.dto.BookDto;
import org.ardian.librarymanagementsystem.model.Book;

import java.util.List;

public interface BookService {

    List<BookDto> searchBooks(String query);

    Book addBook(BookDto dto, int totalCopies);
}
