package com.dreams.users.service;

import javax.naming.AuthenticationException;
import javax.naming.ServiceUnavailableException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreams.users.entities.UsersEntity;
import com.dreams.users.repository.UserRepository;
import com.dreams.utils.utils.Authenticate;
import com.dreams.utils.utils.SessionUtils;

@Service
public class GetUserServiceImpl {

	@Autowired private UserRepository userRepo;
	
	public UsersEntity getUser(HttpServletRequest request) throws AuthenticationException {
		UsersEntity user = null;
		boolean auth = Authenticate.authenticateUser(request);
		try {
			if(auth) {
				user = userRepo.findByEmail(request.getAttribute("userId").toString());				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	
	public UsersEntity getUserByEmail(HttpServletRequest request, String email) throws AuthenticationException {
		UsersEntity user = null;
		try {
			user = userRepo.findByEmail(email);				
		} catch(Exception e) {
			e.printStackTrace();
		}
		return user;
	}
}
