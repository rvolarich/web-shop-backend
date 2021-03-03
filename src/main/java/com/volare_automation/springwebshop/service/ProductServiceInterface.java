package com.volare_automation.springwebshop.service;

import com.volare_automation.springwebshop.model.CartProduct;
import com.volare_automation.springwebshop.model.Products;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public interface ProductServiceInterface {

    public String getImageService() throws IOException, SQLException;
    public void storeImageService();
    public List<Products> getAllProducts();
    public void postCartProduct(HttpServletRequest request, CartProduct cp);
    public List<Integer> getProductId();
    public List<CartProduct> deleteCartId(CartProduct cp, String idString);
}
