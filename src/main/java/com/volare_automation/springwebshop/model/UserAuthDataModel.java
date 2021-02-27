package com.volare_automation.springwebshop.model;

public class UserAuthDataModel {

    private String username;
    private String userEnabled;
    private boolean isLogged;


    public UserAuthDataModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    public String getUserEnabled() {
        return userEnabled;
    }

    public void setUserEnabled(String userEnabled) {
        this.userEnabled = userEnabled;
    }
}
