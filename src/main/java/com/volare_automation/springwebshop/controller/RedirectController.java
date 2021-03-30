package com.volare_automation.springwebshop.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class RedirectController implements ErrorController{



        @RequestMapping("/error")
        public String handleError() {
            //do something like logging
            System.out.println("bio u error-redirect");
            return "index";
        }

        @Override
        public String getErrorPath() {
            return null;
        }




}
