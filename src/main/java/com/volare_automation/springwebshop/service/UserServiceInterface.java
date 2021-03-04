package com.volare_automation.springwebshop.service;

//import com.volare_automation.springwebshop.model.CartProductTest;
import com.volare_automation.springwebshop.model.CartProduct;
import com.volare_automation.springwebshop.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserServiceInterface {

    List<User> getAllUsers();
    public User getUser(int id);
    public void saveUser(User user);
    public void updateUser(User user, Integer id);
    public void deleteUser(int id);
    public String getSessionId(User user);
    public List<CartProduct> getList();
    void printUser(User u);
    boolean logoutUser(HttpServletRequest request);
    boolean testUserLogged(HttpServletRequest request);
    String getUserId(User user);
   // Integer parseUserId(HttpServletRequest request);
    String generateSessionId();
    boolean userExists(User user);
    String getUserName(HttpServletRequest request);
    Integer getUserIdFromCookie(HttpServletRequest request);
    String getSessionIdFromCookie(HttpServletRequest request);
    String authUser(User user);
    boolean registerUser(HttpServletRequest request, User user);
   // public void postCartProduct(CartProductTest c);
}
