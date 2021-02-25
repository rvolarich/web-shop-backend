package com.volare_automation.springwebshop.service;

//import com.volare_automation.springwebshop.model.CartProductTest;
import com.volare_automation.springwebshop.model.CartProduct;
import com.volare_automation.springwebshop.model.User;

import java.util.List;

public interface UserServiceInterface {

    List<User> getAllUsers();
    public User getUser(int id);
    public void saveUser(User u);
    public void updateUser(User u);
    public void deleteUser(int id);
    public String getSessionId(User user);
    public List<CartProduct> getList();
    void printUser(User u);
   // public void postCartProduct(CartProductTest c);
}
