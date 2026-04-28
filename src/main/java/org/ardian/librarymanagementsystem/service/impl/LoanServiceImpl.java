package org.ardian.librarymanagementsystem.service.impl;

import org.ardian.librarymanagementsystem.exception.BookNotFoundException;
import org.ardian.librarymanagementsystem.exception.InvalidBookUpdateException;
import org.ardian.librarymanagementsystem.model.Book;
import org.ardian.librarymanagementsystem.model.LibraryUser;
import org.ardian.librarymanagementsystem.model.Loan;
import org.ardian.librarymanagementsystem.repository.BookRepository;
import org.ardian.librarymanagementsystem.repository.LibraryUserRepository;
import org.ardian.librarymanagementsystem.repository.LoanRepository;
import org.ardian.librarymanagementsystem.service.LoanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class LoanServiceImpl implements LoanService {

    private final BookRepository bookRepository;
    private final LibraryUserRepository libraryUserRepository;
    private final LoanRepository loanRepository;

    public LoanServiceImpl(BookRepository bookRepository, LibraryUserRepository libraryUserRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.libraryUserRepository = libraryUserRepository;
        this.loanRepository = loanRepository;
    }

    @Transactional
    @Override
    public Loan borrowBook(Long userId, Long bookId) {

        //TODO add user not found exception
        LibraryUser user = libraryUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));

        if (book.getAvailableCopies() <= 0) {
            throw new InvalidBookUpdateException("No available copies");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);

        Loan loan = new Loan();
        loan.setLibraryUser(user);
        loan.setBook(book);
        loan.setBorrowedAt(LocalDateTime.now());

        loanRepository.save(loan);

        return loan;
    }
}
