package com.nnk.poseidon.business;

import com.nnk.poseidon.enumerator.ApplicationLanguage;
import com.nnk.poseidon.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

/**
 * LanguageBusiness is the language change service
 *
 * @author MC
 * @version 1.0
 */
@Slf4j
@Service
public class LanguageBusiness {

    @Autowired
    private MessageSource messageSource;

    /**
     * Change language.
     *
     * @param optLang New language
     *
     * @return void
     * @throws MyException Exception
     */
    public void changeLanguage(Optional<String> optLang)
                                throws MyException {
        try {
            ApplicationLanguage language = ApplicationLanguage.valueOf(optLang.get());
            Locale locale = new Locale(language.toString());
            LocaleContextHolder.setLocale(locale);
        } catch (Exception e) {
            String msgSource = messageSource.getMessage("exception.changeLanguage.unable"
                                    , null, LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
    }
}
