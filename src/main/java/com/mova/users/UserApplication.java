package com.mova.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserApplication {

	public static void main(String[] args) {
        System.out.println("hola bb");
		SpringApplication.run(UserApplication.class, args);
        System.out.println("chao bb");
	}

}
