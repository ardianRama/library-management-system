package org.ardian.librarymanagementsystem.service.impl;

import org.ardian.librarymanagementsystem.dto.LibraryUserDetailedDto;
import org.ardian.librarymanagementsystem.dto.LibraryUserDto;
import org.ardian.librarymanagementsystem.model.LibraryUser;
import org.ardian.librarymanagementsystem.model.Role;
import org.ardian.librarymanagementsystem.repository.LibraryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;



@ExtendWith(MockitoExtension.class)
class LibraryUserServiceImplTest {

    private static final Long USER_ID = 1L;
    private static final Long SECOND_USER_ID = 2L;
    private static final Long INVALID_USER_ID = 99L;

    private static final String EMAIL = "john.doe@example.com";
    private static final String SECOND_EMAIL = "jane.doe@example.com";

    private static final String PASSWORD = "password";
    private static final String ENCODED_PASSWORD = "encodedPassword";

    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";

    private static final String SECOND_FIRST_NAME = "Jane";
    private static final String SECOND_LAST_NAME = "Doe";

    @Mock
    private LibraryUserRepository libraryUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LibraryUserServiceImpl libraryUserService;

    private LibraryUserDto userDto;
    private LibraryUser libraryUser;
    private LibraryUserDetailedDto detailedDto;

    @BeforeEach
    void setUp() {
        userDto = new LibraryUserDto(EMAIL, PASSWORD, FIRST_NAME, LAST_NAME);

        libraryUser = createUser(USER_ID, EMAIL, PASSWORD, FIRST_NAME, LAST_NAME);

        detailedDto = createDetailedDto(USER_ID, EMAIL, FIRST_NAME, LAST_NAME);
    }

    private LibraryUser createUser(Long id, String email, String password, String firstName, String lastName) {
        LibraryUser user = new LibraryUser();
        user.setId(id);
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        return user;
    }

    private LibraryUserDetailedDto createDetailedDto(Long id, String email, String firstName, String lastName) {
        return LibraryUserDetailedDto.builder()
                .id(id)
                .email(email)
                .password(PASSWORD)
                .firstName(firstName)
                .lastName(lastName)
                .role(Role.USER)
                .build();
    }
}

