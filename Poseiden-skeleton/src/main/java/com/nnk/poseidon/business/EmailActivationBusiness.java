package com.nnk.poseidon.business;

import com.nnk.poseidon.exception.MyException;
import com.nnk.poseidon.model.User;
import com.nnk.poseidon.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * EmailActivationBusiness is the email activation page processing service
 *
 * @author MC
 * @version 1.0
 */
@Slf4j
@Service
public class EmailActivationBusiness {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageSource messageSource;


    /**
     * Account activation
     *
     * @param validationKey Validation key for user
     * @return New modified user record
     */
    @Transactional(rollbackFor = Exception.class)
    public User activatedUser(String validationKey) throws MyException {

        // User does not exist
        Optional<User> optUserEntity = userRepository.findByEmailValidationKey(validationKey);
        if (optUserEntity.isEmpty()) {
            String msgSource = messageSource.getMessage("exception.CustomerNotExist"
                                , null, LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
        User userEntity = optUserEntity.get();

        // Invalid email key for user
        if (!userEntity.isValidEmailKey(validationKey)) {
            String msgSource = messageSource.getMessage("exception.InvalidEmailKey"
                                , null, LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
        // Email validation for user time exceeded
        if (!userEntity.isValidEmailEndDate()) {
            String msgSource = messageSource.getMessage("exception.EmailTimeExceeded"
                                , null, LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
        // Account already activated
        if (userEntity.isEnabled()) {
            String msgSource = messageSource.getMessage("exception.AccountAlreadyActivated"
                                , null, LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }

        userEntity.setEnabled(true);
        return userRepository.save(userEntity);
    }
}
