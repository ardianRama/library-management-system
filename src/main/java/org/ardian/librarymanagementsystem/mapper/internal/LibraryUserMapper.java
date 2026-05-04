package org.ardian.librarymanagementsystem.mapper.internal;

import org.ardian.librarymanagementsystem.dto.LibraryUserDetailedDto;
import org.ardian.librarymanagementsystem.dto.LibraryUserDto;
import org.ardian.librarymanagementsystem.model.LibraryUser;
import org.ardian.librarymanagementsystem.model.Role;

/**
 * Handles mapping between LibraryUser dto and LibraryUser entity for internal use.
 */

public class LibraryUserMapper {

    public static LibraryUser libraryUserDtoToLibraryUserEntity(LibraryUserDto dto, Role role) {
        return LibraryUser.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .role(role)
                .build();
    }

    public static LibraryUserDetailedDto libraryUserEntityToLibraryUserDetailedDto(LibraryUser entity) {
        return LibraryUserDetailedDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .role(entity.getRole())
                .build();
    }
}
