package com.volare_automation.springwebshop.service;

//import com.volare_automation.springwebshop.model.CartProductTest;
import com.volare_automation.springwebshop.model.CartProduct;
import com.volare_automation.springwebshop.model.User;
import com.volare_automation.springwebshop.repository.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserService implements UserServiceInterface{

    private  Integer userId;
    private String sessionId;

    @Autowired
    UserRepositoryInterface userRepositoryInterface;

    List<CartProduct> list = new ArrayList<>();

//    public UserService() {
//        System.out.println("Service layer created");
//        list.add(new CartProducts("čokolada", 2, 10 ,1));
//        list.add(new CartProducts("sladoled", 3, 15 ,2));
//        list.add(new CartProducts("masline", 5, 50 ,3));
//    }


    public List<CartProduct> getList() {

        return list;
    }

    @Override
    public void printUser(User u) {
        System.out.println("user = " + u.getUsername());
    }

    @Override
    public boolean logoutUser(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("UserId")) userId = Integer.parseInt(cookie.getValue());
        }

        System.out.println("UserId = " + userId);
        int i = userRepositoryInterface.logoutUser(userId);
        if(i == 1) return false;
        else return true;
    }

    @Override
    public boolean testUserLogged(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("UserId")) userId = Integer.parseInt(cookie.getValue());
            if(cookie.getName().equals("SessionId")) sessionId = cookie.getValue();
        }
       if(sessionId.equals(userRepositoryInterface.testUserLogged(userId))){
           return true;
       }

        return false;
    }

    @Override
    public String getUserId(User user) {
        return userRepositoryInterface.getUserId(user);
    }

//    @Override
//    public Integer parseUserId(HttpServletRequest request) {
//        Integer userId = 1;
//        StringBuilder sb = new StringBuilder();
//        Cookie[] cookies = request.getCookies();
//        for(Cookie cookie : cookies) {
//            if (cookie.getName().charAt(0) == '$') {
//                for (int i = 1; i < cookie.getName().toCharArray().length; i++) {
//                    if (cookie.getName().charAt(i) == '&') {
//                        break;
//                    } else {
//                        sb.append(cookie.getName().charAt(i));
//                    }
//
//                    System.out.println("sb je = " + sb.toString());
//                }
//            }
//        }
//        return userId;
//    }

    @Override
    public String generateSessionId() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 50;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepositoryInterface.getAllUsers();
    }

    @Override
    public User getUser(int id) {

        return userRepositoryInterface.getUserById(id);

    }

    @Override
    public String getSessionId(User user){
        return userRepositoryInterface.queryForSessionId(user);
    }

//    @Override
//    public User getUser(int id) {
//        for(User u : list){
//            if(u.getId() == id){
//                return u;
//            }
//        }
//        return null;
//    }

//    @Override
//    public void saveUser(User u) {
//        this.list.add(u);
//    }

    @Override
    public void saveUser(User u) {

        userRepositoryInterface.saveUser(u);
    }

    @Override
    public void updateUser(User user) {
        userRepositoryInterface.updateUser(user);
    }

//    @Override
//    public void updateUser(User user) {
//        for(User u : list){
//            if(u.getId() == user.getId()){
//                if(user.getFirstname() != null) {
//                    u.setFirstname(user.getFirstname());
//                }
//                if(user.getSurname() != null) {
//                    u.setSurname(user.getSurname());
//                }
//
//            }
//        }
//    }

    @Override
    public void deleteUser(int id) {
        userRepositoryInterface.deleteUserById(id);
    }

//    @Override
//    public void postCartProduct(CartProductTest c) {
//
//        userRepositoryInterface.postCartProd(c);
//    }

//    @Override
//    public void deleteUser(int id) {
//        list.remove(id);
//    }
}
