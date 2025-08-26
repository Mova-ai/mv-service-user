package com.mova.users.dto;

import com.mova.users.model.UserProfile;

import java.util.Date;

public class UserProfileDTO {

    String id;
    String email;
    String firstName;
    String lastName;
    String phone;
    String avatarUrl;
    Date birthday;
    String bio;

    public UserProfileDTO() {
    }

    public UserProfileDTO(UserProfile entity) {
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.phone = entity.getPhone();
        this.avatarUrl = entity.getAvatarUrl();
        this.birthday = entity.getBirthday();
        this.bio = entity.getBio();
    }


    public UserProfileDTO(String id, String email, String firstName, String lastName, String phone, String avatarUrl, Date birthday, String bio) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.avatarUrl = avatarUrl;
        this.birthday = birthday;
        this.bio = bio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
