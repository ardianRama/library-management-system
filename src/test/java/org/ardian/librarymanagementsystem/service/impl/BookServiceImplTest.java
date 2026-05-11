package org.ardian.librarymanagementsystem.service.impl;

import org.ardian.librarymanagementsystem.client.BookClient;
import org.ardian.librarymanagementsystem.config.OpenLibraryProperties;
import org.ardian.librarymanagementsystem.dto.BookDetailedDto;
import org.ardian.librarymanagementsystem.dto.BookDoc;
import org.ardian.librarymanagementsystem.dto.BookDto;
import org.ardian.librarymanagementsystem.dto.LibraryBookDto;
import org.ardian.librarymanagementsystem.exception.business.conflict.BookAlreadyExistsException;
import org.ardian.librarymanagementsystem.exception.business.conflict.BookDeletionException;
import org.ardian.librarymanagementsystem.exception.business.notfound.BookNotFoundException;
import org.ardian.librarymanagementsystem.exception.validation.InvalidBookUpdateException;
import org.ardian.librarymanagementsystem.exception.validation.InvalidSearchException;
import org.ardian.librarymanagementsystem.mapper.external.OpenLibraryMapper;
import org.ardian.librarymanagementsystem.model.Book;
import org.ardian.librarymanagementsystem.repository.BookRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    private static final String QUERY = "clean code";
    private static final Long BOOK_ID = 1L;
    private static final int TOTAL_COPIES = 5;
    private static final int UPDATED_TOTAL_COPIES = 10;
    private static final int AVAILABLE_COPIES = 5;
    private static final int AVAILABLE_COPIES_AFTER_LOAN = 2;

    @Mock private BookRepository bookRepository;
    @Mock private BookClient bookClient;
    @Mock private OpenLibraryProperties properties;
    @InjectMocks private BookServiceImpl bookService;
    @Captor private ArgumentCaptor<Book> bookCaptor;

    private BookDto dto;
    private Book book;

    @BeforeEach
    void setUp() {
        dto = new BookDto("Clean Code", "Robert C. Martin", 2008, "cover-url", "OL123");
        book = buildAvailableBook(dto, BOOK_ID, TOTAL_COPIES);
    }

    @Test
    void shouldReturnBooksFromExternalApi() {
        BookDoc doc = new BookDoc();

        when(bookClient.fetchBooks(QUERY)).thenReturn(List.of(doc));

        try (MockedStatic<OpenLibraryMapper> mapper = mockStatic(OpenLibraryMapper.class)) {
            mapper.when(() -> OpenLibraryMapper.bookDocToBookDto(doc, properties)).thenReturn(dto);

            List<BookDto> result = bookService.searchExternalBooks(QUERY);

            assertThat(result).hasSize(1);
            assertThat(result.getFirst().getTitle()).isEqualTo(dto.getTitle());
            verify(bookClient).fetchBooks(QUERY);
        }
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void shouldThrowExceptionWhenExternalQueryIsInvalid(String query) {
        assertThatThrownBy(() -> bookService.searchExternalBooks(query))
                .isInstanceOf(InvalidSearchException.class);
        verifyNoInteractions(bookClient);
    }

    @Test
    void shouldReturnLibraryBooksWhenQueryIsValid() {
        when(bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(QUERY, QUERY))
                .thenReturn(List.of(book));

        List<LibraryBookDto> result = bookService.searchLibraryBooks(QUERY);

        assertThat(result).hasSize(1);
        verify(bookRepository).findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(QUERY, QUERY);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void shouldThrowExceptionWhenLibraryQueryIsInvalid(String query) {
        assertThatThrownBy(() -> bookService.searchLibraryBooks(query))
                .isInstanceOf(InvalidSearchException.class);
        verifyNoInteractions(bookRepository);
    }

    @Test
    void shouldCreateBookSuccessfully() {
        when(bookRepository.existsByExternalId(dto.getExternalId())).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDetailedDto result = bookService.createBook(dto, TOTAL_COPIES);
        verify(bookRepository).existsByExternalId(dto.getExternalId());
        verify(bookRepository).save(bookCaptor.capture());
        Book captured = bookCaptor.getValue();

        assertThat(captured.getTitle()).isEqualTo(dto.getTitle());
        assertThat(captured.getAuthor()).isEqualTo(dto.getAuthor());
        assertThat(captured.getTotalCopies()).isEqualTo(TOTAL_COPIES);
        assertThat(captured.getAvailableCopies()).isEqualTo(AVAILABLE_COPIES);
        assertThat(result.getId()).isEqualTo(BOOK_ID);
    }

    @Test
    void shouldThrowExceptionWhenBookAlreadyExists() {
        when(bookRepository.existsByExternalId(dto.getExternalId())).thenReturn(true);

        assertThatThrownBy(() -> bookService.createBook(dto, TOTAL_COPIES))
                .isInstanceOf(BookAlreadyExistsException.class);

        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void shouldUpdateTotalCopiesSuccessfully() {
        Book bookWithLoans = buildBook(dto, BOOK_ID, TOTAL_COPIES, AVAILABLE_COPIES_AFTER_LOAN);
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(bookWithLoans));
        when(bookRepository.save(any(Book.class))).thenReturn(bookWithLoans);

        BookDetailedDto result = bookService.updateTotalCopies(BOOK_ID, UPDATED_TOTAL_COPIES);

        int borrowedCopies = TOTAL_COPIES - AVAILABLE_COPIES_AFTER_LOAN;
        int expectedAvailableCopies = UPDATED_TOTAL_COPIES - borrowedCopies;
        verify(bookRepository).findById(BOOK_ID);
        assertThat(bookWithLoans.getTotalCopies()).isEqualTo(UPDATED_TOTAL_COPIES);
        assertThat(bookWithLoans.getAvailableCopies()).isEqualTo(expectedAvailableCopies);
        assertThat(result.getTotalCopies()).isEqualTo(UPDATED_TOTAL_COPIES);
        assertThat(result.getAvailableCopies()).isEqualTo(expectedAvailableCopies);
    }

    @Test
    void shouldThrowExceptionIfTotalCopiesIsLessThanBorrowedCopies() {
        Book bookWithLoans = buildBook(dto, BOOK_ID, TOTAL_COPIES, AVAILABLE_COPIES_AFTER_LOAN);
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(bookWithLoans));

        assertThatThrownBy(() -> bookService.updateTotalCopies(BOOK_ID, 2))
                .isInstanceOf(InvalidBookUpdateException.class);

        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void shouldDeleteBookSuccessfully() {
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(book));

        bookService.deleteBook(BOOK_ID);

        verify(bookRepository).findById(BOOK_ID);
        verify(bookRepository).delete(bookCaptor.capture());
        assertThat(bookCaptor.getValue().getId()).isEqualTo(BOOK_ID);
    }

    @Test
    void shouldThrowExceptionWhenBookNotFound() {
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.deleteBook(BOOK_ID))
                .isInstanceOf(BookNotFoundException.class);

        verify(bookRepository, never()).delete(any(Book.class));
    }

    @Test
    void shouldThrowExceptionWhenBookHasActiveLoans() {
        Book bookWithLoans = buildBook(dto, BOOK_ID, TOTAL_COPIES, AVAILABLE_COPIES_AFTER_LOAN);
        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(bookWithLoans));

        assertThatThrownBy(() -> bookService.deleteBook(BOOK_ID))
                .isInstanceOf(BookDeletionException.class);

        verify(bookRepository, never()).delete(any(Book.class));
    }

    private Book buildAvailableBook(BookDto dto, Long id, int totalCopies) {
        return buildBook(dto, id, totalCopies, totalCopies);
    }

    private Book buildBook(BookDto dto, Long id, int totalCopies, int availableCopies) {
        Book book = Book.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .firstPublishYear(dto.getFirstPublishYear())
                .coverUrl(dto.getCoverUrl())
                .externalId(dto.getExternalId())
                .totalCopies(totalCopies)
                .availableCopies(availableCopies)
                .build();

        book.setId(id);
        return book;
    }
}
