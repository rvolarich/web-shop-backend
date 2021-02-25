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
@CrossOrigin("http://127.0.0.1:3000")
public class UserController {

    @Autowired
    private UserServiceInterface userServiceInterface;

    @Autowired
    private UserRepositoryInterface userRepositoryInterface;

    @Autowired
    JdbcTemplate jdbcTemplate;


//    @RequestMapping(value = "/", method = RequestMethod.OPTIONS)
//    public void options (){
//
//    }

    @RequestMapping(value = "/testit", method = RequestMethod.POST)
    public void getString(@ModelAttribute ("user") User user) {

        System.out.println("This is name: " +  user.getUsername());
    }




//    @RequestMapping(value = "/logged_in", method = RequestMethod.GET)
//    public boolean loggedIn(HttpServletRequest request, HttpServletResponse response) {
        //request.getSession().invalidate();

        //HttpSession newSession = request.getSession(true);
        //System.out.println("session reg: " + newSession.getId());

//        System.out.println("session: " + request.getRequestedSessionId());
        //Cookie ck = new Cookie("session_id", newSession.getId());
        //System.out.println("SessionID: " + request.getRequestedSessionId());
        //System.out.println("Request: " + request.getRequestedSessionId());
        //response.addCookie(ck);
        //response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        //response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        //return userRepositoryInterface.regUser(user);
        
//        if(newSession != null){
//            return  true;
//        }
//        else return  false;
//    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public boolean register(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
        //request.getSession().invalidate();
//        final HttpSession newSession = request.getSession();
//        System.out.println("session reg: " + newSession.getId());

//        System.out.println("session: " + request.getRequestedSessionId());
//          Cookie ck = new Cookie("session_id", newSession.getId());
//        System.out.println(ck.getName()+ " " + ck.getValue());
//          response.addCookie(ck);
        
        return userRepositoryInterface.regUser(user);
    }

    @PostMapping("/persistMessage")
    public String persistMessage(HttpServletRequest request, HttpServletResponse response) {
        //@SuppressWarnings("unchecked")

        //System.out.println("request: " + s);
        //List<String> messages = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
//        if (messages == null) {
//            messages = new ArrayList<>();
//            request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
//        }
        //messages.add(msg);
//        request.getSession();
//        System.out.println("session: " + request.getRequestedSessionId());
//        Cookie ck = new Cookie("sessid", request.getRequestedSessionId());
//        System.out.println(ck.getName()+ " " + ck.getValue());
//        response.addCookie(ck);
        return "redirect:/";
    }








//    @RequestMapping(value = "/testit", method = RequestMethod.POST)
//    public void getString(HttpServletRequest request) throws IOException {
//        StringBuilder buffer = new StringBuilder();
//        BufferedReader br = request.getReader();
//        String line;
//        while((line = br.readLine()) != null){
//            buffer.append(line);
//            buffer.append(System.lineSeparator());
//        }
//        String data = buffer.toString();
//        System.out.println("This is name: " +  buffer);
//    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUsers(){
        return userServiceInterface.getAllUsers();
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable int id){
        return userServiceInterface.getUser(id);
    }

    @RequestMapping(value = "users/save", method = RequestMethod.POST)
    public void saveUser(@RequestBody User u){
        userServiceInterface.saveUser(u);
    }

    @RequestMapping(value = "users/update", method = RequestMethod.PUT)
    public void updateUser(@RequestBody User u){
        userServiceInterface.updateUser(u);
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
