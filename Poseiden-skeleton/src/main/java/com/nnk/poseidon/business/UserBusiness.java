package com.nnk.poseidon.business;

import com.nnk.poseidon.exception.MyException;
import com.nnk.poseidon.mapper.RegisterMapper;
import com.nnk.poseidon.model.Register;
import com.nnk.poseidon.model.User;
import com.nnk.poseidon.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * UserBusiness is the User page processing service
 *
 * @author MC
 * @version 1.0
 */
@Slf4j
@Service
public class UserBusiness {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RegisterMapper registerMapper;
    @Autowired
    private MessageSource messageSource;

    /**
     * Find list of Registers
     *
     * @return List of Registers
     */
    public List<Register> getUsersList() {
        List<User> users = userRepository.findAll();
        return registerMapper.listFrom(users);
    }

    /**
     * Create new User
     *
     * @param register The User object added
     *
     * @return User added
     * @throws MyException Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public User createUser(final Register register)
                            throws MyException {
        // User parameter is null
        if (register == null) {
            String msgSource = messageSource.getMessage("exception.user.nullUser"
                                , null, LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
        // User exist
        Integer id = register.getId();
        if (id != null) {
            Optional<User> optUserEntity = userRepository.findById(id);
            if (optUserEntity.isPresent()) {
                String msgSource = messageSource.getMessage("exception.user.userExists"
                                    , new Object[] { id }
                                    , LocaleContextHolder.getLocale());
                log.debug("Exception, " + msgSource);
                throw new MyException(msgSource);
            }
        }
        // User saved
        User newUserEntity = registerMapper.addUserFrom(register);
        newUserEntity.createAuthorization();
        return userRepository.save(newUserEntity);
    }

    /**
     * Find User
     *
     * @param id User ID founded
     *
     * @return Register founded
     * @throws MyException Exception
     */
    public Register getRegisterById(final Integer id)
                                        throws MyException {
        // User does not exist
        Optional<User> optUserEntity = userRepository.findById(id);
        if (optUserEntity.isEmpty()) {
            String msgSource = messageSource.getMessage("exception.user.unknown"
                                , new Object[] { id }
                                , LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
        // User found
        return registerMapper.from(optUserEntity.get());
    }

    /**
     * Updated User
     *
     * @param id User ID updated
     * @param register The User object updated
     *
     * @return User updated
     * @throws MyException Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public User updateUser(final Integer id
                            , final Register register)
                            throws MyException {
        // User does not exist
        Optional<User> optUserEntity = userRepository.findById(id);
        if (optUserEntity.isEmpty()) {
            String msgSource = messageSource.getMessage("exception.user.unknown"
                                , new Object[] { id }
                                , LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
        // User parameter is null
        if (register == null) {
            String msgSource = messageSource.getMessage("exception.user.nullUser"
                                , null, LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
        // User updated
        User userEntity = registerMapper.updateUserFrom(register, optUserEntity.get());
        return userRepository.save(userEntity);
    }

    /**
     * Deleted User
     *
     * @param id User ID deleted
     *
     * @return void
     * @throws MyException Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(final Integer id)
                            throws MyException {
        // User does not exist
        if (userRepository.existsById(id) == false) {
            String msgSource = messageSource.getMessage("exception.user.unknown"
                                , new Object[] { id }
                                , LocaleContextHolder.getLocale());
            log.debug("Exception, " + msgSource);
            throw new MyException(msgSource);
        }
        // User deleted
        userRepository.deleteById(id);
    }
}
