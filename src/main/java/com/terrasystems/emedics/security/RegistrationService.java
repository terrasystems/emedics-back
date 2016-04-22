package com.terrasystems.emedics.security;


import com.terrasystems.emedics.model.dto.UserDto;

public interface RegistrationService {
    String registerOrganisation(String mock);
    String registerUser(UserDto user, String type);
}
