package com.nnk.poseidon.configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class LocalConfigurationIT {

    @Autowired
    private LocalConfiguration localConfiguration;

    // -----------------------------------------------------------------------------------------------
    // messageSource method
    // -----------------------------------------------------------------------------------------------
    @Test
    void messageSource_normal_returnMessageSource() {
        // GIVEN
        Locale locale = Locale.ENGLISH;
        // WHEN
        String result = localConfiguration.messageSource()
                .getMessage("label.curvePoint.titleList", null, locale);

        // THEN
        assertThat(result).isEqualTo("Curve Point List");
    }
}
