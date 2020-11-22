package com.volare_automation.springwebshop.service;

//import com.volare_automation.springwebshop.model.CartProductTest;
import com.volare_automation.springwebshop.model.CartProduct;
import com.volare_automation.springwebshop.model.User;
import com.volare_automation.springwebshop.repository.UserRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserServiceInterface{

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
    public List<User> getAllUsers() {
        return userRepositoryInterface.getAllUsers();
    }

    @Override
    public User getUser(int id) {

        return userRepositoryInterface.getUserById(id);

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
