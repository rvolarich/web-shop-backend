package com.volare_automation.springwebshop.repository;

//import com.volare_automation.springwebshop.model.CartProductTest;
import com.volare_automation.springwebshop.model.User;
import com.volare_automation.springwebshop.service.CustomerRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository implements UserRepositoryInterface{

    JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM customers";

        List<User> customers = jdbcTemplate.query(
                sql,
                new CustomerRowMapper());

        return customers;
    }

    @Override
    public void saveUser(User u){
        String sql = "INSERT INTO customers (firstname, surname) VALUES ( ?, ?)";
        int result = jdbcTemplate.update(sql, u.getUsername(), u.getPassword());

        if (result > 0) {
            System.out.println("Insert successfully.");
        }
        else if (result==0) {
            System.out.println("Unsuccessfull insert");
        }
    }

    @Override
    public User getUserById(int id) {

        String sql = "SELECT * FROM customers WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new CustomerRowMapper());

    }

    @Override
    public void updateUser(User user) {
        if(user.getUsername() != null) {
            String sql = "UPDATE customers SET firstname = ? WHERE id = ?";
            jdbcTemplate.update(sql, user.getUsername(), user.getUserid());
            System.out.println("firstname updated");
        }
        if(user.getPassword() != null) {
            String sql = "UPDATE customers SET surname = ? WHERE id = ?";
            jdbcTemplate.update(sql, user.getPassword(), user.getUserid());
            System.out.println("surname updated");
        }


    }


    @Override
    public void deleteUserById(int id) {
        String sql = "DELETE FROM customers WHERE id = ?";
        Object [] userObject = new Object[]{id};
        int del = jdbcTemplate.update(sql, userObject);
        if(del > 0){
            System.out.println("Deleted");
        }
    }

    @Override
    public boolean regUser(User user) {
        String sql = "INSERT INTO users (username, password) VALUES (?,?)";
        int i = jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
        if(i == 1){
            return true;
        }
        return  false;
    }



    @Override
    public boolean authUser(User user) {
        boolean b = false;

            String sql = "SELECT * FROM users";

        List<User> userList = jdbcTemplate.query(
                sql,
                (rs, rowNum) ->
                        new User(

                                rs.getString("username"),
                                rs.getString("password")

                        )
        );
        for(User u : userList){
//            System.out.println("User: " + user.getPassword());
//            System.out.println("User: " + user.getUsername());
//            System.out.println("U: " + u.getPassword());
//            System.out.println("U: " + u.getUsername());
            if(u.getUsername().equals(user.getUsername()) && u.getPassword().equals(user.getPassword())) {
                return true;
            }

        }
//        if(b) System.out.println("You are logged in!");
//        else System.out.println("You are not logged in!");
        return false;
    }

//    @Override
//    public void postCartProd(CartProductTest c) {
//        String sql = "INSERT INTO one (productname) VALUES ( ?)";
//        int result = jdbcTemplate.update(sql, c.getProductName());
//
//        if (result > 0) {
//            System.out.println("Insert successfully.");
//        }
//        else if (result==0) {
//            System.out.println("Unsuccessfull insert");
//        }
//    }
}
