package org.ardian.librarymanagementsystem.mapper.internal;

import org.ardian.librarymanagementsystem.dto.LibraryUserDto;
import org.ardian.librarymanagementsystem.model.LibraryUser;

/**
 * Handles mapping between LibraryUser dto and LibraryUser entity for internal use.
 */

public class LibraryUserMapper {

    public static LibraryUser libraryUserDtoToLibraryUserEntity(LibraryUserDto dto) {
        return LibraryUser.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();
    }
}
