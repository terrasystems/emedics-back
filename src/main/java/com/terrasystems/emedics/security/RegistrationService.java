package com.terrasystems.emedics.security;


import com.terrasystems.emedics.model.dtoV2.*;

public interface RegistrationService {

    ResponseDto registerUser(UserDto userDto);
    UserDto loginUser(LoginDto loginDto);
    ResponseDto activateUser(String key);
    ResponseDto resetPassword(ResetPasswordDto resetPasswordDto);
    UserDto changePassword(ChangePasswordDto changePasswordDto);
    ResponseDto checkEmail(String email);
    ResponseDto checkKey(String key);
}
