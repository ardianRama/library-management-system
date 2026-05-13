package org.ardian.librarymanagementsystem.service.impl;

import org.ardian.librarymanagementsystem.dto.LoanDto;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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

    private void mockLoanDoesNotExist(Long userId, Long bookId) {
        when(loanRepository.existsByLibraryUserIdAndBookIdAndReturnedAtIsNull(userId, bookId))
                .thenReturn(false);
    }

    private void mockSaveLoan(Loan loan) {
        when(loanRepository.save(any(Loan.class)))
                .thenReturn(loan);
    }
}
