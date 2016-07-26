package com.terrasystems.emedics.security;


import com.terrasystems.emedics.model.dtoV2.*;

public interface RegistrationService {

    ResponseDto registerUser(UserDto userDto);
    ResponseDto activateUser(String key);
    ResponseDto resetPassword(UserDto userDto);
    ResponseDto changePassword(ResetPasswordDto resetPasswordDto);
    ResponseDto checkEmail(String email);
    ResponseDto checkKey(String key);
    ResponseDto login(LoginDto loginDto);
}
