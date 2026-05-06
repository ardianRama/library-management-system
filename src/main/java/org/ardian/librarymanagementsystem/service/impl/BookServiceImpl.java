package org.ardian.librarymanagementsystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ardian.librarymanagementsystem.client.BookClient;
import org.ardian.librarymanagementsystem.config.OpenLibraryProperties;
import org.ardian.librarymanagementsystem.dto.BookDetailedDto;
import org.ardian.librarymanagementsystem.dto.BookDto;
import org.ardian.librarymanagementsystem.dto.LibraryBookDto;
import org.ardian.librarymanagementsystem.exception.business.conflict.BookAlreadyExistsException;
import org.ardian.librarymanagementsystem.exception.business.conflict.BookDeletionException;
import org.ardian.librarymanagementsystem.exception.business.notfound.BookNotFoundException;
import org.ardian.librarymanagementsystem.exception.validation.InvalidBookUpdateException;
import org.ardian.librarymanagementsystem.exception.validation.InvalidSearchException;
import org.ardian.librarymanagementsystem.mapper.external.OpenLibraryMapper;
import org.ardian.librarymanagementsystem.mapper.internal.BookMapper;
import org.ardian.librarymanagementsystem.model.Book;
import org.ardian.librarymanagementsystem.repository.BookRepository;
import org.ardian.librarymanagementsystem.service.BookService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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
    public List<BookDto> searchExternalBooks(String query) {

        if (query == null || query.isBlank()) {
            throw new InvalidSearchException();
        }

        List<BookDto> books = bookClient.fetchBooks(query)
                .stream()
                .map(doc -> OpenLibraryMapper.bookDocToBookDto(doc, properties))
                .toList();

        log.info("OpenLibrary search completed. query={}, results={}",
                query, books.size());

        return books;
    }

    @Override
    public BookDetailedDto createBook(BookDto dto, int totalCopies) {

        if (bookRepository.existsByExternalId(dto.getExternalId())) {

            log.info("Attempt to add duplicate book. externalId={}",
                    dto.getExternalId());

            throw new BookAlreadyExistsException();
        }

        Book book = BookMapper.toEntity(dto, totalCopies);
        book.setAvailableCopies(totalCopies);

        Book saved = bookRepository.save(book);

        log.info("Book created. bookId={}, externalId={}", saved.getId(), saved.getExternalId());

        return BookMapper.toDetailedDto(saved);
    }

    @Override
    public BookDetailedDto updateTotalCopies(Long bookId, int totalCopies) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);

        int borrowed = book.getTotalCopies() - book.getAvailableCopies();

        if (totalCopies < borrowed) {

            log.info(
                    "Rejected totalCopies update. bookId={}, requestedTotalCopies={}, borrowed={}",
                    bookId,
                    totalCopies,
                    borrowed
            );

            throw new InvalidBookUpdateException();
        }

        int oldTotalCopies = book.getTotalCopies();

        book.setTotalCopies(totalCopies);
        book.setAvailableCopies(totalCopies - borrowed);

        Book saved = bookRepository.save(book);

        log.info(
                "Book totalCopies updated. bookId={}, oldTotalCopies={}, newTotalCopies={}",
                saved.getId(),
                oldTotalCopies,
                saved.getTotalCopies()
        );

        return BookMapper.toDetailedDto(saved);
    }

    @Override
    public void deleteBook(Long bookId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);

        if (book.getAvailableCopies() < book.getTotalCopies()) {

            log.warn(
                    "Attempt to delete book with active loans. bookId={}, totalCopies={}, availableCopies={}",
                    bookId,
                    book.getTotalCopies(),
                    book.getAvailableCopies()
            );

            throw new BookDeletionException();
        }

        bookRepository.delete(book);

        log.info(
                "Book deleted successfully. bookId={}, title={}",
                book.getId(),
                book.getTitle()
        );
    }

    @Override
    public List<BookDetailedDto> getAllDetailedBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookMapper::toDetailedDto)
                .toList();
    }

    @Override
    public BookDetailedDto getDetailedBook(Long bookId) {
        return bookRepository.findById(bookId)
                .map(BookMapper::toDetailedDto)
                .orElseThrow(BookNotFoundException::new);
    }

    @Override
    public List<LibraryBookDto> searchLibraryBooks(String query) {

        if (query == null || query.isBlank()) {
            throw new InvalidSearchException();
        }

        return bookRepository
                .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query)
                .stream()
                .map(BookMapper::toLibraryDto)
                .toList();
    }

    @Override
    public List<LibraryBookDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookMapper::toLibraryDto)
                .toList();
    }
}