package org.ardian.librarymanagementsystem.controller;

import org.ardian.librarymanagementsystem.dto.BookDto;
import org.ardian.librarymanagementsystem.dto.LibraryBookDto;
import org.ardian.librarymanagementsystem.dto.UpdateCopiesRequest;
import org.ardian.librarymanagementsystem.dto.ImportBookRequest;
import org.ardian.librarymanagementsystem.model.Book;
import org.ardian.librarymanagementsystem.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * for admin
     */

    //http://localhost:8080/api/books/search/external?q=harry+potter
    //http://localhost:8080/api/books/search/external?q=rowling
    @GetMapping("/search/external")
    public List<BookDto> searchBooks(@RequestParam String q) {
        return bookService.searchBooksFromApi(q);
    }

    //http://localhost:8080/api/books/import
    @PostMapping("/import")
    public ResponseEntity<Book> addBook(@RequestBody ImportBookRequest request) {

        Book saved = bookService.addBook(request.getBook(), request.getTotalCopies());

        return ResponseEntity.ok(saved);
    }

    //http://localhost:8080/api/books/OL82586W/copies
    @PatchMapping("/{externalId}/copies")
    public ResponseEntity<Book> updateCopies(
            @PathVariable String externalId,
            @RequestBody UpdateCopiesRequest request
    ) {
        return ResponseEntity.ok(
                bookService.updateTotalCopies(externalId, request.getTotalCopies())
        );
    }

    /**
     * for user
     */

    //http://localhost:8080/api/books
    @GetMapping
    public List<LibraryBookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    //http://localhost:8080/api/books/search?q=tolkien
    //http://localhost:8080/api/books/search?q=lord+of+the+rings
    @GetMapping("/search")
    public List<LibraryBookDto> searchBooksInLibrary(@RequestParam String q) {
        return bookService.searchBooksInLibrary(q);
    }
}