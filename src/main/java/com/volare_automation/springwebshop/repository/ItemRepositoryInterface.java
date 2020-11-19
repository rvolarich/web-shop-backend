package com.volare_automation.springwebshop.repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

public interface ItemRepositoryInterface {

    public byte [] getImage() throws SQLException, IOException;
    public void storeImage();
}
