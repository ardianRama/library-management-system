package org.ardian.librarymanagementsystem.mapper.external;

import org.ardian.librarymanagementsystem.config.OpenLibraryProperties;
import org.ardian.librarymanagementsystem.dto.BookDoc;
import org.ardian.librarymanagementsystem.dto.BookDto;

/**
 * Handles mapping of data from the external OpenLibrary API into internal BookDto format.
 */

public class OpenLibraryMapper {

    public static BookDto bookDocToBookDto(BookDoc doc, OpenLibraryProperties properties) {

        String authors = getAuthor(doc);
        String coverUrl = getCoverUrl(doc.getCoverId(), properties);
        String externalId = normalizeExternalId(doc.getKey());

        return new BookDto(
                doc.getTitle(),
                authors,
                doc.getFirstPublishYear(),
                coverUrl,
                externalId
        );
    }

    private static String getAuthor(BookDoc doc) {
        if (doc.getAuthorName() == null || doc.getAuthorName().isEmpty()) {
            return "Unknown";
        }
        return doc.getAuthorName().getFirst();
    }

    private static String getCoverUrl(Integer coverId, OpenLibraryProperties properties) {
        if (coverId == null) {
            return null;
        }

        return properties.getCoverBaseUrl() + coverId + "-M.jpg";
    }

    private static String normalizeExternalId(String key) {
        if (key == null) return null;
        return key.replace("/works/", "");
    }
}
