package org.ardian.librarymanagementsystem.controller;

import jakarta.validation.Valid;
import org.ardian.librarymanagementsystem.dto.AuthRequest;
import org.ardian.librarymanagementsystem.dto.AuthResponse;
import org.ardian.librarymanagementsystem.dto.LibraryUserDto;
import org.ardian.librarymanagementsystem.security.CustomUserDetailsService;
import org.ardian.librarymanagementsystem.security.JwtService;
import org.ardian.librarymanagementsystem.security.annotation.IsAnonymousOrAdmin;
import org.ardian.librarymanagementsystem.service.LibraryUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final LibraryUserService libraryUserService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthController(LibraryUserService libraryUserService,
                          AuthenticationManager authenticationManager,
                          CustomUserDetailsService userDetailsService,
                          JwtService jwtService) {
        this.libraryUserService = libraryUserService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @IsAnonymousOrAdmin
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody LibraryUserDto dto) {
        libraryUserService.registerUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}