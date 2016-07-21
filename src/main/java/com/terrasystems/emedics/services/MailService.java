package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.dtoV2.ResponseDto;

public interface MailService {
    ResponseDto sendRegistrationMail(String address, String activateToken, String password);
    ResponseDto sendResetPasswordMail(String address, String newPass);
    ResponseDto sendStuffMail(String address, String password);
    ResponseDto sendMailToStuffIfAdminChangedPassword(String address, String newPassword);
}
