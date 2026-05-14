package org.ardian.librarymanagementsystem.controller;

import jakarta.validation.Valid;
import org.ardian.librarymanagementsystem.dto.*;
import org.ardian.librarymanagementsystem.security.annotation.IsAdmin;
import org.ardian.librarymanagementsystem.security.annotation.IsUserOrAdmin;
import org.ardian.librarymanagementsystem.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@IsAdmin
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/search/external")
    public List<BookDto> searchExternalBooks(@RequestParam String q) {
        return bookService.searchExternalBooks(q);
    }

    @PostMapping("/import")
    public ResponseEntity<BookDetailedDto> importBook(@Valid @RequestBody ImportBookRequest request) {

        BookDetailedDto saved = bookService.createBook(request.getBook(), request.getTotalCopies());

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("api/books/detailed/{bookId}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }

    @PatchMapping("/{bookId}/copies")
    public ResponseEntity<BookDetailedDto> updateCopies(
            @PathVariable Long bookId,
            @Valid @RequestBody UpdateCopiesRequest request
    ) {
        return ResponseEntity.ok(
                bookService.updateTotalCopies(bookId, request.getTotalCopies())
        );
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {

        bookService.deleteBook(bookId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/detailed")
    public List<BookDetailedDto> getAllDetailedBooks() {
        return bookService.getAllDetailedBooks();
    }

    @GetMapping("/detailed/{bookId}")
    public BookDetailedDto getDetailedBook(@PathVariable Long bookId) {
        return bookService.getDetailedBook(bookId);
    }

    @IsUserOrAdmin
    @GetMapping("/search")
    public List<LibraryBookDto> searchLibraryBooks(@RequestParam String q) {
        return bookService.searchLibraryBooks(q);
    }

    @IsUserOrAdmin
    @GetMapping
    public List<LibraryBookDto> getAllBooks() {
        return bookService.getAllBooks();
    }
}