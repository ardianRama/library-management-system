package org.ardian.librarymanagementsystem.controller;

import org.ardian.librarymanagementsystem.dto.BookDto;
import org.ardian.librarymanagementsystem.service.impl.BookServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookServiceImpl bookServiceImpl;

    public BookController(BookServiceImpl bookServiceImpl) {
        this.bookServiceImpl = bookServiceImpl;
    }

    //http://localhost:8080/api/books/search?q=harry+potter
    //http://localhost:8080/api/books/search?q=Rowling
    //http://localhost:8080/api/books/search?q=tolkien
    //http://localhost:8080/api/books/search?q=lord+of+the+rings
    //http://localhost:8080/api/books/search?q=history
    //for admin to view books
    @GetMapping("/search")
    public List<BookDto> search(@RequestParam String q) {
        return bookServiceImpl.searchBooks(q);
    }

}