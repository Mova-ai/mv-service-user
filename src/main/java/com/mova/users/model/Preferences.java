package com.mova.users.model;

public class Preferences {
    private String language = "es";
    private Boolean darkMode = false;
    private String currency = "EUR";

    public Preferences() {
    }

    public Preferences(String language, Boolean darkMode, String currency) {
        this.language = language;
        this.darkMode = darkMode;
        this.currency = currency;
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
