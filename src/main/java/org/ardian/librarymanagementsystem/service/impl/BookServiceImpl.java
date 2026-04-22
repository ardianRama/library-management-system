package org.ardian.librarymanagementsystem.service.impl;

import org.ardian.librarymanagementsystem.client.BookClient;
import org.ardian.librarymanagementsystem.config.OpenLibraryProperties;
import org.ardian.librarymanagementsystem.dto.BookDto;
import org.ardian.librarymanagementsystem.exception.InvalidSearchException;
import org.ardian.librarymanagementsystem.mapper.external.OpenLibraryMapper;
import org.ardian.librarymanagementsystem.mapper.internal.BookMapper;
import org.ardian.librarymanagementsystem.model.Book;
import org.ardian.librarymanagementsystem.repository.BookRepository;
import org.ardian.librarymanagementsystem.service.BookService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookClient bookClient;
    private final OpenLibraryProperties properties;
    private final BookRepository bookRepository;

    public BookServiceImpl(BookClient bookClient, OpenLibraryProperties properties, BookRepository bookRepository) {
        this.bookClient = bookClient;
        this.properties = properties;
        this.bookRepository = bookRepository;
    }

    @Override
    @Cacheable(value = "books", key = "#query")
    public List<BookDto> searchBooks(String query) {

        if (query == null || query.isBlank()) {
            throw new InvalidSearchException("Search query cannot be empty");
        }

        return bookClient.fetchBooks(query)
                .stream()
                .map(doc -> OpenLibraryMapper.bookDocToBookDto(doc, properties))
                .toList();
    }

    @Override
    public Book addBook(BookDto dto, int totalCopies) {

        Book book = BookMapper.bookDtoToBookEntity(dto, totalCopies);

        return bookRepository.save(book);
    }
}