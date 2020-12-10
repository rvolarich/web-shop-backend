package com.volare_automation.springwebshop.service;

import com.volare_automation.springwebshop.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        User user = new User();
        user.setUserid(rs.getInt("id"));
        user.setUsername(rs.getString("firstname"));
        user.setPassword(rs.getString("surname"));
        System.out.println("been in RowMapper");
        return user;

    }
}