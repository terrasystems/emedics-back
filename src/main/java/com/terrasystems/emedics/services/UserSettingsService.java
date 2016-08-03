package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.dtoV2.ChangePasswordDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.dtoV2.UserDto;

public interface UserSettingsService {
    ResponseDto getUserById(String id);
    ResponseDto changePassword(ChangePasswordDto dto);
    ResponseDto editUser(UserDto dto);
    ResponseDto removeUser(String id);
}
