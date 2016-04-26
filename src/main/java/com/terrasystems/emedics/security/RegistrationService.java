package com.terrasystems.emedics.security;


import com.terrasystems.emedics.model.dto.StateDto;
import com.terrasystems.emedics.model.dto.UserDto;

public interface RegistrationService {
    StateDto registerOrganisation(String mock);
    StateDto registerUser(UserDto user, String type);
    String resetPassword(String email);
}
