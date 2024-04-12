package com.dorbello.dorbellomain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.dorbello.database_services.DatabaseOperations;

@SpringBootApplication
public class DorbelloMainApplication {
	public static void main(String[] args) {
		SpringApplication.run(DorbelloMainApplication.class, args);
		DatabaseOperations operations = new DatabaseOperations();
		System.out.println(operations.receiveClientToServer("test"));
	}
}