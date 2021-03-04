package com.volare_automation.springwebshop.controller;

import com.volare_automation.springwebshop.model.CartProduct;
import com.volare_automation.springwebshop.model.User;
import com.volare_automation.springwebshop.repository.UserRepositoryInterface;
import com.volare_automation.springwebshop.service.UserRepoInterface;
import com.volare_automation.springwebshop.service.UserService;
import com.volare_automation.springwebshop.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:3000", allowCredentials = "true", methods = {
        RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.OPTIONS})

public class UserController {

    @Autowired
    private UserServiceInterface userServiceInterface;

    @Autowired
    private UserRepositoryInterface userRepositoryInterface;



    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUsers(){
        return userServiceInterface.getAllUsers();
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable int id){
        return userServiceInterface.getUser(id);
    }

    @RequestMapping(value = "users/save", method = RequestMethod.POST)
    public void saveUser( @RequestBody User user){


        userServiceInterface.saveUser(user);
    }

    @RequestMapping(value = "users/update", method = RequestMethod.PUT)
    public void updateUser(HttpServletRequest request, HttpServletResponse response, @RequestBody User u){

        Integer id = userServiceInterface.getUserIdFromCookie(request);
        userServiceInterface.updateUser(u, id);
    }

    @RequestMapping(value = "/users/delete/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable int id){
        userServiceInterface.deleteUser(id);
    }

    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public List <CartProduct> deleteUser(){
        return userServiceInterface.getList();
    }
}
