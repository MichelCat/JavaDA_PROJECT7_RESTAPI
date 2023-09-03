package com.nnk.poseidon.business;

import com.nnk.poseidon.enumerator.ApplicationLanguage;
import com.nnk.poseidon.exception.MyException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Locale;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * LanguageBusinessIT is a class of integration tests on language change service.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class LanguageBusinessIT {

    @Autowired
    private LanguageBusiness languageBusiness;
    @Autowired
    private MessageSource messageSource;

    private TestMessageSource testMessageSource;

    @BeforeEach
    public void setUpBefore() {
        testMessageSource = new TestMessageSource(messageSource);
    }

    @AfterEach
    public void setUpAfter() {
        // Reset the locale to its default value
        LocaleContextHolder.resetLocaleContext();
    }

    // -----------------------------------------------------------------------------------------------
    // changeLanguage method
    // -----------------------------------------------------------------------------------------------
    @Test
    void changeLanguage_languageExist() throws MyException {
        // GIVEN
        // Local in french
        LocaleContextHolder.setLocale(new Locale(ApplicationLanguage.fr.toString()));
        // WHEN
        languageBusiness.changeLanguage(Optional.of(ApplicationLanguage.en.toString()));
        // THEN
        assertThat(LocaleContextHolder.getLocale()).isEqualTo(new Locale(ApplicationLanguage.en.toString()));
    }

    @Test
    void changeLanguage_languageEmpty() {
        // GIVEN
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> languageBusiness.changeLanguage(Optional.empty()));
        // THEN
        testMessageSource.compare(exception
                                , "exception.changeLanguage.unable", null);
    }

    @Test
    void changeLanguage_languageNull() {
        // GIVEN
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> languageBusiness.changeLanguage(null));
        // THEN
        testMessageSource.compare(exception
                            , "exception.changeLanguage.unable", null);
    }

    @Test
    void changeLanguage_languageBlank() {
        // GIVEN
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> languageBusiness.changeLanguage(Optional.of("")));
        // THEN
        testMessageSource.compare(exception
                , "exception.changeLanguage.unable", null);
    }

    @Test
    void changeLanguage_unknownLanguage() {
        // GIVEN
        // WHEN
        Throwable exception = assertThrows(MyException.class, () -> languageBusiness.changeLanguage(Optional.of("unknown")));
        // THEN
        testMessageSource.compare(exception
                , "exception.changeLanguage.unable", null);
    }
}
