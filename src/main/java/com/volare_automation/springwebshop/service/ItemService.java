package com.volare_automation.springwebshop.service;

import com.volare_automation.springwebshop.repository.ItemRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.sql.SQLException;

@Service
public class ItemService implements ItemServiceInterface {

    @Autowired
    ItemRepositoryInterface itemRepositoryInterface;

    @Override
    public String getImageService() throws IOException, SQLException {
        byte[] b = itemRepositoryInterface.getImage();
        String base64Encoded = DatatypeConverter.printBase64Binary(b);
        return base64Encoded;
    }

    @Override
    public void storeImageService(){
        itemRepositoryInterface.storeImage();
    }
}
