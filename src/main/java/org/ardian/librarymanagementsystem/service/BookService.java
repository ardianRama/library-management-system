package org.ardian.librarymanagementsystem.service;

import org.ardian.librarymanagementsystem.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> searchBooks(String query);
}
