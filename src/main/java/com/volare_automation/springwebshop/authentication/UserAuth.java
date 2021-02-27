package com.volare_automation.springwebshop.authentication;

import com.volare_automation.springwebshop.model.User;
import com.volare_automation.springwebshop.model.UserAuthDataModel;
import com.volare_automation.springwebshop.repository.UserRepositoryInterface;
import com.volare_automation.springwebshop.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:3000", allowCredentials = "true",
                methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
                        RequestMethod.OPTIONS, RequestMethod.DELETE})

public class UserAuth {

    UserServiceInterface userServiceInterface;




    @Autowired
    private UserRepositoryInterface userRepositoryInterface;

    @Autowired
    public UserAuth(UserServiceInterface userServiceInterface){
        this.userServiceInterface = userServiceInterface;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void getSession(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        session.setAttribute("ghztr", "jhhj");
    }

    @RequestMapping(value = "/logged_in", method = RequestMethod.GET)
    public UserAuthDataModel loggedIn(HttpServletRequest request) {

        UserAuthDataModel userAuthDataModel = new UserAuthDataModel();
        userAuthDataModel.setUsername(userServiceInterface.getUserName(request));
        userAuthDataModel.setLogged(false);
        if(userServiceInterface.testUserLogged(request)){
            userAuthDataModel.setLogged(true);
        }
        return  userAuthDataModel;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserAuthDataModel userLogin(HttpServletRequest request, HttpServletResponse response,
                                       @RequestBody User user) throws IOException {

        UserAuthDataModel userAuthDataModel = new UserAuthDataModel();
        userAuthDataModel.setUsername(user.getUsername());
        userAuthDataModel.setLogged(false);

        if(userServiceInterface.authUser(user).equals("authenticated")){

            String sessionId = userServiceInterface.generateSessionId();


            userRepositoryInterface.saveSessionId(user, sessionId);

            response.addHeader("Set-Cookie",
                    String.format("%s=%s; %s; %s; %s",
                            "SessionId", sessionId,
                            "HttpOnly;", "SameSite=Lax", "Path=/"));
            response.addHeader("Set-Cookie",
                    String.format("%s=%s; %s; %s; %s", "UserId", userRepositoryInterface.getUserId(user),
                            "HttpOnly;", "SameSite=Lax", "Path=/"));

            userAuthDataModel.setLogged(true);
            userAuthDataModel.setLoginStatus("");

        }

        else if(userServiceInterface.authUser(user).equals("disabled")){
            userAuthDataModel.setLoginStatus("The user is disabled!");
        }

        else if (userServiceInterface.authUser(user).equals("wrongUsernameOrPassword")){
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

        userServiceInterface.logoutUser(request);
            return false;



    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public boolean register(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {


        if(!userServiceInterface.userExists(user)){
            System.out.println("user exists");
            return false;
        }
        else return userRepositoryInterface.regUser(user);
    }

    }


