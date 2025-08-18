package com.mova.users.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class UserPreferences {

    @Id
    private String id;

    private String language = "es";
    private Boolean darkMode = false;
    private String currency = "EUR";

    // referencia al User
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @JsonBackReference
    private User user;

    public UserPreferences() {
    }

    public UserPreferences(String id, String language, Boolean darkMode, String currency) {
        this.id = id;
        this.language = language;
        this.darkMode = darkMode;
        this.currency = currency;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getDarkMode() {
        return darkMode;
    }

    public void setDarkMode(Boolean darkMode) {
        this.darkMode = darkMode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}

