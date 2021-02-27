package com.volare_automation.springwebshop.repository;

//import com.volare_automation.springwebshop.model.CartProductTest;
import com.volare_automation.springwebshop.model.User;

import java.util.List;
import java.util.Map;

public interface UserRepositoryInterface {

    public List<User> getAllUsers();
    public void saveUser(User u);
    //public User getUserById(int id);
    public void updateUser(User u);
    public void deleteUserById(int id);
    public boolean authUser(User user);
    public boolean regUser(User user);
    public void userLogin(User user);
    public String queryForSessionId(User user);
    public void saveSessionId(User user, String sessionId);
    public String getUserId(User user);
    String getUserName(Integer id);
    int logoutUser(Integer userId);
    Map<String, Object> testUserLogged(Integer userId);
    List<String> listOfUsernames();
    String userEnabled(Integer id);

    //public void postCartProd(CartProductTest c);

}
