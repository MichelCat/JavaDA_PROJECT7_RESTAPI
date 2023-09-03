package com.nnk.poseidon.enumerator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * RoleTest is the unit test class for enumerating user roles
 *
 * @author MC
 * @version 1.0
 */
@SpringBootTest
class UserRoleTest {

    /**
     * Read USER ROLE
     */
    @Test
    void getUserRole_returnUserRole() {
        // GIVEN
        // WHEN
        assertSame(UserRole.USER, UserRole.valueOf("USER"));
        // THEN
    }

    /**
     * Read ADMIN ROLE
     */
    @Test
    void getAdminRole_returnAdmin() {
        // GIVEN
        // WHEN
        assertSame(UserRole.ADMIN, UserRole.valueOf("ADMIN"));
        // THEN
    }
}
