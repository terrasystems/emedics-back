package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.User;
import com.terrasystems.emedics.model.dto.StateDto;

public interface MailService {
    StateDto sendRegistrationMail(String address, String activateToken, String password);
    StateDto sendResetPasswordMail(String address, String newPass);
    StateDto sendStuffMail(String address, String password);
    void velocityTest(User user);
    StateDto sendMailToStuffIfAdminChangedPassword(String address, String newPassword);
    /*StateDto sendMailToStuffIfAdminChangedEmail(String address, String newEmail);*/
}
