package com.volare_automation.springwebshop.controller;

import com.volare_automation.springwebshop.model.CartItems;
import com.volare_automation.springwebshop.model.User;
import com.volare_automation.springwebshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;


//    @RequestMapping(value = "/", method = RequestMethod.OPTIONS)
//    public void options (){
//
//    }



    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable int id){
        return userService.getUser(id);
    }

    @RequestMapping(value = "users/save", method = RequestMethod.POST)
    public void saveUser(@RequestBody User u){
        userService.saveUser(u);
    }

    @RequestMapping(value = "users/update", method = RequestMethod.PUT)
    public void updateUser(@RequestBody User u){
        userService.updateUser(u);
    }

    @RequestMapping(value = "/users/delete/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable int id){
        userService.deleteUser(id);
    }

    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public List <CartItems> deleteUser(){
        return userService.getList();
    }
}
