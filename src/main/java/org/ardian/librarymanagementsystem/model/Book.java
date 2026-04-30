package org.ardian.librarymanagementsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private Integer firstPublishYear;

    private String coverUrl;

    @Column(unique = true, nullable = false)
    private String externalId;

    @Column(nullable = false)
    private int totalCopies;

    private int availableCopies;

    @OneToMany(mappedBy = "book")
    private List<Loan> loanHistory;

    @Builder
    public Book(String title, String author, Integer firstPublishYear, String coverUrl, String externalId, int totalCopies, int availableCopies) {
        this.title = title;
        this.author = author;
        this.firstPublishYear = firstPublishYear;
        this.coverUrl = coverUrl;
        this.externalId = externalId;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }
}
