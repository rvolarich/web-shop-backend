package com.volare_automation.springwebshop;


import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;




import java.sql.SQLException;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SpringWebShopApplication{



	public static void main(String[] args) throws SQLException {
		SpringApplication.run(SpringWebShopApplication.class, args);




	}

}
