package com.nnk.springboot.business;

import com.nnk.springboot.Exception.MyException;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * EmailActivationBusiness is the email activation page processing service
 *
 * @author MC
 * @version 1.0
 */
@Service
public class EmailActivationBusiness {

    @Autowired
    private UserRepository userRepository;


    /**
     * Account activation
     *
     * @param validationKey Validation key for customers
     * @return New modified user record
     */
    @Transactional(rollbackFor = Exception.class)
    public User emailActivationBusiness(String validationKey) throws MyException {

        // User does not exist
        User userEntity = userRepository.findByEmailValidationKey(validationKey)
                .orElseThrow(() -> new MyException("throw.CustomerNotExist"));

        // Invalid email key for customers
        if (!userEntity.isValidEmailKey(validationKey)) {
            throw new MyException("throw.InvalidEmailKey");
        }
        // Email validation for customers time exceeded
        if (!userEntity.isValidEmailEndDate()) {
            throw new MyException("throw.EmailTimeExceeded");
        }
        // Account already activated
        if (userEntity.isEnabled()) {
            throw new MyException("throw.AccountAlreadyActivated");
        }

        userEntity.setEnabled(true);
        return userRepository.save(userEntity);
    }
}
