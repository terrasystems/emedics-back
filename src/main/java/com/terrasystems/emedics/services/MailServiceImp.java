package com.terrasystems.emedics.services;


import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.StateDto;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailServiceImp implements MailService {
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
    @Autowired
    HttpServletRequest request;
    @Autowired
    VelocityEngine velocityEngine;

    @Override
    public StateDto sendRegistrationMail(String address, String activateToken, String password) {
        //MimeMessage message = mailSender.createMimeMessage();
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
            return new StateDto(false, MessageEnums.MSG_ERROR_SEND_MAIL.toString());
        }
        System.out.println("After catch");
        return new StateDto(true,MessageEnums.MSG_SEND_MAIL.toString());
    }

    @Override
    public StateDto sendResetPasswordMail(String address, String valueKey) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(address);
        message.setSubject("Reset password for emedics");
        message.setText("Please visit to link"+" " + URL_RESET+valueKey);
        message.setFrom("admin@emedics.org");
        try {
            mailSender.send(message);
        } catch (MailException ex) {
            return new StateDto(false, MessageEnums.MSG_ERROR_SEND_MAIL.toString());
        }
        return new StateDto(true,MessageEnums.MSG_SEND_MAIL.toString());
    }



    @Override
    public StateDto sendMailToStuffIfAdminChangedPassword(String address, String newPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(address);
        message.setSubject("Your new password for emedics");
        message.setText("Admin of your organization changed your password and now your password is: " + newPassword);
        message.setFrom("admin@emedics.org");
        mailSender.send(message);
        return new StateDto(true, "Message sent");
    }

    /*@Override
    public StateDto sendMailToStuffIfAdminChangedEmail(String address, String newEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(address);
        message.setSubject("Your new email for emedics");
        message.setText("Admin of your organization changed your email and now your email is: " + newEmail);
        message.setFrom("admin@emedics.org");
        mailSender.send(message);
        return new StateDto(true, "Message sent");
    }*/

    @Override
    public StateDto sendStuffMail(String address, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(address);
        message.setSubject("Welcome to eMedics");

        message.setText(URL_LOGIN +  " Your password is" + " " + password );
        message.setFrom(EMAIL_FROM);
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
    public void velocityTest(User user ) {
        /*MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                Map model = new HashMap<>();

                message.setTo("serhiimorunov@gmail.com");
                message.setSubject("test");
                message.setText("test text");
                message.setFrom("admin@emedics.org");

            }
        };*/
        mailSender.send(mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            Map model = new HashMap<>();
            model.put("user", user);
            String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "classpath*:velocity/test.vm", model);


            message.setTo("serhiimorunov@gmail.com");
            message.setSubject("test");
            message.setText(text);
            message.setFrom(EMAIL_FROM);
        });
    }


}
