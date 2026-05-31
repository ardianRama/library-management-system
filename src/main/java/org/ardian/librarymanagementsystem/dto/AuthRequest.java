package org.ardian.librarymanagementsystem.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for authentication requests containing email and password.
 */

@Getter
@Setter
public class AuthRequest {
    private String email;
    private String password;
}
