package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.dto.StateDto;
import org.apache.log4j.ConsoleAppender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class MailServiceImp implements MailService {
    private  @Value("${activate.url}") String HOST ;
    private  String ACTIVATE_URL = HOST + "rest/public/activate/";

    @Autowired
    JavaMailSender mailSender;
    @Autowired
    HttpServletRequest request;

    @Override
    public StateDto sendRegistrationMail(String address, String activateToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(address);
        message.setSubject("test");
        message.setText(HOST+activateToken);
        message.setFrom("minilis@minilis.org");
        System.out.println("message "+ message.toString());
        try {
            System.out.println("try to send mail");
            mailSender.send(message);
        } catch (MailException ex) {
            System.out.println("Email Exception - "+ ex.getMessage());
            return new StateDto(false, "Error while send message");
        }
        System.out.println("After catch");
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
