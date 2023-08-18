package com.nnk.poseidon.business;

import com.nnk.poseidon.exception.MessagePropertieFormat;
import com.nnk.poseidon.exception.MyException;
import com.nnk.poseidon.exception.MyExceptionBadRequestException;
import com.nnk.poseidon.mapper.UserRegisterMapper;
import com.nnk.poseidon.model.Register;
import com.nnk.poseidon.model.Role;
import com.nnk.poseidon.repository.UserRepository;
import com.nnk.poseidon.model.User;
import com.nnk.poseidon.model.AppUserPrincipal;
import com.nnk.poseidon.utils.EmailBusiness;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * LoginBusiness is the login page processing service
 *
 * @author MC
 * @version 1.0
 */
@Slf4j
@Service
public class LoginBusiness implements UserDetailsService
{

    @Autowired
    private EmailBusiness emailBusiness;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRegisterMapper userRegisterMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        log.trace("Entering method loadUserByUsername");
        log.debug("Authentication user email: " + username);

        Optional<User> optUser = userRepository.findByUsername(username);
        if (optUser.isEmpty()) {
            log.debug(MessagePropertieFormat.getMessage("throw.UserNotFound", username));
            throw new UsernameNotFoundException(MessagePropertieFormat.getMessage("throw.UserNotFound", username));
        }
        User user = optUser.get();

        // Activated user
        if (!user.isEnabled()) {
            log.debug(MessagePropertieFormat.getMessage("throw.AccountNotActivated", username));
            throw new UsernameNotFoundException(MessagePropertieFormat.getMessage("throw.AccountNotActivated", username));
        }
        // User account expired
        if (!user.isAccountNonExpired()) {
            log.debug(MessagePropertieFormat.getMessage("throw.AccountExpired", username));
            throw new UsernameNotFoundException(MessagePropertieFormat.getMessage("throw.AccountExpired", username));
        }
        // User locked
        if (!user.isAccountNonLocked()) {
            log.debug(MessagePropertieFormat.getMessage("throw.AccountLocked", username));
            throw new UsernameNotFoundException(MessagePropertieFormat.getMessage("throw.AccountLocked", username));
        }
        // User credentials (password) expired
        if (!user.isCredentialsNonExpired()) {
            log.debug(MessagePropertieFormat.getMessage("throw.PasswordExpired", username));
            throw new UsernameNotFoundException(MessagePropertieFormat.getMessage("throw.PasswordExpired", username));
        }

        return new AppUserPrincipal(user);
    }


    /**
     * Add new user
     *
     * @param register New user to add
     *
     * @return void
     * @throws MyException Exception message
     */
    @Transactional(rollbackFor = Exception.class)
    public void addUser(Register register) throws MyExceptionBadRequestException {
        // User does not exist
        if (userRepository.existsByUsername(register.getUsername())) {
            log.debug(MessagePropertieFormat.getMessage("throw.EmailAccountAlreadyExists", register.getUsername()));
            throw new MyExceptionBadRequestException("throw.EmailAccountAlreadyExists", register.getUsername());
        }

        //Add user
        User newUserEntity = userRegisterMapper.addUserFrom(register);
        newUserEntity.setRole(Role.USER);
//        newUserEntity.setCreationDate(MyDateUtils.getcurrentTime())
        newUserEntity.createAuthorization();
        newUserEntity.createValidationEmail();
        newUserEntity = userRepository.save(newUserEntity);

        // Send activation email contact
        String subject = "Activation Email";
        String message = "You have 24 hours to activate your account using the link : "
                + "http://localhost:8080/app/register/" + newUserEntity.getEmailValidationKey();
        try {
            emailBusiness.sendEmail("contact@gmail.com", register.getUsername(), subject, message);
        } catch (MailException e) {
            log.debug(MessagePropertieFormat.getMessage("throw.ErrorSendEmail", register.getUsername()));
            throw new MyExceptionBadRequestException("throw.ErrorSendEmail", register.getUsername());
        }
    }
}
