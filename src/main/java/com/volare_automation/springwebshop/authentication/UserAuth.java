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
                methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.OPTIONS})

public class UserAuth {

    UserServiceInterface userServiceInterface;




    @Autowired
    private UserRepositoryInterface userRepositoryInterface;

    @Autowired
    public UserAuth(UserServiceInterface userServiceInterface){
        this.userServiceInterface = userServiceInterface;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String userLogin(HttpServletRequest request, HttpServletResponse response,
                            @RequestBody User user) throws IOException {

        if(userRepositoryInterface.authUser(user)){
            HttpSession session = request.getSession();
            String sessionId = session.getId();
            userRepositoryInterface.saveSessionId(user, sessionId);

            response.addHeader("Set-Cookie",
                    String.format("%s=%s; %s; %s; %s", "SessionId", sessionId,
                            "HttpOnly;", "SameSite=Lax", "Path=/"));
            response.addHeader("Set-Cookie",
                    String.format("%s=%s; %s; %s; %s", "Userid", userRepositoryInterface.getUserId(user),
                            "HttpOnly;", "SameSite=Lax", "Path=/"));
        }




        return "hi";


//        response.addHeader("Set-Cookie",
//                String.format("%s=%s; %s; %s; %s", "SessionId", sessionId,
//                        "HttpOnly;", "SameSite=Lax", "Path=/"));
//        response.addHeader("Set-Cookie",
//                String.format("%s=%s; %s; %s; %s", "num2", sessionId,
//                        "HttpOnly;", "SameSite=Lax", "Path=/"));

        //response.setHeader("Referrer-Policy", "no-referrer");
//    Cookie cookie1 = new Cookie("TestCookie", "123456");
//    cookie1.setPath("/");
//    cookie1.setHttpOnly(true);
//    response.addCookie(cookie1);

//        Cookie[] cookies = request.getCookies();
//        for (int i = 0; i < cookies.length; i++) {
//            String name = cookies[i].getName();
//            String value = cookies[i].getValue();
//            if(cookies[i].getName().equals("SessionId")){
//                System.out.println("sessionCookie= " + value);
//            }
//        }



        //response.setHeader("Access-Control-Allow-Credentials", "true");

//
//
  }

   // @RequestMapping(value = "/cookie", method = RequestMethod.GET)
    public String log(HttpServletRequest request, HttpServletResponse response,
                             String username, String password) {



//        if(userRepositoryInterface.authUser(user)){
//            HttpSession session = request.getSession();
//            String sessionId = session.getId();
//            userRepositoryInterface.saveSessionId(user, sessionId);
////            response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:3000");
//
////            response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
////            response.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Accept");
////            response.setHeader("Access-Control-Expose-Headers", "Set-Cookie");
//            response.addHeader("Set-Cookie",
//                    String.format("%s=%s; %s; %s; %s", "SessionId", sessionId,
//                            "HttpOnly;", "SameSite=Lax", "Path=/"));
//            response.addHeader("Set-Cookie",
//                    String.format("%s=%s; %s; %s; %s", "Userid", userRepositoryInterface.getUserId(user),
//                            "HttpOnly;", "SameSite=Lax", "Path=/"));
//
//
//
//
//
//
////       response.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Accept");
////        response.setHeader("Access-Control-Expose-Headers", "SessionId");
////       response.setHeader("Access-Control-Expose-Headers", "Content-Length");
////        response.setHeader("Access-Control-Expose-Headers", "*");
////       response.setHeader("Access-Control-Expose-Headers", "*, Authorization, Content-Length, X-Kuma-Revision");
////        response.setHeader("Set-Cookie", "key=value; HttpOnly; SameSite=Lax; Path=/; Domain=127.0.0.1; Secure=true");
//
//        }
        return "logged";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutUser(HttpServletRequest request, HttpServletResponse response,
                           @RequestBody User user){

        if(userRepositoryInterface.authUser(user)){
//            Cookie cookie = new Cookie("SessionId", userServiceInterface.getSessionId(user));
//            response.addCookie(cookie);
//            response.addCookie(new Cookie("userid",
//                    userRepositoryInterface.getUserId(user)));
            userRepositoryInterface.logoutUser(user);
            return "You are logged out";
        }
        else return "403 Not Authorized";

    }

    }


