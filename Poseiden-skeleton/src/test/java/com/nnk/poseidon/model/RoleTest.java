package com.nnk.poseidon.model;

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
class RoleTest {

    /**
     * Read USER ROLE
     */
    @Test
    void getUserRole_returnUserRole() {
        // GIVEN
        // WHEN
        assertSame(Role.USER, Role.valueOf("USER"));
        // THEN
    }

    /**
     * Read ADMIN ROLE
     */
    @Test
    void getAdminRole_returnAdmin() {
        // GIVEN
        // WHEN
        assertSame(Role.ADMIN, Role.valueOf("ADMIN"));
        // THEN
    }
}
