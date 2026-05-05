package org.ardian.librarymanagementsystem.controller;

import jakarta.validation.Valid;
import org.ardian.librarymanagementsystem.dto.*;
import org.ardian.librarymanagementsystem.security.annotation.IsAdmin;
import org.ardian.librarymanagementsystem.security.annotation.IsUser;
import org.ardian.librarymanagementsystem.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@IsUser
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @IsAdmin
    @GetMapping("/search/external")
    public List<BookDto> searchBooksExternal(@RequestParam String q) {
        return bookService.searchBooksFromApi(q);
    }

    @IsAdmin
    @PostMapping("/import")
    public ResponseEntity<BookDetailedDto> addBook(@Valid @RequestBody ImportBookRequest request) {

        BookDetailedDto saved = bookService.addBook(request.getBook(), request.getTotalCopies());

        return ResponseEntity.ok(saved);
    }

    @IsAdmin
    @PatchMapping("/{bookId}/copies")
    public ResponseEntity<BookDetailedDto> updateCopies(
            @PathVariable Long bookId,
            @Valid @RequestBody UpdateCopiesRequest request
    ) {
        return ResponseEntity.ok(
                bookService.updateTotalCopies(bookId, request.getTotalCopies())
        );
    }

    @IsAdmin
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {

        bookService.deleteBook(bookId);

        return ResponseEntity.noContent().build();
    }

    @IsAdmin
    @GetMapping("/detailed")
    public List<BookDetailedDto> getAllDetailedBooks() {
        return bookService.getAllDetailedBooksFromLibrary();
    }

    @GetMapping("/search")
    public List<LibraryBookDto> searchBooks(@RequestParam String q) {
        return bookService.searchBooksInLibrary(q);
    }

    @GetMapping
    public List<LibraryBookDto> getAllBooks() {
        return bookService.getAllBooks();
    }
}