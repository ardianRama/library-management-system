package org.ardian.librarymanagementsystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ardian.librarymanagementsystem.dto.LoanDto;
import org.ardian.librarymanagementsystem.exception.business.conflict.BookNotAvailableException;
import org.ardian.librarymanagementsystem.exception.business.conflict.UserAlreadyBorrowedBookException;
import org.ardian.librarymanagementsystem.exception.business.notfound.BookNotFoundException;
import org.ardian.librarymanagementsystem.exception.business.notfound.LibraryUserNotFoundException;
import org.ardian.librarymanagementsystem.mapper.internal.LoanMapper;
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

@Slf4j
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

    /**
     * for admin
     */


    /**
     * for user
     */

    @Transactional
    @Override
    public LoanDto borrowBook(String email, Long bookId) {

        LibraryUser user = getUser(email);
        Book book = getBook(bookId);

        validateNotAlreadyBorrowed(user, bookId);
        validateBookAvailability(book, bookId, email);

        book.setAvailableCopies(book.getAvailableCopies() - 1);

        Loan savedLoan = createLoan(user, book);

        log.info("Book borrowed successfully. loanId={}, user={}, bookId={}",
                savedLoan.getId(), email, bookId);

        return LoanMapper.loanEntityToLoanDto(savedLoan);
    }

    private LibraryUser getUser(String email) {
        return libraryUserRepository.findByEmail(email)
                .orElseThrow(() -> new LibraryUserNotFoundException(email));
    }

    private Book getBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    private void validateNotAlreadyBorrowed(LibraryUser user, Long bookId) {
        boolean alreadyBorrowed = loanRepository
                .existsByLibraryUserIdAndBookIdAndReturnedAtIsNull(user.getId(), bookId);

        if (alreadyBorrowed) {
            throw new UserAlreadyBorrowedBookException(bookId);
        }
    }

    private void validateBookAvailability(Book book, Long bookId, String email) {
        if (book.getAvailableCopies() <= 0) {
            log.warn("Attempt to borrow unavailable book. user={}, bookId={}", email, bookId);
            throw new BookNotAvailableException(bookId);
        }
    }

    private Loan createLoan(LibraryUser user, Book book) {
        Loan loan = new Loan();
        loan.setLibraryUser(user);
        loan.setBook(book);
        loan.setBorrowedAt(LocalDateTime.now());

        return loanRepository.save(loan);
    }
}
