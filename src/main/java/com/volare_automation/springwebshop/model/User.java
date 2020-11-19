package com.volare_automation.springwebshop.model;

public class User {

    private int id;
    private String firstname;
    private String surname;




    public User() {
    }

    public User(String firstName, String lastName, int id) {
        this.firstname = firstName;
        this.surname = lastName;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
