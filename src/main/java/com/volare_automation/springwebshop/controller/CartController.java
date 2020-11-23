package com.volare_automation.springwebshop.controller;
//import com.volare_automation.springwebshop.model.CartProductTest;

import com.volare_automation.springwebshop.model.CartProduct;
import com.volare_automation.springwebshop.model.Products;
import com.volare_automation.springwebshop.repository.ProductRepositoryInterface;
import com.volare_automation.springwebshop.service.ProductServiceInterface;
import com.volare_automation.springwebshop.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class CartController {

    @Autowired
    ProductServiceInterface productServiceInterface;

    @Autowired
    ProductRepositoryInterface productRepositoryInterface;


//    @RequestMapping(value = "/pcp", method = RequestMethod.POST)
//    public void postCartProduct(@RequestBody CartProductTest s){
//        userServiceInterface.postCartProduct(s);
//    }

    @RequestMapping(value = "/pcp", method = RequestMethod.POST)
    public void postCartProduct(@RequestBody CartProduct cp){
        productServiceInterface.postCartProduct(cp);

    }

    @RequestMapping(value = "/getid", method = RequestMethod.GET)
    public List<Integer> getProductId(){
        return productRepositoryInterface.getProductId();

    }

    @RequestMapping(value = "/getcart", method = RequestMethod.GET)
    public List<CartProduct> getCartProducts(){
        return productRepositoryInterface.getCartProducts();

    }


}
