package com.volare_automation.springwebshop.authentication;

import com.google.common.io.CharStreams;
import com.volare_automation.springwebshop.model.User;
import com.volare_automation.springwebshop.repository.UserRepositoryInterface;
import com.volare_automation.springwebshop.service.UserServiceInterface;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    UserServiceInterface userServiceInterface;




    @Autowired
    private UserRepositoryInterface userRepositoryInterface;

    @Autowired
    public UserAuth(UserServiceInterface userServiceInterface){
        this.userServiceInterface = userServiceInterface;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public boolean userLogin(HttpServletRequest request, HttpServletResponse response,
                            @RequestBody User user) throws IOException {



        if(userRepositoryInterface.authUser(user)){

            String sessionId = userServiceInterface.generateSessionId();


            userRepositoryInterface.saveSessionId(user, sessionId);

            response.addHeader("Set-Cookie",
                    String.format("%s=%s; %s; %s; %s",
                            "SessionId", sessionId,
                            "HttpOnly;", "SameSite=Lax", "Path=/"));
            response.addHeader("Set-Cookie",
                    String.format("%s=%s; %s; %s; %s", "UserId", userRepositoryInterface.getUserId(user),
                            "HttpOnly;", "SameSite=Lax", "Path=/"));
            return true;
        }

        return false;

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public boolean logoutUser(HttpServletRequest request){

        userServiceInterface.logoutUser(request);
            return false;



    }

    }


