package org.ardian.librarymanagementsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Loan {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime borrowedAt;
    private LocalDateTime  returnedAt;

    @JoinColumn
    @ManyToOne
    private Book book;

    @JoinColumn
    @ManyToOne
    private LibraryUser libraryUser;

    public Loan(LocalDateTime borrowedAt, LocalDateTime returnedAt, Book book, LibraryUser libraryUser) {
        this.borrowedAt = borrowedAt;
        this.returnedAt = returnedAt;
        this.book = book;
        this.libraryUser = libraryUser;
    }
}
