package com.volare_automation.springwebshop.controller;

import com.volare_automation.springwebshop.model.CartItems;
import com.volare_automation.springwebshop.service.ItemServiceInterface;
import com.volare_automation.springwebshop.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class ItemController {

    @Autowired
    private ItemServiceInterface itemServiceInterface;

    @RequestMapping(value = "/photo", method = RequestMethod.GET)
    public String getPhoto() throws SQLException, IOException {
        String s = itemServiceInterface.getImageService();
        return s;
    }

    @RequestMapping(value = "/postitem", method = RequestMethod.POST)
    public void storeItem() throws SQLException {
        itemServiceInterface.storeImageService();
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadImage(@RequestParam(required = true, value = "file")MultipartFile file,
                                              @RequestParam(required = true, value = "jsondata")String jsondata)  {
        return null;
    }

}
