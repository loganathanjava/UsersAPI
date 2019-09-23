package com.dreams.users.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreams.users.constants.Constants;
import com.dreams.users.entities.UsersEntity;
import com.dreams.users.model.Response;
import com.dreams.users.repository.UserRepository;
import com.dreams.users.utils.Utilities;

@Service
public class LoginServiceImpl {
	
	@Autowired private UserRepository userRepository;
	
	public Response login(HttpServletRequest request, HttpServletResponse response, String username, String password) {
		
		Response toRet = new Response();
		try
		{
			UsersEntity user = userRepository.findByEmail(username);
			if(user != null && !user.getPassword().equals("") && user.getPassword().equals(password))
			{
				toRet.setResponseCode(Constants.LOGIN_SUCCESS);
				toRet.setResponseMessage(Constants.LOGIN_SUCCESS_MSG);
				Utilities.setCookie(response, "dAuth", username);		
			}
			else
			{
				toRet.setResponseCode(Constants.INVALID_CREDENTIALS);
				toRet.setResponseMessage(Constants.INVALID_CREDENTIALS_MSG);
			}
		}
		catch(Exception e)
		{
			toRet.setResponseCode(Constants.SOMETHING_WENT_WRONG);
			toRet.setResponseMessage(Constants.SOMETHING_WENT_WRONG_MSG);
		}
		return toRet;
	} 
}
