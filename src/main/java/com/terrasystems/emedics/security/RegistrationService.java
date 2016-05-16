package com.terrasystems.emedics.security;


import com.terrasystems.emedics.model.dto.*;

public interface RegistrationService {
    StateDto registerOrganisation(UserDto user, OrganisationDto org);
    StateDto registerUser(RegisterDto registerDto , String type);
    StateDto resetPassword(String email);
    RegisterResponseDto activateUser(String link);
    default UserDto getUserDto(RegisterDto registerDto) {
        return registerDto.getUser();
    }

}
