package org.ardian.librarymanagementsystem;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("Disabled in CI since contextLoads requires full Spring Boot initialization with database, which is not available in current test configuration")
@SpringBootTest
class LibraryManagementSystemApplicationTests {

    @Test
    void contextLoads() {
    }

}
