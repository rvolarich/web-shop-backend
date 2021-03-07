package com.volare_automation.springwebshop.service;

import com.volare_automation.springwebshop.model.CartProduct;
import com.volare_automation.springwebshop.model.Products;
import com.volare_automation.springwebshop.repository.ProductRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
public class ProductService implements ProductServiceInterface {

    @Autowired
    ProductRepositoryInterface productRepositoryInterface;

    @Autowired
    UserServiceInterface userServiceInterface;


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

    @Override
    public void postCartProduct(HttpServletRequest request, CartProduct cp) {

        System.out.println("ime produkta: " + cp.getProductName());
        cp.setProductQuantity(1);
        boolean b = true;
        String id = userServiceInterface.getUserIdFromCookie(request).toString();
        System.out.println("Idddddddd " + id);
        productRepositoryInterface.postCartProduct(cp, b, id);

    }

    @Override
    public List<Integer> getProductId() {
        return null;
    }

    @Override
    public List<CartProduct> deleteCartId(CartProduct cp, String idString) {
        Integer id = cp.getProductId();
        return productRepositoryInterface.deleteCartById(id, idString);
    }

    @Override
    public boolean updateProducts(CartProduct cp) {


        double doublePrice = Double.parseDouble(cp.getProductPriceString());
        double doublePriceRounded = Math.round(doublePrice * 100.0) /100.0;
        boolean sRet = productRepositoryInterface.updateProducts(doublePriceRounded,
                cp.getProductQuantity(), cp.getProductId());
        if(sRet){
            return true;
        }
        return false;
    }
}
