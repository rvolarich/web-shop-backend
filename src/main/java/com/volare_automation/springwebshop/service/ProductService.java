package com.volare_automation.springwebshop.service;

import com.volare_automation.springwebshop.model.Products;
import com.volare_automation.springwebshop.repository.ProductRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
public class ProductService implements ProductServiceInterface {

    @Autowired
    ProductRepositoryInterface productRepositoryInterface;


    @Override
    public String getImageService() throws IOException, SQLException {
        byte[] b = productRepositoryInterface.getImage();
        String base64Encoded = DatatypeConverter.printBase64Binary(b);
        return base64Encoded;
    }

    @Override
    public void storeImageService(){
        productRepositoryInterface.storeImage();
    }

    @Override
    public List<Products> getAllProducts() {
        return productRepositoryInterface.getAllProducts();
    }
}
