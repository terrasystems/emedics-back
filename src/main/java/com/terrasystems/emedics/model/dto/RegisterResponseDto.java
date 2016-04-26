package com.terrasystems.emedics.model.dto;


public class RegisterResponseDto extends AbstractResponse {
    private static final long serialVersionUID = 207840154637735986L;
    private UserDto user;
    private String token;

    public RegisterResponseDto(UserDto user, String token) {
        this.user = user;
        this.token = token;
    }
    public RegisterResponseDto() {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }


}
