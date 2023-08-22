package com.nnk.poseidon.configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class PoseidonSecurityConfigurationIT {

    @Autowired
    private PoseidonSecurityConfiguration poseidonSecurityConfiguration;

    // -----------------------------------------------------------------------------------------------
    // passwordEncoder method
    // -----------------------------------------------------------------------------------------------
    @Test
    void passwordEncoder_password_returnEncryptedPassword() {
        // GIVEN
        PasswordEncoder encoder = poseidonSecurityConfiguration.passwordEncoder();
        // WHEN
        String password = encoder.encode("test");
        // THEN
        assertThat(encoder.matches("test", password)).isTrue();
    }
}
