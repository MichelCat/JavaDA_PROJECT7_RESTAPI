package com.nnk.poseidon.business;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TestMessageSource is the test class for compare a message with the list of messages
 *
 * @author MC
 * @version 1.0
 */
public class TestMessageSource {

    private MessageSource messageSource;

    public TestMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Compare a message with the list of messages
     *
     * @param throwable Exception to test
     * @param codeToSearch Code to search
     * @param parameters Parameter list
     */
    public void compare(Throwable throwable, String codeToSearch, @Nullable Object[] parameters) {
        String msgSource = messageSource.getMessage(
                            codeToSearch, parameters
                            , LocaleContextHolder.getLocale());
        assertThat(throwable.getMessage()).isEqualTo(msgSource);
    }
}
