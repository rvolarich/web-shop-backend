package com.volare_automation.springwebshop.service;
import com.volare_automation.springwebshop.model.Products;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductsRowMapper implements RowMapper<Products> {
    @Override
    public Products mapRow(ResultSet rs, int i) throws SQLException {
        Products products = new Products();
        products.setProduct_id(rs.getInt("product_id"));
        products.setProduct_name(rs.getString("product_name"));
        products.setProduct_description(rs.getString("product_description"));
        products.setProduct_quantity(rs.getInt("product_quantity"));
        products.setProduct_price(rs.getDouble("product_price"));
        products.setProduct_image(rs.getString("product_image"));
        return products;
    }
}
