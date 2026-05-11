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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

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
    private static final int TOTAL_COPIES = 5;
    private static final int AVAILABLE_COPIES = 2;
    private static final Long BOOK_ID = 1L;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookClient bookClient;

    @Mock
    private OpenLibraryProperties properties;

    @InjectMocks
    private BookServiceImpl bookService;

    @Captor
    private ArgumentCaptor<Book> bookCaptor;

    @Test
    void shouldReturnBooksFromExternalApi() {

        BookDoc doc = new BookDoc();

        BookDto dto = buildBookDto();

        when(bookClient.fetchBooks(QUERY))
                .thenReturn(List.of(doc));

        try (MockedStatic<OpenLibraryMapper> mapper =
                     mockStatic(OpenLibraryMapper.class)) {

            mapper.when(() ->
                            OpenLibraryMapper.bookDocToBookDto(doc, properties))
                    .thenReturn(dto);

            List<BookDto> result = bookService.searchExternalBooks(QUERY);

            assertThat(result).hasSize(1);
            assertThat(result.getFirst().getTitle())
                    .isEqualTo(dto.getTitle());

            verify(bookClient).fetchBooks(QUERY);
        }
    }

    @Test
    void shouldThrowExceptionWhenExternalQueryIsInvalid() {

        assertThatThrownBy(() -> bookService.searchExternalBooks(""))
                .isInstanceOf(InvalidSearchException.class);

        assertThatThrownBy(() -> bookService.searchExternalBooks(null))
                .isInstanceOf(InvalidSearchException.class);

        verifyNoInteractions(bookClient);
    }

    @Test
    void shouldReturnLibraryBooksWhenQueryIsValid() {

        Book book = buildBookEntity(buildBookDto(), TOTAL_COPIES);
        book.setId(BOOK_ID);

        when(bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(QUERY, QUERY))
                .thenReturn(List.of(book));

        List<LibraryBookDto> result = bookService.searchLibraryBooks(QUERY);

        verify(bookRepository)
                .findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(QUERY, QUERY);

        assertThat(result).hasSize(1);
    }

    @Test
    void shouldThrowExceptionWhenLibraryQueryIsInvalid() {

        assertThatThrownBy(() -> bookService.searchLibraryBooks(""))
                .isInstanceOf(InvalidSearchException.class);

        assertThatThrownBy(() -> bookService.searchLibraryBooks(null))
                .isInstanceOf(InvalidSearchException.class);

        verifyNoInteractions(bookRepository);
    }

    @Test
    void shouldCreateBookSuccessfully() {

        BookDto dto = buildBookDto();

        Book savedBook = buildBookEntity(dto, TOTAL_COPIES);
        savedBook.setId(BOOK_ID);

        when(bookRepository.existsByExternalId(dto.getExternalId()))
                .thenReturn(false);

        when(bookRepository.save(any(Book.class)))
                .thenReturn(savedBook);

        BookDetailedDto result = bookService.createBook(dto, TOTAL_COPIES);

        verify(bookRepository).existsByExternalId(dto.getExternalId());
        verify(bookRepository).save(bookCaptor.capture());

        Book capturedBook = bookCaptor.getValue();

        assertThat(capturedBook.getTitle()).isEqualTo(dto.getTitle());
        assertThat(capturedBook.getAuthor()).isEqualTo(dto.getAuthor());
        assertThat(capturedBook.getExternalId()).isEqualTo(dto.getExternalId());
        assertThat(capturedBook.getTotalCopies()).isEqualTo(TOTAL_COPIES);
        assertThat(capturedBook.getAvailableCopies()).isEqualTo(TOTAL_COPIES);

        assertThat(result.getId()).isEqualTo(BOOK_ID);
        assertThat(result.getExternalId()).isEqualTo(dto.getExternalId());
        assertThat(result.getAvailableCopies()).isEqualTo(TOTAL_COPIES);
    }

    @Test
    void shouldThrowExceptionWhenBookAlreadyExists() {

        BookDto dto = buildBookDto();

        when(bookRepository.existsByExternalId(dto.getExternalId()))
                .thenReturn(true);

        assertThatThrownBy(() -> bookService.createBook(dto, TOTAL_COPIES))
                .isInstanceOf(BookAlreadyExistsException.class);

        verify(bookRepository).existsByExternalId(dto.getExternalId());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void shouldUpdateTotalCopiesSuccessfully() {

        BookDto dto = buildBookDto();

        Book existingBook = buildBookEntity(dto, TOTAL_COPIES);
        existingBook.setId(BOOK_ID);
        existingBook.setAvailableCopies(AVAILABLE_COPIES);

        when(bookRepository.findById(BOOK_ID))
                .thenReturn(Optional.of(existingBook));

        when(bookRepository.save(any(Book.class)))
                .thenReturn(existingBook);

        BookDetailedDto result = bookService.updateTotalCopies(BOOK_ID, 10);

        verify(bookRepository).findById(BOOK_ID);
        verify(bookRepository).save(any(Book.class));

        assertThat(existingBook.getTotalCopies()).isEqualTo(10);
        assertThat(existingBook.getAvailableCopies()).isEqualTo(7);

        assertThat(result.getTotalCopies()).isEqualTo(10);
        assertThat(result.getAvailableCopies()).isEqualTo(7);
    }

    @Test
    void shouldThrowExceptionIfTotalCopiesIsLessThanAvailableCopies() {

        BookDto dto = buildBookDto();

        Book existingBook = buildBookEntity(dto, TOTAL_COPIES);
        existingBook.setId(BOOK_ID);
        existingBook.setAvailableCopies(AVAILABLE_COPIES);

        when(bookRepository.findById(BOOK_ID))
                .thenReturn(Optional.of(existingBook));

        assertThatThrownBy(() -> bookService.updateTotalCopies(BOOK_ID, 2))
                .isInstanceOf(InvalidBookUpdateException.class);

        verify(bookRepository).findById(BOOK_ID);
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void shouldDeleteBookSuccessfully() {

        Book book = buildBookEntity(buildBookDto(), TOTAL_COPIES);
        book.setId(BOOK_ID);
        book.setAvailableCopies(5);

        when(bookRepository.findById(BOOK_ID))
                .thenReturn(Optional.of(book));

        bookService.deleteBook(BOOK_ID);

        verify(bookRepository).findById(BOOK_ID);
        verify(bookRepository).delete(bookCaptor.capture());

        Book capturedBook = bookCaptor.getValue();

        assertThat(capturedBook.getId()).isEqualTo(BOOK_ID);
        assertThat(capturedBook.getTitle()).isEqualTo(book.getTitle());
    }

    @Test
    void shouldThrowExceptionWhenBookNotFound() {

        when(bookRepository.findById(BOOK_ID))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.deleteBook(BOOK_ID))
                .isInstanceOf(BookNotFoundException.class);

        verify(bookRepository).findById(BOOK_ID);
        verify(bookRepository, never()).delete(any(Book.class));
    }

    @Test
    void shouldThrowExceptionWhenBookHasActiveLoans() {

        Book book = buildBookEntity(buildBookDto(), TOTAL_COPIES);
        book.setId(BOOK_ID);
        book.setAvailableCopies(AVAILABLE_COPIES);

        when(bookRepository.findById(BOOK_ID))
                .thenReturn(Optional.of(book));

        assertThatThrownBy(() -> bookService.deleteBook(BOOK_ID))
                .isInstanceOf(BookDeletionException.class);

        verify(bookRepository).findById(BOOK_ID);
        verify(bookRepository, never()).delete(any(Book.class));
    }

    private BookDto buildBookDto() {
        return new BookDto(
                "Clean Code",
                "Robert C. Martin",
                2008,
                "cover-url",
                "OL123"
        );
    }

    private Book buildBookEntity(BookDto dto, int totalCopies) {
        return Book.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .firstPublishYear(dto.getFirstPublishYear())
                .coverUrl(dto.getCoverUrl())
                .externalId(dto.getExternalId())
                .totalCopies(totalCopies)
                .availableCopies(totalCopies)
                .build();
    }
}
