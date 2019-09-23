package com.dreams.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
//@EnableEurekaClient
public class UserApplication {
	
	public static void main(String arg[])
	{
		SpringApplication.run(UserApplication.class, arg);
	}
}
