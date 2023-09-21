package com.nnk.poseidon.Retention;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;

/**
 * TestWithMockUser is the test class for test WithMockUser
 *
 * @author MC
 * @version 1.0
 */
public class TestWithMockUser {

    public void checkUserRoleUser (ResultActions resultActions) throws Exception {
        resultActions
                .andExpect(authenticated().withUsername("user"))
                .andExpect(authenticated().withAuthorities(
                        Arrays.asList(new SimpleGrantedAuthority("USER"))
                ));
    }

    public void checkAdminRoleAdmin (ResultActions resultActions) throws Exception {
        resultActions
                .andExpect(authenticated().withUsername("admin"))
                .andExpect(authenticated().withAuthorities(
                        Arrays.asList(new SimpleGrantedAuthority("ADMIN"))
                ));
    }
}
