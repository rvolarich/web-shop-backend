package com.volare_automation.springwebshop.service;

import java.sql.ResultSet;
import java.sql.SQLException;



import com.volare_automation.springwebshop.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        User user = new User();
        user.setUserid(rs.getInt("userid"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        //user.setEnabled(rs.getBoolean("enabled"));
        //System.out.println("been in RowMapper");
        return user;

    }
}