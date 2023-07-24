package com.nnk.springboot.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * EmailBusiness is send email service
 *
 * @author MC
 * @version 1.0
 */
@Service
public class EmailBusiness {

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * Send an email
     *
     * @param sender Email sender
     * @param recipient Email recipient
     * @param subject Message subject
     * @param message Message detail
     */
    public void sendEmail(String sender, String recipient, String subject, String message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(sender);
        mail.setTo(recipient);
        mail.setSubject(subject);
        mail.setText(message);
        javaMailSender.send(mail);
    }
}
