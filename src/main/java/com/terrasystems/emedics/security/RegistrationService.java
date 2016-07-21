package com.terrasystems.emedics.security;


import com.terrasystems.emedics.model.dtoV2.*;

public interface RegistrationService {

    ResponseDto registerUser(UserDto userDto);
    ResponseDto activateUser(String key);
    ResponseDto resetPassword(ResetPasswordDto resetPasswordDto);
    ResponseDto changePassword(ChangePasswordDto changePasswordDto);
    ResponseDto checkEmail(String email);
    ResponseDto checkKey(String key);
}
