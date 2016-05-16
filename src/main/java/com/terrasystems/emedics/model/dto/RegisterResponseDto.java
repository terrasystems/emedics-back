package com.terrasystems.emedics.model.dto;


public class RegisterResponseDto extends AbstractResponse {
    private static final long serialVersionUID = 207840154637735986L;
    private StateDto state;
    private UserDto user;
    private String token;
    private OrganisationDto organisation;

    public RegisterResponseDto(UserDto user, String token, StateDto state) {
        this.state = state;
        this.user = user;
        this.token = token;
    }
    public RegisterResponseDto() {}

    @Override
    public StateDto getState() {
        return state;
    }

    @Override
    public void setState(StateDto state) {
        this.state = state;
    }

    public OrganisationDto getOrganisation() {
        return organisation;
    }

    public void setOrganisation(OrganisationDto organisation) {
        this.organisation = organisation;
    }

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
