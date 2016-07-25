package com.terrasystems.emedics.services;


import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Value("${activate.url}")
    private String URL_HOST ;
    @Value("${resetPassword.url}")
    private String URL_RESET;
    @Value("${login.url}")
    private String URL_LOGIN;
    @Value("${email.from}")
    private String EMAIL_FROM;

    @Autowired
    JavaMailSender mailSender;

    @Override
    public ResponseDto sendRegistrationMail(String address, String activateToken, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(address);
        message.setSubject("Welcome to eMedics");

        message.setText(URL_HOST+activateToken+"  "+"Your password is" + " " + password );
        message.setFrom(EMAIL_FROM);
        System.out.println("message "+ message.toString());
        try {
            System.out.println("try to send mail");
            mailSender.send(message);
        } catch (MailException ex) {
            System.out.println("Email Exception - "+ ex.getMessage());
            return new ResponseDto(false, MessageEnums.MSG_ERROR_SEND_MAIL.toString());
        }
        System.out.println("After catch");
        return new ResponseDto(true, MessageEnums.MSG_SEND_MAIL.toString());
    }

    @Override
    public ResponseDto sendResetPasswordMail(String address, String valueKey) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(address);
        message.setSubject("Reset password for emedics");
        message.setText("Please visit to link"+" " + URL_RESET+valueKey);
        message.setFrom("admin@emedics.org");
        try {
            mailSender.send(message);
        } catch (MailException ex) {
            return new ResponseDto(false, MessageEnums.MSG_ERROR_SEND_MAIL.toString());
        }
        return new ResponseDto(true,MessageEnums.MSG_SEND_MAIL.toString());
    }

    @Override
    public ResponseDto sendStuffMail(String address, String password) {
        return null;
    }

    @Override
    public ResponseDto sendMailToStuffIfAdminChangedPassword(String address, String newPassword) {
        return null;
    }
}
