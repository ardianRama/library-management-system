package org.ardian.librarymanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String authors;
    private String firstPublishYear;
    private String coverUrl;
    private String externalId;

    @OneToMany(mappedBy = "book")
    private List<Loan> loanHistory;

    public Book(String title, String authors, String firstPublishYear, String coverUrl, String externalId) {
        this.title = title;
        this.authors = authors;
        this.firstPublishYear = firstPublishYear;
        this.coverUrl = coverUrl;
        this.externalId = externalId;
    }
}
