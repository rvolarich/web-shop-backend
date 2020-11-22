package com.volare_automation.springwebshop.repository;

import com.volare_automation.springwebshop.model.Products;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public interface ProductRepositoryInterface {

    public byte [] getImage() throws SQLException, IOException;
    public void storeImage();
    public List<Products> getAllProducts();
}
