package com.terrasystems.emedics.services;


import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.model.dto.StateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class MailServiceImp implements MailService {
    private  @Value("${activate.url}") String HOST ;
    //private  String ACTIVATE_URL = HOST + "rest/public/activate/";

    @Autowired
    JavaMailSender mailSender;
    @Autowired
    HttpServletRequest request;

    @Override
    public StateDto sendRegistrationMail(String address, String activateToken, String password) {
        //MimeMessage message = mailSender.createMimeMessage();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(address);
        message.setSubject("Welcome to eMedics");

        message.setText(HOST+activateToken+"  "+"Your password is" + " " + password );
        message.setFrom("admin@emedics.org");
        System.out.println("message "+ message.toString());
        try {
            System.out.println("try to send mail");
            mailSender.send(message);
        } catch (MailException ex) {
            System.out.println("Email Exception - "+ ex.getMessage());
            return new StateDto(false, MessageEnums.MSG_ERROR_SEND_MAIL.toString());
        }
        System.out.println("After catch");
        return new StateDto(true,MessageEnums.MSG_SEND_MAIL.toString());
    }

    @Override
    public StateDto sendResetPasswordMail(String address, String newPass) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(address);
        message.setSubject(MessageEnums.MSG_RESET_PASS.toString());
        message.setText("Your new password is:"+" " + newPass);
        message.setFrom("admin@emedics.org");
        try {
            mailSender.send(message);
        } catch (MailException ex) {
            return new StateDto(false, MessageEnums.MSG_ERROR_SEND_MAIL.toString());
        }
        return new StateDto(true,MessageEnums.MSG_SEND_MAIL.toString());

    }


}
