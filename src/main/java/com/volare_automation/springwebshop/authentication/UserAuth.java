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
import java.io.*;
import java.util.List;

@RestController
@CrossOrigin(allowCredentials = "true",
                methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
                        RequestMethod.OPTIONS, RequestMethod.DELETE})

public class UserAuth {


    UserServiceInterface userServiceInterface;

    @Autowired
    private ProductRepositoryInterface productRepositoryInterface;

    @Autowired
    private UserRepositoryInterface userRepositoryInterface;

    @Autowired
    public UserAuth(UserServiceInterface userServiceInterface){
        this.userServiceInterface = userServiceInterface;
    }

//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public void getSession(HttpServletRequest request, HttpServletResponse response){
//        HttpSession session = request.getSession();
//        session.setMaxInactiveInterval(60);
//        session.setAttribute(session.getId(), "");
//       // userRepositoryInterface.saveCartproductToSession(session.getId());
//    }

    @RequestMapping(value = "/logged_in", method = RequestMethod.GET)
    public UserAuthDataModel loggedIn(HttpServletRequest request,
                                      HttpServletResponse response) {




        UserAuthDataModel userAuthDataModel = new UserAuthDataModel();
        userAuthDataModel.setLogged(false);


        Cookie [] cookies = request.getCookies();

        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("UserId")) {
                    userAuthDataModel.setSessionExpired(true);
                    for (Cookie c : cookies) {
                        if (c.getName().equals("SessionId")) {
                            userAuthDataModel.setSessionExpired(false);
                        }
                    }

                } else {
                    userAuthDataModel.setSessionExpired(false);
                }
            }
        }

        if(userAuthDataModel.isSessionExpired()){
            response.addHeader("Set-Cookie", "SessionId=0; HttpOnly; SameSite=Lax; Path=/; Max-Age=0;");
            response.addHeader("Set-Cookie", "UserId=0; HttpOnly; SameSite=Lax; Path=/; Max-Age=0;");
            response.addHeader("Set-Cookie", "ExpValue=0; HttpOnly; SameSite=Lax; Path=/; Max-Age=0;");
        }

        String userAuthData = userServiceInterface.testUserLogged(request);

        if(userAuthData.equals("userAuthenticated") || userAuthData.equals("adminAuthenticated")){

            if(userAuthData.equals("adminAuthenticated")){
                userAuthDataModel.setAdminLogged(true);
            }
            else{
                userAuthDataModel.setAdminLogged(false);
            }
            userAuthDataModel.setNameName(userServiceInterface.getName(request));
            userAuthDataModel.setLogged(true);
            int maxAge = userServiceInterface.getExpValueFromCookie(request);
            System.out.println("staylogged u logged_in: ");
            System.out.println("userLogged u logged_in: ");
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

        if(userAuthData.equals("userAuthenticated") || userAuthData.equals("adminAuthenticated")){


            String sessionId = userServiceInterface.generateSessionId();
            userRepositoryInterface.saveSessionId(user, sessionId);

            int maxAge;
            if(user.isStayLogged()){
               maxAge = 1800;
               response.addHeader("Set-Cookie",
                       String.format("ExpValue=%s; HttpOnly; SameSite=Lax; Path=/;", maxAge));
            }
            else{
                maxAge = 300;
                response.addHeader("Set-Cookie",
                        String.format("ExpValue=%s; HttpOnly; SameSite=Lax; Path=/;", maxAge));
            }

            response.addHeader("Set-Cookie",
                    String.format("%s=%s; %s; %s; %s; %s=%s",
                            "SessionId", sessionId,
                            "HttpOnly;", "SameSite=Lax", "Path=/", "Max-Age", maxAge));
            response.addHeader("Set-Cookie",
                    String.format("%s=%s; %s; %s; %s", "UserId", userRepositoryInterface.getUserId(user),
                            "HttpOnly;", "SameSite=Lax", "Path=/"));

            userAuthDataModel.setLogged(true);
            if(userAuthData.equals("adminAuthenticated")){
                userAuthDataModel.setAdminLogged(true);
            }
            else{
                userAuthDataModel.setAdminLogged(false);
            }
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
    public boolean logoutUser(HttpServletRequest request, HttpServletResponse response){

        response.addHeader("Set-Cookie", "SessionId=0; HttpOnly; SameSite=Lax; Path=/; Max-Age=0;");
        response.addHeader("Set-Cookie", "UserId=0; HttpOnly; SameSite=Lax; Path=/; Max-Age=0;");
        response.addHeader("Set-Cookie", "ExpValue=0; HttpOnly; SameSite=Lax; Path=/; Max-Age=0;");
        userServiceInterface.logoutUser(request);
            return false;
    }


    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public boolean register(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) throws IOException, MessagingException, DocumentException {



        return userServiceInterface.registerUser(request, user);
    }

    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    public boolean activateAccount(HttpServletResponse response, @RequestParam String token){

        boolean activateUser = userRepositoryInterface.saveActivationToken(token);
        if(activateUser) {
            response.addHeader("Set-Cookie",
                    String.format("%s=%s; %s; %s; %s;",
                            "SessionId", "welcome",
                            "HttpOnly;", "SameSite=Lax", "Path=/"));
            userRepositoryInterface.activateUser(token);
            System.out.println("bio u userAuth");
            return true;
        }
        return false;
    }

    //reset password email
    @RequestMapping(value = "/pass", method = RequestMethod.POST)
    public String resetPasswordEmail(@RequestBody User user) throws DocumentException, MessagingException, IOException {

        String userName = "";
        List<String> userlist = userRepositoryInterface.listOfUsernames();
        System.out.println("user list: " + userlist);
        if(userlist.size() > 0){
            for(int i = 0; i < userlist.size(); i++){
                if(userlist.get(i).equals(user.getUsername())){
                    userName = userRepositoryInterface.getNameByUsername(user.getUsername());
                    userServiceInterface.resetPasswordEmail(userName, user.getUsername());
                    return "An email has been sent to the entered address with password reset link!";
                }
            }
        }
        return "Error sending email address!";
    }

    @RequestMapping(value = "/passhgjgJHGhk76JhjgjhewerRTEiopopijLJKoiiuuitwJH6738", method = RequestMethod.POST)
    public String resetPassword(@RequestBody User user) throws DocumentException, MessagingException, IOException {

        if(user.getUsername() == null){
            return "Operation not allowed!";
        }

        String hashPassword = userServiceInterface.encodePassword(user.getPassword());
        boolean passUpdate = userRepositoryInterface.updatePasswordByUsername(user.getUsername(), hashPassword);
        if(passUpdate){
            return "Reseting password...";
        }
        return "Error reseting password!";
    }

    @RequestMapping(value = "/user/del", method = RequestMethod.GET)
    public boolean deleteUser(HttpServletRequest request, HttpServletResponse response){

        response.addHeader("Set-Cookie", "SessionId=0; HttpOnly; SameSite=Lax; Path=/; Max-Age=0;");
        response.addHeader("Set-Cookie", "UserId=0; HttpOnly; SameSite=Lax; Path=/; Max-Age=0;");
        response.addHeader("Set-Cookie", "ExpValue=0; HttpOnly; SameSite=Lax; Path=/; Max-Age=0;");


        if(userServiceInterface.deleteUser(request)){
            return true;
        }
        return false;
    }

    }


