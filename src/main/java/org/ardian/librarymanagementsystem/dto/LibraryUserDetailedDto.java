package org.ardian.librarymanagementsystem.dto;

import lombok.Builder;
import lombok.Getter;
import org.ardian.librarymanagementsystem.model.Role;

/**
 * Represent a detailed library user in the database visible for the admin.
 */

@Getter
@Builder
public class LibraryUserDetailedDto {

    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Role role ;
}
