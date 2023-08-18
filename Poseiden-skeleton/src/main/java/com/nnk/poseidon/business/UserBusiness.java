package com.nnk.poseidon.business;

import com.nnk.poseidon.exception.MyExceptionBadRequestException;
import com.nnk.poseidon.exception.MyExceptionNotFoundException;
import com.nnk.poseidon.mapper.UserRegisterMapper;
import com.nnk.poseidon.model.Register;
import com.nnk.poseidon.model.User;
import com.nnk.poseidon.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserRegisterMapper userRegisterMapper;

    /**
     * Find list of Registers
     *
     * @return List of Registers
     */
    public List<Register> getUsersList() {
        List<User> users = userRepository.findAll();
        return userRegisterMapper.listFrom(users);
    }

    /**
     * Create new User
     *
     * @param register The User object added
     *
     * @return User added
     * @throws MyExceptionBadRequestException Exception bad request
     */
    @Transactional(rollbackFor = Exception.class)
    public User createUser(final Register register)
                            throws MyExceptionBadRequestException {
        // User parameter is null
        if (register == null) {
            log.debug("THROW, User is null.");
            throw new MyExceptionBadRequestException("throw.user.nullUser");
        }
        // User exist
        Integer id = register.getId();
        if (id != null) {
            Optional<User> optUserEntity = userRepository.findById(id);
            if (optUserEntity.isPresent()) {
                log.debug("THROW, User exist ({}).", optUserEntity.get());
                throw new MyExceptionBadRequestException("throw.user.userExists");
            }
        }
        // User saved
        User newUserEntity = userRegisterMapper.addUserFrom(register);
//        newUserEntity.setCreationDate(MyDateUtils.getcurrentTime())
        newUserEntity.createAuthorization();
//        newUserEntity.createValidationEmail();
        return userRepository.save(newUserEntity);
    }

    /**
     * Find User
     *
     * @param id User ID founded
     *
     * @return User founded
     * @throws MyExceptionNotFoundException Exception not found
     */
    public Register getRegisterById(final Integer id)
                                        throws MyExceptionNotFoundException {
        // User does not exist
        Optional<User> optUserEntity = userRepository.findById(id);
        if (optUserEntity.isPresent() == false) {
            log.debug("THROW, User not exist ({}).", id);
            throw new MyExceptionNotFoundException("throw.user.unknown", id);
        }
        // User found
        return userRegisterMapper.from(optUserEntity.get());
    }

    /**
     * Updated User
     *
     * @param id User ID updated
     * @param register The User object updated
     *
     * @return User updated
     * @throws MyExceptionNotFoundException Exception not found
     * @throws MyExceptionBadRequestException Exception bad request
     */
    @Transactional(rollbackFor = Exception.class)
    public User updateUser(final Integer id
                            , final Register register)
                            throws MyExceptionNotFoundException {
        // User does not exist
        Optional<User> optUserEntity = userRepository.findById(id);
        if (optUserEntity.isPresent() == false) {
            log.debug("THROW, User not exist ({}).", id);
            throw new MyExceptionNotFoundException("throw.user.unknown", id);
        }
        // User parameter is null
        if (register == null) {
            log.debug("THROW, User is null.");
            throw new MyExceptionBadRequestException("throw.user.nullUser");
        }
        // User updated
        User userEntity = userRegisterMapper.updateUserFrom(register, optUserEntity.get());
//        userEntity.setRevisionDate(MyDateUtils.getcurrentTime());
        return userRepository.save(userEntity);
    }

    /**
     * Deleted User
     *
     * @param id User ID deleted
     *
     * @return void
     * @throws MyExceptionNotFoundException Exception not found
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(final Integer id)
                            throws MyExceptionNotFoundException {
        // User does not exist
        if (userRepository.existsById(id) == false) {
            log.debug("THROW, User not exist ({}).", id);
            throw new MyExceptionNotFoundException("throw.user.unknown", id);
        }
        // User deleted
        userRepository.deleteById(id);
    }
}
