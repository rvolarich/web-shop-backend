package com.volare_automation.springwebshop;

import com.volare_automation.springwebshop.model.Item;
import com.volare_automation.springwebshop.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.sql.SQLOutput;

@SpringBootApplication
public class SpringWebShopApplication{

//	@Autowired
//	JdbcTemplate jdbcTemplate;

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(SpringWebShopApplication.class, args);
		new ItemRepository();



	}

//	@Override
//	public void run(String... args) throws Exception {
//
//		String sql = "INSERT INTO customers (firstname, surname) VALUES ( ?, ?)";
//		int result = jdbcTemplate.update(sql, "Bilbo", "Baggins");
//
//		if (result > 0) {
//			System.out.println("Insert successfully.");
//		}
//		else if (result==0) {
//			System.out.println("Unsuccessfull insert");
//		}
//	}

}
