package org.ardian.librarymanagementsystem.dto;

import lombok.Getter;

/**
 * DTO that holds the JWT token returned after successful login.
 */

@Getter
public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }
}
