package org.ardian.librarymanagementsystem.service.impl;

import org.ardian.librarymanagementsystem.dto.LoanDto;
import org.ardian.librarymanagementsystem.exception.business.conflict.BookNotAvailableException;
import org.ardian.librarymanagementsystem.exception.business.conflict.UserHasActiveLoansException;
import org.ardian.librarymanagementsystem.exception.business.notfound.BookNotFoundException;
import org.ardian.librarymanagementsystem.exception.business.notfound.LoanNotFoundException;
import org.ardian.librarymanagementsystem.exception.business.notfound.UserNotFoundException;
import org.ardian.librarymanagementsystem.mapper.internal.LoanMapper;
import org.ardian.librarymanagementsystem.model.Book;
import org.ardian.librarymanagementsystem.model.LibraryUser;
import org.ardian.librarymanagementsystem.model.Loan;
import org.ardian.librarymanagementsystem.model.Role;
import org.ardian.librarymanagementsystem.repository.BookRepository;
import org.ardian.librarymanagementsystem.repository.LibraryUserRepository;
import org.ardian.librarymanagementsystem.repository.LoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceImplTest {

    private static final Long USER_ID = 1L;
    private static final Long BOOK_ID = 1L;
    private static final Long LOAN_ID = 1L;
    private static final Long INVALID_ID = 99L;

    private static final String EMAIL = "john.doe@example.com";
    private static final String BOOK_TITLE = "Test Book";
    private static final String ADMIN_EMAIL = "admin@example.com";

    private static final int AVAILABLE_COPIES = 3;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private LibraryUserRepository libraryUserRepository;

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanServiceImpl loanService;

    private LibraryUser user;
    private LibraryUser adminUser;
    private Book book;
    private Loan activeLoan;
    private LoanDto loanDto;

    @BeforeEach
    void setUp() {
        user = createUser(USER_ID, EMAIL, Role.USER);
        adminUser = createUser(2L, ADMIN_EMAIL, Role.ADMIN);
        book = createBook(BOOK_ID, AVAILABLE_COPIES);
        activeLoan = createActiveLoan(LOAN_ID, user, book);
        loanDto = createLoanDto(LOAN_ID);
    }

    @Test
    void shouldBorrowBookSuccessfully() {
        mockFindUser(EMAIL, user);
        mockFindBook(BOOK_ID, book);
        mockLoanDoesNotExist(USER_ID, BOOK_ID);
        mockSaveLoan(activeLoan);

        try (MockedStatic<LoanMapper> mapperMock = mockMapper()) {
            mapperMock.when(() -> LoanMapper.toDto(activeLoan))
                    .thenReturn(loanDto);

            LoanDto result = loanService.borrowBook(EMAIL, BOOK_ID);

            assertThat(result).isEqualTo(loanDto);
            assertThat(book.getAvailableCopies()).isEqualTo(AVAILABLE_COPIES - 1);
            verify(loanRepository).save(any(Loan.class));
        }
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenBorrowingWithUnknownEmail() {
        when(libraryUserRepository.findByEmail(EMAIL))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> loanService.borrowBook(EMAIL, BOOK_ID))
                .isInstanceOf(UserNotFoundException.class);

        verify(loanRepository, never()).save(any());
    }

    @Test
    void shouldThrowBookNotFoundExceptionWhenBorrowingNonExistentBook() {
        mockFindUser(EMAIL, user);
        when(bookRepository.findById(INVALID_ID))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> loanService.borrowBook(EMAIL, INVALID_ID))
                .isInstanceOf(BookNotFoundException.class);

        verify(loanRepository, never()).save(any());
    }

    @Test
    void shouldThrowUserHasActiveLoansExceptionWhenBookAlreadyBorrowed() {
        mockFindUser(EMAIL, user);
        mockFindBook(BOOK_ID, book);
        when(loanRepository.existsByLibraryUserIdAndBookIdAndReturnedAtIsNull(USER_ID, BOOK_ID))
                .thenReturn(true);

        assertThatThrownBy(() -> loanService.borrowBook(EMAIL, BOOK_ID))
                .isInstanceOf(UserHasActiveLoansException.class);

        verify(loanRepository, never()).save(any());
    }

    @Test
    void shouldThrowBookNotAvailableExceptionWhenNoCopiesLeft() {
        mockFindUser(EMAIL, user);
        Book unavailableBook = createBook(BOOK_ID, 0);
        mockFindBook(BOOK_ID, unavailableBook);
        mockLoanDoesNotExist(USER_ID, BOOK_ID);

        assertThatThrownBy(() -> loanService.borrowBook(EMAIL, BOOK_ID))
                .isInstanceOf(BookNotAvailableException.class);

        verify(loanRepository, never()).save(any());
    }

    @Test
    void shouldReturnBookSuccessfully() {
        mockFindActiveLoan(EMAIL, BOOK_ID, activeLoan);
        when(loanRepository.save(activeLoan)).thenReturn(activeLoan);

        try (MockedStatic<LoanMapper> mapperMock = mockMapper()) {
            mapperMock.when(() -> LoanMapper.toDto(activeLoan))
                    .thenReturn(loanDto);

            LoanDto result = loanService.returnBook(EMAIL, BOOK_ID);

            assertThat(result).isEqualTo(loanDto);
            assertThat(book.getAvailableCopies()).isEqualTo(AVAILABLE_COPIES + 1);
            assertThat(activeLoan.getReturnedAt()).isNotNull();
            verify(loanRepository).save(activeLoan);
        }
    }

    @Test
    void shouldThrowLoanNotFoundExceptionWhenNoActiveLoanExists() {
        when(loanRepository.findByLibraryUserEmailAndBookIdAndReturnedAtIsNull(EMAIL, BOOK_ID))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> loanService.returnBook(EMAIL, BOOK_ID))
                .isInstanceOf(LoanNotFoundException.class);

        verify(loanRepository, never()).save(any());
    }

    @Test
    void shouldReturnAllLoansForAdmin() {
        mockFindUser(ADMIN_EMAIL, adminUser);
        Loan secondLoan = createActiveLoan(2L, adminUser, book);
        LoanDto secondDto = createLoanDto(2L);

        when(loanRepository.findAll())
                .thenReturn(List.of(activeLoan, secondLoan));

        try (MockedStatic<LoanMapper> mapperMock = mockMapper()) {
            mapperMock.when(() -> LoanMapper.toDto(activeLoan)).thenReturn(loanDto);
            mapperMock.when(() -> LoanMapper.toDto(secondLoan)).thenReturn(secondDto);

            List<LoanDto> result = loanService.getAllLoans(ADMIN_EMAIL);

            assertThat(result)
                    .hasSize(2)
                    .containsExactly(loanDto, secondDto);
        }
    }

    @Test
    void shouldReturnOnlyActiveLoansForRegularUser() {
        mockFindUser(EMAIL, user);

        when(loanRepository.findAllByLibraryUserEmailAndReturnedAtIsNull(EMAIL))
                .thenReturn(List.of(activeLoan));

        try (MockedStatic<LoanMapper> mapperMock = mockMapper()) {
            mapperMock.when(() -> LoanMapper.toDto(activeLoan)).thenReturn(loanDto);

            List<LoanDto> result = loanService.getAllLoans(EMAIL);

            assertThat(result)
                    .hasSize(1)
                    .containsExactly(loanDto);
        }
    }

    @Test
    void shouldReturnEmptyListWhenUserHasNoActiveLoans() {
        mockFindUser(EMAIL, user);

        when(loanRepository.findAllByLibraryUserEmailAndReturnedAtIsNull(EMAIL))
                .thenReturn(List.of());

        List<LoanDto> result = loanService.getAllLoans(EMAIL);

        assertThat(result).isEmpty();
    }

    private LibraryUser createUser(Long id, String email, Role role) {
        LibraryUser u = new LibraryUser();
        u.setId(id);
        u.setEmail(email);
        u.setRole(role);
        return u;
    }

    private Book createBook(Long id, int availableCopies) {
        Book b = new Book();
        b.setId(id);
        b.setAvailableCopies(availableCopies);
        return b;
    }

    private Loan createActiveLoan(Long id, LibraryUser user, Book book) {
        Loan loan = new Loan();
        loan.setId(id);
        loan.setLibraryUser(user);
        loan.setBook(book);
        loan.setBorrowedAt(LocalDateTime.now());
        loan.setReturnedAt(null);
        return loan;
    }

    private LoanDto createLoanDto(Long id) {
        return LoanDto.builder()
                .id(id)
                .borrowedAt(LocalDateTime.now())
                .returnedAt(null)
                .bookId(BOOK_ID)
                .bookTitle(BOOK_TITLE)
                .libraryUserId(USER_ID)
                .email(EMAIL)
                .build();
    }

    private MockedStatic<LoanMapper> mockMapper() {
        return mockStatic(LoanMapper.class);
    }

    private void mockFindUser(String email, LibraryUser user) {
        when(libraryUserRepository.findByEmail(email))
                .thenReturn(Optional.of(user));
    }

    private void mockFindBook(Long bookId, Book book) {
        when(bookRepository.findById(bookId))
                .thenReturn(Optional.of(book));
    }

    private void mockFindActiveLoan(String email, Long bookId, Loan loan) {
        when(loanRepository.findByLibraryUserEmailAndBookIdAndReturnedAtIsNull(email, bookId))
                .thenReturn(Optional.of(loan));
    }

    private void mockLoanDoesNotExist(Long userId, Long bookId) {
        when(loanRepository.existsByLibraryUserIdAndBookIdAndReturnedAtIsNull(userId, bookId))
                .thenReturn(false);
    }

    private void mockSaveLoan(Loan loan) {
        when(loanRepository.save(any(Loan.class)))
                .thenReturn(loan);
    }
}
