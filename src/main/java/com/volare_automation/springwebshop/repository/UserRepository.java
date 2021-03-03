package com.volare_automation.springwebshop.repository;

//import com.volare_automation.springwebshop.model.CartProductTest;
import com.volare_automation.springwebshop.model.User;
//import com.volare_automation.springwebshop.service.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository implements UserRepositoryInterface{

    JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<User> getAllUsers() {
//        String sql = "SELECT * FROM customers";
//
          List<User> customers = new ArrayList<>();//jdbcTemplate.query(
//                sql,
//                new UserRowMapper());
//
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
    public String queryForSessionId(User user){
        String query = "SELECT sessionid FROM users WHERE username=?";
        String id = jdbcTemplate.queryForObject(query, new Object[]{user.getUsername()},
                String.class);
        return  id;
    }

//    @Override
//    public void userLogin(User user){
//        String sql = "UPDATE users SET sessionid = ? WHERE username = ?";
//        String query = "SELECT sessionid FROM users WHERE username=?";
//        String queryId = "SELECT userid FROM users WHERE username=?";
//
//        String id = jdbcTemplate.queryForObject(query, new Object[]{"mila"}, String.class);
//        Integer userid = jdbcTemplate.queryForObject(queryId, new Object[]{"mila"}, Integer.class);
//
//        jdbcTemplate.update(sql, sessionId, "mila");
//    }

//    @Override
//    public User getUserById(int id) {
//
//        String sql = "SELECT * FROM customers WHERE id = ?";
//
//        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new UserRowMapper());
//
//    }

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
        String sql = "INSERT INTO users (username, password, role, enabled) VALUES (?,?,?,?)";
        int i = jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), "ROLE_USER", "true");
        if(i == 1){
            System.out.println("User registered");
            return true;
        }
        return  false;
    }

    @Override
    public void userLogin(User user) {

    }




 //   @Override
//    public boolean authUser(User user) {
//
//
//            String sql = "SELECT * FROM users";
//
//        List<User> userList = jdbcTemplate.query(
//                sql,
//                (rs, rowNum) ->
//                        new User(
//
//
//                                rs.getString("username"),
//                                rs.getString("password"),
//                                rs.getString("enabled")
//
//                        )
//        );
//        for(User u : userList){
//            if(u.getUsername().equals(user.getUsername()) &&
//                    u.getPassword().equals(user.getPassword()) && u.getIsEnabled().equals("true")) {
//                return true;
//            }
//        }
//        return false;
//    }

    @Override
    public void saveSessionId(User user, String sessionId){
        String sql = "UPDATE users SET sessionid = ? WHERE username = ?";
       // String createTable = String.format("%s %s %s %s", "CREATE", "TABLE", user.getUsername(),
         //       "(id int, name varchar)");

        jdbcTemplate.update(sql, sessionId, user.getUsername());
        //jdbcTemplate.execute(createTable);
    }

    @Override
    public String getUserId(User user) {
        String queryId = "SELECT userid FROM users WHERE username=?";
        Integer userid = jdbcTemplate.queryForObject(queryId, new Object[]{user.getUsername()}, Integer.class);
        return Integer.toString(userid);
    }

    @Override
    public String getUserNameById(Integer id) {
        String username = "";
        String queryId = "SELECT username FROM users WHERE userid=?";
        if(id != 0) {
            username = jdbcTemplate.queryForObject(queryId, new Object[]{id}, String.class);
            System.out.println("USERNAME ID = " + id);
        }
        return username;
    }

    @Override
    public Map<String, Object> getPasswordAndEnabledByUsername(User user) {

        boolean allowMap = false;
        Map<String, Object> passwordEnabledMap = new HashMap<String, Object>();
        String sql = "SELECT password, enabled FROM users WHERE username=?";
        String query = "SELECT username FROM users";

        List<String> usernameList = jdbcTemplate.queryForList(query, String.class);
        for(int i = 0; i < usernameList.size(); i++){
            if(usernameList.get(i).equals(user.getUsername())){
                allowMap = true;
            }
        }
        if(allowMap) {
            passwordEnabledMap = jdbcTemplate.queryForMap
                    (sql, new Object[]{user.getUsername()});
        }
        else{
            passwordEnabledMap.put("password","");
            passwordEnabledMap.put("enabled","");
        }
//        for(Map.Entry m:passwordEnabledMap.entrySet()){
//            System.out.println(m.getKey()+" "+m.getValue());
//        }
        return passwordEnabledMap;
    }

    @Override
    public int logoutUser(Integer userId){
        String sql = "UPDATE users SET sessionid = NULL WHERE userid = ?";
        int i = jdbcTemplate.update(sql, userId);
       return i;
    }

    @Override
    public Map<String, Object> testUserLogged(Integer userId) {
        String sql = "SELECT enabled, sessionid FROM users WHERE userid=?";
        Map<String, Object> result = new HashMap<String, Object>();
        if(userId != 0) {
            result = jdbcTemplate
                    .queryForMap(sql, new Object[]{userId});
//        for(Map.Entry m:result.entrySet()){
//            System.out.println(m.getKey()+" "+m.getValue());
//        }
        }

        return result;

    }

    @Override
    public List<String> listOfUsernames() {

        String sql = "SELECT username FROM users";
        List<String> usernameList = jdbcTemplate.queryForList(sql, String.class);
        return usernameList;
    }

    @Override
    public String userEnabled(Integer id) {

        String sql = "SELECT enabled FROM users WHERE userid=?";
        String isEnabled = jdbcTemplate.queryForObject(sql, new Object[]{id}, String.class);
        System.out.println("Is enabled iz funkcije = " + id  + isEnabled);
        return isEnabled;
    }

    @Override
    public void saveCartproductToSession(String sessionId) {
        String sql = "INSERT INTO spring_session_attributes (sessionid, products) VALUES (?,?)";
        //String sql = "UPDATE spring_session_attributes SET products = ? WHERE attribute_name= ?";
        int i = jdbcTemplate.update(sql, sessionId, "hi");

    }


}
