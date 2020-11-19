package com.volare_automation.springwebshop.service;

import com.volare_automation.springwebshop.model.User;

import java.util.List;

public interface UserServiceInterface {

    List<User> getAllUsers();
    public User getUser(int id);
    public void saveUser(User u);
    public void updateUser(User u);
    public void deleteUser(int id);
}
