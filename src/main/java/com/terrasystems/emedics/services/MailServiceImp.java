package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.dto.StateDto;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ResourceBundle;

@Service
public class MailServiceImp implements MailService {
    private static final String HOST = "localhost:8080/";
    private static final String ACTIVATE_URL = HOST + "rest/public/activate/";

    @Autowired
    JavaMailSender mailSender;

    /*@PostConstruct
    public void init() {
        ResourceBundle props = ResourceBundle.getBundle("resources/email");
        mailSender.setHost(props.getString("email.host"));
        mailSender.setPort(Integer.parseInt(props.getString("email.port")));
        mailSender.setPassword(props.getString("email.pass"));
        //Username login aware
        mailSender.setUsername(props.getString("email.login"));
    }*/


    @Override
    public StateDto sendRegistrationMail(String address, String activateToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(address);
        message.setSubject("test");
        message.setText(ACTIVATE_URL+activateToken);
        message.setFrom("minilis@minilis.org");
        try {
            mailSender.send(message);
        } catch (MailException ex) {
            return new StateDto(false, "Error while send message");
        }
        return new StateDto(true,"Mail Send");
    }

    @Override
    public StateDto sendResetPasswordMail(String address, String newPass) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(address);
        message.setSubject("test");
        message.setText(newPass);
        message.setFrom("minilis@minilis.org");
        try {
            mailSender.send(message);
        } catch (MailException ex) {
            return new StateDto(false, "Error while send message");
        }
        return new StateDto(true,"Mail Send");

    }


}
