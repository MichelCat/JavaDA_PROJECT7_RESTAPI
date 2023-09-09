package com.nnk.poseidon.business;

import com.nnk.poseidon.exception.MyException;
import com.nnk.poseidon.mapper.RegisterMapper;
import com.nnk.poseidon.model.Register;
import com.nnk.poseidon.enumerator.UserRole;
import com.nnk.poseidon.repository.UserRepository;
import com.nnk.poseidon.model.User;
import com.nnk.poseidon.model.AppUserPrincipal;
import com.nnk.poseidon.utils.EmailBusiness;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
    private RegisterMapper registerMapper;
    @Autowired
    private MessageSource messageSource;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        log.trace("Entering method loadUserByUsername");
        log.debug("Authentication user email: " + username);

        Optional<User> optUser = userRepository.findByUsername(username);
        if (optUser.isEmpty()) {
            String msgSource = messageSource.getMessage("exception.UserNotFound"
                                , new Object[] { username }
                                , LocaleContextHolder.getLocale());
            log.debug(msgSource);
            throw new UsernameNotFoundException(msgSource);
        }
        User user = optUser.get();

        // Activated user
        if (!user.isEnabled()) {
            String msgSource = messageSource.getMessage("exception.AccountNotActivated"
                                , new Object[] { username }
                                , LocaleContextHolder.getLocale());
            log.debug(msgSource);
            throw new UsernameNotFoundException(msgSource);
        }
        // User account expired
        if (!user.isAccountNonExpired()) {
            String msgSource = messageSource.getMessage("exception.AccountExpired"
                                , new Object[] { username }
                                , LocaleContextHolder.getLocale());
            log.debug(msgSource);
            throw new UsernameNotFoundException(msgSource);
        }
        // User locked
        if (!user.isAccountNonLocked()) {
            String msgSource = messageSource.getMessage("exception.AccountLocked"
                                , new Object[] { username }
                                , LocaleContextHolder.getLocale());
            log.debug(msgSource);
            throw new UsernameNotFoundException(msgSource);
        }
        // User credentials (password) expired
        if (!user.isCredentialsNonExpired()) {
            String msgSource = messageSource.getMessage("exception.PasswordExpired"
                                , new Object[] { username }
                                , LocaleContextHolder.getLocale());
            log.debug(msgSource);
            throw new UsernameNotFoundException(msgSource);
        }

        return new AppUserPrincipal(user);
    }


    /**
     * Add new user
     *
     * @param register New user to add
     *
     * @throws MyException Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public void addUser(Register register) throws MyException {
        // User does not exist
        if (userRepository.existsByUsername(register.getUsername())) {
            String msgSource = messageSource.getMessage("exception.EmailAccountAlreadyExists"
                                , new Object[] { register.getUsername() }
                                , LocaleContextHolder.getLocale());
            log.debug(msgSource);
            throw new MyException(msgSource);
        }

        //Add user
        User newUserEntity = registerMapper.addUserFrom(register);
        newUserEntity.setRole(UserRole.USER);
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
            String msgSource = messageSource.getMessage("exception.ErrorSendEmail"
                                , new Object[] { register.getUsername() }
                                , LocaleContextHolder.getLocale());
            log.debug(msgSource);
            throw new MyException(msgSource);
        }
    }
}
