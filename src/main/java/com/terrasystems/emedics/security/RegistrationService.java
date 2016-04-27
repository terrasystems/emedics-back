package com.terrasystems.emedics.security;


import com.terrasystems.emedics.model.dto.RegisterDto;
import com.terrasystems.emedics.model.dto.RegisterResponseDto;
import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.dto.UserDto;

public interface RegistrationService {
    StateDto registerOrganisation(String mock);
    StateDto registerUser(UserDto user, String type);
    StateDto resetPassword(String email);
    RegisterResponseDto activateUser(String link);
    default UserDto getUserDto(RegisterDto registerDto) {
        return registerDto.getUser();
    }

}
