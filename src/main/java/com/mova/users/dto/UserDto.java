package com.mova.users.dto;

import com.mova.users.model.UserPreferences;

public class UserDto {

    private String email;
    private UserPreferences preferences;

    public UserDto(String email, UserPreferences preferences) {
        this.email = email;
        this.preferences = preferences;
    }

    public UserDto() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserPreferences getPreferences() {
        return preferences;
    }

    public void setPreferences(UserPreferences preferences) {
        this.preferences = preferences;
    }
}
