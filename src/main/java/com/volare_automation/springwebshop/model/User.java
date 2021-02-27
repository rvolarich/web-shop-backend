package com.volare_automation.springwebshop.model;

public class User {

    private int userid;
    private String username;
    private String password;
    private String isEnabled;




    public User() {
    }

    public User(String username, String password, String isEnabled) {

        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
    }

    public User(int userid, String username, String password, String isEnabled) {
        this.userid = userid;
        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
    }


    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    //    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }

//    @Override
//    public String toString(){
//        return "User [First Name " + firstname + "Last Name" + surname + "]";
//    }
}
