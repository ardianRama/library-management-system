package org.ardian.librarymanagementsystem.service.impl;

import org.ardian.librarymanagementsystem.client.BookClient;
import org.ardian.librarymanagementsystem.config.OpenLibraryProperties;
import org.ardian.librarymanagementsystem.dto.BookDetailedDto;
import org.ardian.librarymanagementsystem.dto.BookDto;
import org.ardian.librarymanagementsystem.exception.business.conflict.BookAlreadyExistsException;
import org.ardian.librarymanagementsystem.model.Book;
import org.ardian.librarymanagementsystem.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    private static final int TOTAL_COPIES = 5;

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
    void shouldCreateBookSuccessfully() {

        BookDto dto = buildBookDto();

        Book savedBook = buildBookEntity(dto, TOTAL_COPIES);
        savedBook.setId(1L);

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

        assertThat(result.getId()).isEqualTo(1L);
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
