package com.volare_automation.springwebshop.service;

import com.volare_automation.springwebshop.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        User user = new User();
        user.setId(rs.getInt("id"));
        user.setFirstname(rs.getString("firstname"));
        user.setSurname(rs.getString("surname"));
        System.out.println("been in RowMapper");
        return user;

    }
}