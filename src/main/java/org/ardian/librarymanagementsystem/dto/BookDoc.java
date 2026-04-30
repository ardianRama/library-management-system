package org.ardian.librarymanagementsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

/**
 * Dto representing a book returned by the OpenLibrary API.
 */

@Getter
public class BookDoc {

    private String title;

    @JsonProperty("author_name")
    private List<String> authorName;

    @JsonProperty("first_publish_year")
    private Integer firstPublishYear;

    @JsonProperty("cover_i")
    private Integer coverId;

    private String key;
}