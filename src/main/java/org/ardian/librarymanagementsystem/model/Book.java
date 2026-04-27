package org.ardian.librarymanagementsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@Entity
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String author;
    private String firstPublishYear;
    private String coverUrl;

    @Column(unique = true)
    private String externalId;

    private int totalCopies;
    private int availableCopies;

    @OneToMany(mappedBy = "book")
    private List<Loan> loanHistory;

    public Book(Long id, String title, String author, String firstPublishYear, String coverUrl, String externalId, int totalCopies, int availableCopies, List<Loan> loanHistory) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.firstPublishYear = firstPublishYear;
        this.coverUrl = coverUrl;
        this.externalId = externalId;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
        this.loanHistory = loanHistory;
    }
}
