package com.nnk.poseidon.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

/**
 * EmailBusinessTest is a class of unit tests on email.
 *
 * @author MC
 * @version 1.0
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class EmailBusinessTest {

    @Autowired
    private EmailBusiness emailBusiness;

    @MockBean
    private JavaMailSender javaMailSender;


    // -----------------------------------------------------------------------------------------------
    // sendEmail method
    // -----------------------------------------------------------------------------------------------
    @Test
    void sendEmail_normal() {
        // GIVEN
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("contact@gmail.com");
        mail.setTo("user@gmail.com");
        mail.setSubject("test");
        mail.setText("Bonjour");
        doNothing().when(javaMailSender).send(mail);
        // WHEN
        emailBusiness.sendEmail("contact@gmail.com", "user@gmail.com","test", "Bonjour");
        // THEN
        verify(javaMailSender, Mockito.times(1)).send(mail);
    }
}
