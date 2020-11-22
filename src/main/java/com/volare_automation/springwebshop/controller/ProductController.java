package com.volare_automation.springwebshop.controller;

import com.volare_automation.springwebshop.model.Products;
import com.volare_automation.springwebshop.service.ProductServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductServiceInterface productServiceInterface;

    @RequestMapping(value = "/photo", method = RequestMethod.GET)
    public String getPhoto() throws SQLException, IOException {
        String s = productServiceInterface.getImageService();
        return s;
    }

    @RequestMapping(value = "/postitem", method = RequestMethod.POST)
    public void storeItem() throws SQLException {
        productServiceInterface.storeImageService();
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadImage(@RequestParam(required = true, value = "file")MultipartFile file,
                                              @RequestParam(required = true, value = "jsondata")String jsondata)  {
        return null;
    }

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public List<Products> getProducts() throws SQLException {
        return productServiceInterface.getAllProducts();
    }

}
