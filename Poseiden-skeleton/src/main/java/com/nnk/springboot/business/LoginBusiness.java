package com.nnk.springboot.business;

import com.nnk.springboot.Exception.MessagePropertieFormat;
import com.nnk.springboot.Exception.MyException;
import com.nnk.springboot.domain.Register;
import com.nnk.springboot.domain.Role;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.domain.AppUserPrincipal;
import com.nnk.springboot.utils.EmailBusiness;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
//    implements UserDetailsService
{

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailBusiness emailBusiness;
    @Autowired
    private UserRepository userRepository;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        log.trace("Entering method loadUserByUsername");
        log.debug("Authentication user email: " + username);

        Optional<User> optUser = userRepository.findByUsername(username);
        if (optUser.isEmpty()) {
            log.error(MessagePropertieFormat.getMessage("throw.UserNotFound", username));
            throw new UsernameNotFoundException(MessagePropertieFormat.getMessage("throw.UserNotFound", username));
        }
        User user = optUser.get();

        // Activated user
        if (!user.isEnabled()) {
            log.info(MessagePropertieFormat.getMessage("throw.AccountNotActivated", username));
            throw new UsernameNotFoundException(MessagePropertieFormat.getMessage("throw.AccountNotActivated", username));
        }
        // User account expired
        if (!user.isAccountNonExpired()) {
            log.info(MessagePropertieFormat.getMessage("throw.AccountExpired", username));
            throw new UsernameNotFoundException(MessagePropertieFormat.getMessage("throw.AccountExpired", username));
        }
        // User locked
        if (!user.isAccountNonLocked()) {
            log.info(MessagePropertieFormat.getMessage("throw.AccountLocked", username));
            throw new UsernameNotFoundException(MessagePropertieFormat.getMessage("throw.AccountLocked", username));
        }
        // User credentials (password) expired
        if (!user.isCredentialsNonExpired()) {
            log.info(MessagePropertieFormat.getMessage("throw.PasswordExpired", username));
            throw new UsernameNotFoundException(MessagePropertieFormat.getMessage("throw.PasswordExpired", username));
        }

        return new AppUserPrincipal(user);
    }


    /**
     * Add new user
     *
     * @param register New user to add
     * @throws MyException Exception message
     */
    @Transactional(rollbackFor = Exception.class)
    public void addUser(Register register) throws MyException {
        Optional<User> optUser = userRepository.findByUsername(register.getEmail());
        if (optUser.isEmpty() == false) {
            throw new MyException("throw.EmailAccountAlreadyeExists", register.getEmail());
        }

        //Add user
        User newUserEntity = User.builder()
                .username(register.getEmail())
                .password(passwordEncoder.encode(register.getPassword()))
                .fullname(register.getFirstName() + " " + register.getLastName())
                .role(Role.USER.name())
                .build();
        newUserEntity.createAuthorization();
        newUserEntity.createValidationEmail();
        newUserEntity = userRepository.save(newUserEntity);

        // Send activation email contact
        String subject = "Activation Email";
        String message = "You can activate your account using the link : "
                + "http://localhost:8080/app/register/" + newUserEntity.getEmailValidationKey();
        try {
            emailBusiness.sendEmail("contact@gmail.com", register.getEmail(), subject, message);
        } catch (MailException e) {
            throw new MyException("throw.ErrorSendEmail", register.getEmail());
        }
    }
}
