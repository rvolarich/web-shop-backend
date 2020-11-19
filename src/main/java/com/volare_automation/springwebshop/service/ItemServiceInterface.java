package com.volare_automation.springwebshop.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public interface ItemServiceInterface {

    public String getImageService() throws IOException, SQLException;
    public void storeImageService();
}
