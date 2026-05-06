package org.ardian.librarymanagementsystem.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ardian.librarymanagementsystem.dto.LoanDto;
import org.ardian.librarymanagementsystem.exception.business.conflict.BookNotAvailableException;
import org.ardian.librarymanagementsystem.exception.business.conflict.UserAlreadyBorrowedBookException;
import org.ardian.librarymanagementsystem.exception.business.notfound.BookNotFoundException;
import org.ardian.librarymanagementsystem.exception.business.notfound.LibraryUserNotFoundException;
import org.ardian.librarymanagementsystem.exception.business.notfound.LoanNotFoundException;
import org.ardian.librarymanagementsystem.mapper.internal.LoanMapper;
import org.ardian.librarymanagementsystem.model.Book;
import org.ardian.librarymanagementsystem.model.LibraryUser;
import org.ardian.librarymanagementsystem.model.Loan;
import org.ardian.librarymanagementsystem.model.Role;
import org.ardian.librarymanagementsystem.repository.BookRepository;
import org.ardian.librarymanagementsystem.repository.LibraryUserRepository;
import org.ardian.librarymanagementsystem.repository.LoanRepository;
import org.ardian.librarymanagementsystem.service.LoanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class LoanServiceImpl implements LoanService {

    private final BookRepository bookRepository;
    private final LibraryUserRepository libraryUserRepository;
    private final LoanRepository loanRepository;

    public LoanServiceImpl(
            BookRepository bookRepository,
            LibraryUserRepository libraryUserRepository,
            LoanRepository loanRepository
    ) {
        this.bookRepository = bookRepository;
        this.libraryUserRepository = libraryUserRepository;
        this.loanRepository = loanRepository;
    }

    @Transactional
    @Override
    public LoanDto borrowBook(String email, Long bookId) {

        LibraryUser user = getUser(email);
        Book book = getBook(bookId);

        validateNotAlreadyBorrowed(user, bookId);
        validateBookAvailability(book);

        book.setAvailableCopies(book.getAvailableCopies() - 1);

        Loan savedLoan = createLoan(user, book);

        log.info("Book borrowed successfully. loanId={}, user={}, bookId={}",
                savedLoan.getId(), email, bookId);

        return LoanMapper.toDto(savedLoan);
    }

    @Transactional
    @Override
    public LoanDto returnBook(String email, Long bookId) {

        Loan loan = getActiveLoan(email, bookId);

        Book book = loan.getBook();

        book.setAvailableCopies(book.getAvailableCopies() + 1);

        loan.setReturnedAt(LocalDateTime.now());

        Loan savedLoan = loanRepository.save(loan);

        log.info("Book returned successfully. loanId={}, user={}, bookId={}",
                savedLoan.getId(), email, bookId);

        return LoanMapper.toDto(savedLoan);
    }

    @Override
    public List<LoanDto> getAllLoans(String email) {

        LibraryUser user = getUser(email);

        if (user.getRole() == Role.ADMIN) {
            return getAllLoansForAdmin();
        }

        return getAllActiveLoansForUser(email);
    }

    @Override
    public LoanDto getLoanById(Long loanId, String email) {

        LibraryUser user = getUser(email);

        if (user.getRole() == Role.ADMIN) {
            return getLoanForAdmin(loanId);
        }

        return getActiveLoanForUser(user, loanId);
    }

    private LibraryUser getUser(String email) {
        return libraryUserRepository.findByEmail(email)
                .orElseThrow(LibraryUserNotFoundException::new);
    }

    private Book getBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);
    }

    private Loan getActiveLoan(String email, Long bookId) {
        return loanRepository
                .findByLibraryUserEmailAndBookIdAndReturnedAtIsNull(email, bookId)
                .orElseThrow(LoanNotFoundException::new);
    }

    private void validateNotAlreadyBorrowed(LibraryUser user, Long bookId) {
        boolean exists = loanRepository
                .existsByLibraryUserIdAndBookIdAndReturnedAtIsNull(user.getId(), bookId);

        if (exists) {
            throw new UserAlreadyBorrowedBookException();
        }
    }

    private void validateBookAvailability(Book book) {
        if (book.getAvailableCopies() <= 0) {
            throw new BookNotAvailableException();
        }
    }

    private Loan createLoan(LibraryUser user, Book book) {
        Loan loan = new Loan();
        loan.setLibraryUser(user);
        loan.setBook(book);
        loan.setBorrowedAt(LocalDateTime.now());

        return loanRepository.save(loan);
    }

    private List<LoanDto> getAllLoansForAdmin() {

        return loanRepository.findAll()
                .stream()
                .map(LoanMapper::toDto)
                .toList();
    }

    private List<LoanDto> getAllActiveLoansForUser(String email) {

        return loanRepository
                .findAllByLibraryUserEmailAndReturnedAtIsNull(email)
                .stream()
                .map(LoanMapper::toDto)
                .toList();
    }

    private LoanDto getLoanForAdmin(Long loanId) {

        return loanRepository.findById(loanId)
                .map(LoanMapper::toDto)
                .orElseThrow(LoanNotFoundException::new);
    }

    private LoanDto getActiveLoanForUser(LibraryUser user, Long loanId) {

        return loanRepository
                .findByIdAndLibraryUserEmailAndReturnedAtIsNull(loanId, user.getEmail())
                .map(LoanMapper::toDto)
                .orElseThrow(LoanNotFoundException::new);
    }
}
