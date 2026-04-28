package org.ardian.librarymanagementsystem.mapper.internal;

import org.ardian.librarymanagementsystem.dto.LoanDto;
import org.ardian.librarymanagementsystem.model.Loan;

public class LoanMapper {

    public static LoanDto loanEntityToLoanDto(Loan loan) {

        return LoanDto.builder().
                id(loan.getId())
                .borrowedAt(loan.getBorrowedAt())
                .returnedAt(loan.getReturnedAt())
                .bookId(loan.getBook().getId())
                .bookTitle(loan.getBook().getTitle())
                .libraryUserId(loan.getLibraryUser().getId())
                .username(loan.getLibraryUser().getUsername())
                .build();
    }
}
