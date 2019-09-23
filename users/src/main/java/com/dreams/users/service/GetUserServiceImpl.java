package com.dreams.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreams.users.entities.UsersEntity;
import com.dreams.users.repository.UserRepository;

@Service
public class GetUserServiceImpl {

	@Autowired private UserRepository userRepo;
	
	public UsersEntity getUser(String email) {
		UsersEntity user = null;
		try {
			user = userRepo.findByEmail(email);
		} catch(Exception e) {
			
		}
		return user;
	} 
}
