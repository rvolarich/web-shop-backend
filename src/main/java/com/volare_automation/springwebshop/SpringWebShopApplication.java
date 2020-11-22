package com.volare_automation.springwebshop;

import com.volare_automation.springwebshop.repository.ProductRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class SpringWebShopApplication{

//	@Autowired
//	JdbcTemplate jdbcTemplate;

	public static void main(String[] args) throws SQLException {
		SpringApplication.run(SpringWebShopApplication.class, args);
		new ProductRepository();



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
