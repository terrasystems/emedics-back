package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.dto.StateDto;

public interface MailService {
    StateDto sendRegistrationMail(String address, String activateToken);
    StateDto sendResetPasswordMail(String address, String newPass);
}
