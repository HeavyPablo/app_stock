package com.restaurante.app_stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.restaurante.app_stock")
public class AppStockApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppStockApplication.class, args);
	}

}
