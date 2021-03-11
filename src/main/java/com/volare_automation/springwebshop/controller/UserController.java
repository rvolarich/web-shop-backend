package com.volare_automation.springwebshop.controller;

import com.volare_automation.springwebshop.JavaMailConfig;
import com.volare_automation.springwebshop.model.CartProduct;
import com.volare_automation.springwebshop.model.Mail;
import com.volare_automation.springwebshop.model.Products;
import com.volare_automation.springwebshop.model.User;
import com.volare_automation.springwebshop.repository.ProductRepositoryInterface;
import com.volare_automation.springwebshop.repository.UserRepositoryInterface;
import com.volare_automation.springwebshop.service.EmailServiceInterface;
import com.volare_automation.springwebshop.service.UserRepoInterface;
import com.volare_automation.springwebshop.service.UserService;
import com.volare_automation.springwebshop.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:3000", allowCredentials = "true", methods = {
        RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.OPTIONS})

public class UserController {

    @Autowired
    private UserServiceInterface userServiceInterface;

    @Autowired
    private UserRepositoryInterface userRepositoryInterface;

    @Autowired
    private EmailServiceInterface emailServiceInterface;

    @Autowired
    private ProductRepositoryInterface productRepositoryInterface;

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

    @RequestMapping(value = "/send", method = RequestMethod.GET)

    //@GetMapping("/send")
    public String sendEmail() throws MessagingException, IOException {


        double total = 0.00;
        List<Products> list = productRepositoryInterface.getAllProducts();

        for (int i = 0; i < list.size(); i++) {
            total += list.get(i).getProductPrice();

        }
        System.out.println("total " + total);
//        list.add("Mila");
//        list.add("Andi");
//        list.add("mama");
//        list.add("tata");
//
//        model.addAttribute("listUsers", list);
    //System.out.println("usernames " + user.getUsername());
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("list", list);
        properties.put("sum", total);
//        properties.put("location", "Sri Lanka");
//        properties.put("sign", "Java Developer");
//
        Mail mail = new Mail();
        mail.setFrom("noreply@gmail.com");
        mail.setTo("robertvolaric973@hotmail.com");
        mail.setSubject("hi");
        mail.setHtmlTemplate(new Mail.HtmlTemplate("sample", properties));

        emailServiceInterface.sendMail(mail);
        return "sample";
    }
}
