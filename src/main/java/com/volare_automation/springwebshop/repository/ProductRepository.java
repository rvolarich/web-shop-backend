package com.volare_automation.springwebshop.repository;

import com.volare_automation.springwebshop.model.CartProduct;
import com.volare_automation.springwebshop.model.Products;
import com.volare_automation.springwebshop.service.CartProductRowMapper;
import com.volare_automation.springwebshop.service.ProductsRowMapper;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.*;
import java.sql.*;

@Repository
public class ProductRepository implements ProductRepositoryInterface {

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
    public List<Products> getAllProducts (){
        String sql = "SELECT * FROM products ORDER BY productId ASC";
        //jdbcTemplate.execute("SELECT * FROM products ORDER BY productId ASC;");
        List<Products> products = jdbcTemplate.query(
                sql,
                new ProductsRowMapper());
        return products;
    }

    @Override
    public void postCartProduct(CartProduct cp, boolean allowUpdate) {
        String sql = "INSERT INTO guestcart (productId, productName, productDescription, productQuantity," +
                " productPrice, productImage, productStock) " + "VALUES ( ?, ?, ?, ?, ?, ?, ?)";
        String query = "SELECT productId FROM guestcart";
        String updateQty = "UPDATE guestcart SET productquantity = ? WHERE productid = ?";
        String getSingleQty = "SELECT productquantity FROM guestcart WHERE productid = ?";
        String getProductStock = "SELECT productquantity FROM products WHERE productid = ?";
        List<Integer> idList = jdbcTemplate.queryForList(query, Integer.class);
        for(int i = 0; i < idList.size(); i++){
            if(idList.get(i) == cp.getProductId()){
                Integer total = jdbcTemplate.queryForObject(getSingleQty,
                        new Object[]{cp.getProductId()}, Integer.class) + cp.getProductQuantity();
                jdbcTemplate.update(updateQty, total, cp.getProductId());
                allowUpdate = false;
            }
        }

        if(allowUpdate){
            Integer stock = jdbcTemplate.queryForObject(getProductStock,
                    new Object[]{cp.getProductId()}, Integer.class);
            int result = jdbcTemplate.update(sql, cp.getProductId(), cp.getProductName(), cp.getProductDescription(),
                    cp.getProductQuantity(), cp.getProductPrice(), cp.getProductImage(), stock);

            if (result > 0) {
                System.out.println("Insert successfully into Guest Cart.");
            }
            else if (result==0) {
                System.out.println("Unsuccessfull insert");
            }
        }









    }

    @Override
    public List<Integer> getProductId() {

        String query = "SELECT productId FROM guestcart";
        List<Integer> ids = jdbcTemplate.queryForList(query,Integer.class);

        return ids;
    }

    @Override
    public List<CartProduct> getCartProducts() {
        String sql = "SELECT * FROM guestcart";

        List<CartProduct> cartProducts = jdbcTemplate.query(
                sql,
                new CartProductRowMapper());
        return cartProducts;
    }

    @Override
    public Integer getTableQty() {
        Integer totalQuantity = 0;
        List<Integer> totalQty = new ArrayList<Integer>();
        String queryForQty = "SELECT productquantity FROM guestcart";


        totalQty = jdbcTemplate.queryForList(queryForQty, Integer.class);

        for(int i = 0; i < totalQty.size(); i++){
            totalQuantity += totalQty.get(i);
        }



        return totalQuantity;
    }

    @Override
    public List<Integer> getCartItemQty() {
        List<Integer> qtyCartItemList = new ArrayList<Integer>();
        String query = "SELECT productquantity FROM guestcart";
        qtyCartItemList = jdbcTemplate.queryForList(query, Integer.class);
        return qtyCartItemList;
    }

    @Override
    public List<CartProduct> deleteCart() {
        String sql = "DELETE FROM guestcart";
        String query = "SELECT * FROM guestcart";

        int delInt = jdbcTemplate.update(sql);
        List<CartProduct> cp = jdbcTemplate.queryForList(query, CartProduct.class);
        return cp;
    }

    @Override
    public List<CartProduct> deleteCartById(Integer id) {
        String sql = "DELETE FROM guestcart WHERE productid = ?";
        String query = "SELECT * FROM guestcart";

        Object [] cartItem = new Object[]{id};
        System.out.println("ID: " + id);
        int i = jdbcTemplate.update(sql, cartItem);
        if(i == 0){
            System.out.println("unsuccesfull delete");
        }else{
            System.out.println("succesfull delete");
        }
        List<CartProduct> cartProducts = jdbcTemplate.query(
                query,
                new CartProductRowMapper());
        return cartProducts;
    }

    @Override
    public CartProduct postCartAll(List<CartProduct> cpl) {

        String query = "insert into guestcart (productId, productName, productDescription, " +
                "productQuantity, productPrice, productImage, productStock) values (?,?,?,?,?,?,?)";
        String getProductStock = "SELECT productquantity FROM products WHERE productid = ?";
        jdbcTemplate.execute("DELETE FROM guestcart");
        List<Object[]> inputList = new ArrayList<Object[]>();
        for(CartProduct emp:cpl){
            if(emp.getProductQuantity() > 0) {
                Integer stock = jdbcTemplate.queryForObject(getProductStock,
                        new Object[]{emp.getProductId()}, Integer.class);
                Object[] tmp = {emp.getProductId(), emp.getProductName(), emp.getProductDescription(),
                        emp.getProductQuantity(), emp.getProductPrice(), emp.getProductImage(), stock};
                inputList.add(tmp);
            }
        }
        CartProduct cartProduct = new CartProduct();
        int [] iList = jdbcTemplate.batchUpdate(query, inputList);
        for(int i = 0; i < iList.length; i++){
            if(iList[i] != 1){
                //cartProduct.setCartUpdated(false);
            }else {
//                cartProduct.setCartUpdated(true);
//                cartProduct.setTotalCartQty(getTableQty());
            }
        }

        return cartProduct;
    }

    @Override
    public void confirmCartOrder(List<CartProduct> cp) {
        boolean allowUpdate = true;

        String getProductStock = "SELECT productquantity FROM products WHERE productid = ?";
        String sql = "UPDATE products SET productquantity = ? WHERE productid = ?";
        for(CartProduct c:cp){
            Object [] tmp = {c.getProductId(), c.getProductQuantity()};
            Integer stock = jdbcTemplate.queryForObject(getProductStock,
                    new Object[]{c.getProductId()}, Integer.class);
            if(stock - c.getProductQuantity() < 0){
                allowUpdate = false;
            }
            System.out.println("result: " + (stock - c.getProductQuantity()));
            if(allowUpdate){
                jdbcTemplate.execute("DELETE FROM guestcart");
                jdbcTemplate.update(sql, (stock - c.getProductQuantity()), c.getProductId());
            }

        }
    }


    @Override
    public byte[] getImage() throws SQLException, IOException {




        String sql = "SELECT * FROM images";
        conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        //FileOutputStream fileOutputStream = new FileOutputStream("G:/fcu.jpg");
        if (rs != null) {
            int index = 0;
            while(rs.next()) {
                imgBytes = rs.getBytes(6);
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
                Logger.getLogger(ProductRepository.class.getName()).log(
                        Level.SEVERE, ex.getMessage(), ex);
            }

        } catch (SQLException ex) {

            Logger lgr = Logger.getLogger(ProductRepository.class.getName());
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
