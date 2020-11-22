package com.volare_automation.springwebshop.repository;

//import com.volare_automation.springwebshop.model.CartProductTest;
import com.volare_automation.springwebshop.model.User;
import com.volare_automation.springwebshop.service.CustomerRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
        int result = jdbcTemplate.update(sql, u.getFirstname(), u.getSurname());

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
        if(user.getFirstname() != null) {
            String sql = "UPDATE customers SET firstname = ? WHERE id = ?";
            jdbcTemplate.update(sql, user.getFirstname(), user.getId());
            System.out.println("firstname updated");
        }
        if(user.getSurname() != null) {
            String sql = "UPDATE customers SET surname = ? WHERE id = ?";
            jdbcTemplate.update(sql, user.getSurname(), user.getId());
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
