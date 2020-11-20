package com.volare_automation.springwebshop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.*;
import java.sql.*;

@Repository
public class ItemRepository implements ItemRepositoryInterface {

    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;


    private byte[] imgBytes;
    InputStream is;
    Connection conn;
    PreparedStatement preparedStatement;
    FileInputStream fileInputStream = null;

//    public Connection getConnection() throws SQLException {
//        conn = DriverManager
//                .getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
//        return conn;
//    }



    @PostConstruct
    private void postConstruct() throws IOException, SQLException {
        jdbcTemplate = new JdbcTemplate(dataSource);
        storeImage();
        System.out.println("Iv been in post construct");
    }

//    public InputStream inputStream () throws SQLException {
//        conn = DriverManager
//                .getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
//        PreparedStatement ps = conn.prepareStatement("SELECT data FROM images WHERE name=?");
//        ps.setString(1, "fcu.jpg");
//        ResultSet rs = ps.executeQuery();
//        if (rs != null) {
//            while(rs.next()) {
//                imgBytes = rs.getBytes(1);
//                is = rs.getBinaryStream(2);
//            }
//            rs.close();
//        }
//        ps.close();
//        return is;
//    }

    @Override
    public byte[] getImage() throws SQLException, IOException {




        String sql = "SELECT * FROM images";
        conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        //FileOutputStream fileOutputStream = new FileOutputStream("G:/fcu.jpg");
        if (rs != null) {
            while(rs.next()) {
                imgBytes = rs.getBytes(2);
               // fileOutputStream.write(imgBytes);
            }
            //fileOutputStream.close();
            rs.close();
        }
        ps.close();
//        while(rs.next()){
//            Blob imageBlob = rs.getBlob(2);
//            FileOutputStream fileOutputStream = new FileOutputStream("G:/");
//            fileOutputStream.write(imageBlob.getBytes(1, (int) imageBlob.length()));
//
//        }
        return imgBytes;
    }

    @Override
    public void storeImage(){
//        String sql = "INSERT INTO num(number) VALUES  (?)";
//        conn = DriverManager
//                .getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
//        preparedStatement = conn.prepareStatement(sql);
//        preparedStatement.setInt(1,5);
//        preparedStatement.executeUpdate();
        String sql = "INSERT INTO products (product_name, product_description, " +
                "product_quantity, product_price, product_image) VALUES  (?,?,?,?,?)";
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
             PreparedStatement pst = con.prepareStatement(sql)) {

            File img = new File("C:/borg_ship.jpg");

            try (FileInputStream fin = new FileInputStream(img)) {

                pst.setBinaryStream(5, fin, (int) img.length());
                pst.setString(1, "Ultimate Vessel Borg ");
                pst.setString(2, "The Conqueror Of The Universe");
                pst.setInt(3, 5);
                pst.setDouble(4, 249.98);
                pst.executeUpdate();
            } catch (IOException ex) {
                Logger.getLogger(ItemRepository.class.getName()).log(
                        Level.SEVERE, ex.getMessage(), ex);
            }

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(ItemRepository.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    }

//    public void storeImage() {
//        String sql = "INSERT INTO num(number) VALUES  (?)";
//
//        jdbcTemplate.update(connection -> {
//
//            PreparedStatement ps = connection.prepareStatement(sql);
//            ps.setInt(1, 1);
//
////                File file = new File("F:/fcu.jpg");
////
////                try {
////                    fileInputStream = new FileInputStream(file);
////                } catch (FileNotFoundException e) {
////                    e.printStackTrace();
////                }
////                ps.setString(2, file.getName());
////                ps.setBinaryStream(3, fileInputStream);
//            return ps;


 //       });
  //  }
