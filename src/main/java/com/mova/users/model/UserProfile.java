package com.mova.users.model;


import jakarta.persistence.*;
import java.util.Date;

@Entity
public class UserProfile {

    @Id
    private String uid;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "userUid", referencedColumnName = "uid", nullable = false)
//    private User user;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String firstName;

    @Column(unique = true)
    private String lastName;

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String avatarUrl;

    @Column(unique = true)
    private Date birthday;

    @Column(unique = true)
    private String bio;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
//    @JsonBackReference("user-profile")
    private User user;


    public UserProfile(String bio, Date birthday, String avatar_url, String phone, String lastName, String firstName, String email, String id, User user) {
        this.bio = bio;
        this.birthday = birthday;
        this.avatarUrl = avatar_url;
        this.phone = phone;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.uid = uid;
        this.user = user;
    }

    public UserProfile() {}

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User getUidUser() {
        return user;
    }

    public void setUidUser(User uidUser) {
        this.user = uidUser;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "uid=" + uid +
                ", user=" + user +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", birthday=" + birthday +
                ", bio='" + bio + '\'' +
                '}';
    }

}
