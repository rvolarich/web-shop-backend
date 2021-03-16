package com.volare_automation.springwebshop.authentication;

import com.lowagie.text.DocumentException;
import com.volare_automation.springwebshop.model.User;
import com.volare_automation.springwebshop.model.UserAuthDataModel;
import com.volare_automation.springwebshop.repository.ProductRepositoryInterface;
import com.volare_automation.springwebshop.repository.UserRepositoryInterface;
import com.volare_automation.springwebshop.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:3000", allowCredentials = "true",
                methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
                        RequestMethod.OPTIONS, RequestMethod.DELETE})

public class UserAuth {

    private boolean stayLogged;
    private boolean userLogged;
    UserServiceInterface userServiceInterface;

    @Autowired
    private ProductRepositoryInterface productRepositoryInterface;

    @Autowired
    private UserRepositoryInterface userRepositoryInterface;

    @Autowired
    public UserAuth(UserServiceInterface userServiceInterface){
        this.userServiceInterface = userServiceInterface;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void getSession(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(60);
        session.setAttribute(session.getId(), "");
       // userRepositoryInterface.saveCartproductToSession(session.getId());
    }

    @RequestMapping(value = "/logged_in", method = RequestMethod.GET)
    public UserAuthDataModel loggedIn(HttpServletRequest request,
                                      HttpServletResponse response) {



        System.out.println("logged: " + stayLogged);
        UserAuthDataModel userAuthDataModel = new UserAuthDataModel();
        userAuthDataModel.setLogged(false);

        if(userLogged){
            Cookie [] cookies = request.getCookies();
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("SessionId")){
                    userAuthDataModel.setSessionExpired(false);
                }else{
                    userAuthDataModel.setSessionExpired(true);
                }
            }
        }
        //userAuthDataModel.setStayLogged(stayLogged);
        if(userServiceInterface.testUserLogged(request)){

            userAuthDataModel.setNameName(userServiceInterface.getName(request));
            userAuthDataModel.setLogged(true);
            int maxAge;
            if(stayLogged){
                maxAge = 600;
            }
            else{
                maxAge = 180;
            }
            System.out.println("staylogged u logged_in: " + stayLogged);
            System.out.println("userLogged u logged_in: " + userLogged);
            String sessionId = userServiceInterface.getSessionIdFromCookie(request);

                response.addHeader("Set-Cookie",
                        String.format("%s=%s; %s; %s; %s; %s=%s",
                                "SessionId", sessionId,
                                "HttpOnly;", "SameSite=Lax", "Path=/", "Max-Age", maxAge));

        }
        return  userAuthDataModel;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserAuthDataModel userLogin(HttpServletRequest request, HttpServletResponse response,
                                       @RequestBody User user) throws IOException {

        UserAuthDataModel userAuthDataModel = new UserAuthDataModel();
        userAuthDataModel.setUsername(user.getUsername());
        userAuthDataModel.setLogged(false);
        String userAuthData = userServiceInterface.authUser(user);
        System.out.println("bio u login");

        if(userAuthData.equals("authenticated")){

            userLogged = true;
            String sessionId = userServiceInterface.generateSessionId();
            userRepositoryInterface.saveSessionId(user, sessionId);
            int maxAge;
            if(user.isStayLogged()){
               maxAge = 600;
               stayLogged = true;
            }
            else{
                maxAge = 180;
                stayLogged = false;
            }
            System.out.println("stayLogged u login: " + stayLogged);
            System.out.println("userLogged u login: " + userLogged);
            System.out.println("maxAge: " + maxAge);
            response.addHeader("Set-Cookie",
                    String.format("%s=%s; %s; %s; %s; %s=%s",
                            "SessionId", sessionId,
                            "HttpOnly;", "SameSite=Lax", "Path=/", "Max-Age", maxAge));
            response.addHeader("Set-Cookie",
                    String.format("%s=%s; %s; %s; %s", "UserId", userRepositoryInterface.getUserId(user),
                            "HttpOnly;", "SameSite=Lax", "Path=/"));

            userAuthDataModel.setLogged(true);
            userAuthDataModel.setLoginStatus("");

            productRepositoryInterface.createTable(userRepositoryInterface.getUserId(user));

        }

        else if(userAuthData.equals("disabled")){
            userAuthDataModel.setLoginStatus("The user account is not activated!");
        }

        else if (userAuthData.equals("wrongUsernameOrPassword")){
            userAuthDataModel.setLoginStatus("The username or password is incorrect!");
        }



//        Integer id = userServiceInterface.getUserIdFromCookie(request);
//
//        if(userRepositoryInterface.userEnabled(id).equals("true")){
//            userAuthDataModel.setLoginStatus("");
//        }
//        else userAuthDataModel.setLoginStatus("User is disabled!");

        return userAuthDataModel;

    }

//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public boolean userLogin(HttpServletRequest request, HttpServletResponse response,
//                             @RequestBody User user) throws IOException {
//
//
//
//        if(userRepositoryInterface.authUser(user)){
//
//            String sessionId = userServiceInterface.generateSessionId();
//
//
//            userRepositoryInterface.saveSessionId(user, sessionId);
//
//            response.addHeader("Set-Cookie",
//                    String.format("%s=%s; %s; %s; %s",
//                            "SessionId", sessionId,
//                            "HttpOnly;", "SameSite=Lax", "Path=/"));
//            response.addHeader("Set-Cookie",
//                    String.format("%s=%s; %s; %s; %s", "UserId", userRepositoryInterface.getUserId(user),
//                            "HttpOnly;", "SameSite=Lax", "Path=/"));
//            return true;
//        }
//
//        return false;
//
//    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public boolean logoutUser(HttpServletRequest request){

        stayLogged = false;
        userLogged = false;
        userServiceInterface.logoutUser(request);
            return false;
    }

    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public void resetUserLoginStatusVariables(){

        stayLogged = false;
        userLogged = false;
        System.out.println("bio u reset");

    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public boolean register(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) throws IOException, MessagingException, DocumentException {



        return userServiceInterface.registerUser(request, user);
    }

    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    public boolean activateAccount(HttpServletResponse response, @RequestParam String token){

        System.out.println("token " + token);
        response.addHeader("Set-Cookie",
                String.format("%s=%s; %s; %s; %s;",
                        "SessionId", "welcome",
                        "HttpOnly;", "SameSite=Lax", "Path=/"));
        userRepositoryInterface.activateUser(token);
        return true;
    }

    @RequestMapping(value = "/user/del", method = RequestMethod.GET)
    public boolean deleteUser(HttpServletRequest request){

        stayLogged = false;
        userLogged = false;

        if(userServiceInterface.deleteUser(request)){
            return true;
        }
        return false;
    }

    }


