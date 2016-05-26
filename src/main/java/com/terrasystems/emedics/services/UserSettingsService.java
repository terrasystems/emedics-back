package com.terrasystems.emedics.services;


import com.terrasystems.emedics.model.dto.ChangePasswordDto;
import com.terrasystems.emedics.model.dto.RegisterResponseDto;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.dto.UserDto;

public interface UserSettingsService {
    RegisterResponseDto editUser(UserDto userDto);
    RegisterResponseDto changePassword(ChangePasswordDto changePasswordDto);
}
