package com.volare_automation.springwebshop.model;

public class UserAuthDataModel {

    private String username;
    private String loginStatus;
    private boolean isLogged;

    public UserAuthDataModel(String username, String loginStatus, boolean isLogged) {
        this.username = username;
        this.loginStatus = loginStatus;
        this.isLogged = isLogged;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }
}
