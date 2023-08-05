package com.nnk.springboot.business;

import com.nnk.springboot.Exception.MyException;
import com.nnk.springboot.Exception.MyExceptionNotFoundException;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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


    /**
     * Account activation
     *
     * @param validationKey Validation key for user
     * @return New modified user record
     */
    @Transactional(rollbackFor = Exception.class)
    public User activatedUser(String validationKey) throws MyException {

        // User does not exist
//        User userEntity = userRepository.findByEmailValidationKey(validationKey)
//                .orElseThrow(() -> new MyException("throw.CustomerNotExist", ""));
        Optional<User> optUserEntity = userRepository.findByEmailValidationKey(validationKey);
        if (optUserEntity.isPresent() == false) {
            log.debug("THROW, Bid not exist.");
            throw new MyException("throw.CustomerNotExist", "");
        }
        User userEntity = optUserEntity.get();

        // Invalid email key for user
        if (!userEntity.isValidEmailKey(validationKey)) {
            log.debug("THROW, Invalid email.");
            throw new MyException("throw.InvalidEmailKey");
        }
        // Email validation for user time exceeded
        if (!userEntity.isValidEmailEndDate()) {
            log.debug("THROW, Email validation for user time exceeded.");
            throw new MyException("throw.EmailTimeExceeded");
        }
        // Account already activated
        if (userEntity.isEnabled()) {
            log.debug("THROW, Account already activated.");
            throw new MyException("throw.AccountAlreadyActivated");
        }

        userEntity.setEnabled(true);
        return userRepository.save(userEntity);
    }
}
