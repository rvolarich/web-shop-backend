package com.volare_automation.springwebshop.authentication;

import com.volare_automation.springwebshop.model.User;
import com.volare_automation.springwebshop.repository.UserRepositoryInterface;
import com.volare_automation.springwebshop.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class UserAuth {

    UserServiceInterface userServiceInterface;

    @Autowired
    private UserRepositoryInterface userRepositoryInterface;

    @Autowired
    public UserAuth(UserServiceInterface userServiceInterface){
        this.userServiceInterface = userServiceInterface;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String userLogin(HttpServletRequest request, HttpServletResponse response,
                            @RequestBody User user){

        HttpSession session = request.getSession(true);

        String sessionId = session.getId();
        userRepositoryInterface.saveSessionId(user, sessionId);



        Cookie[] cookies = request.getCookies();
        for (int i = 0; i < cookies.length; i++) {
            String name = cookies[i].getName();
            String value = cookies[i].getValue();
            if(cookies[i].getName().equals("SessionId")){
                System.out.println("sessionCookie= " + value);
            }
        }


        if(userRepositoryInterface.authUser(user)){
            Cookie cookie = new Cookie("SessionId", userServiceInterface.getSessionId(user));
            response.addCookie(cookie);
            response.addCookie(new Cookie("userid",
                    userRepositoryInterface.getUserId(user)));
            return "hi";
        }
        else return "403 Not Authorized";

    }

}
