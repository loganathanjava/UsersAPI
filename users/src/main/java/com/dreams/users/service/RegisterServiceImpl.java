package com.dreams.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.dreams.users.constants.Constants;
import com.dreams.users.entities.UsersEntity;
import com.dreams.users.model.Response;
import com.dreams.users.repository.UserRepository;

@Service
public class RegisterServiceImpl {

	@Autowired UserRepository userRepository;
	@Autowired SendEmailNotification sendEmailNotification;
	
	public Response createUser(UsersEntity user) {
		
		Response toRet = new Response();
		try
		{	
			
			UsersEntity entity = userRepository.save(user);
			
			
			if(entity != null)
			{
				
				if(!sendEmailNotification.sendWelcomeMail(user)) {

					toRet.setResponseCode(Constants.USER_CREATE_SUCCESS_Email_Failed_MSG);
					toRet.setResponseMessage(Constants.USER_CREATED_SUCCESS_email_failed_MSG);
				} else {					
					toRet.setResponseCode(Constants.USER_CREATE_SUCCESS_MSG);
					toRet.setResponseMessage(Constants.USER_CREATED_SUCCESS_MSG);
				}
			}
			else
			{
				toRet.setResponseCode(Constants.USER_CREATION_FAILED);
				toRet.setResponseMessage(Constants.USER_CREATION_FAILED_MSG);
			}
		}
		catch(DataIntegrityViolationException ex)
		{
			toRet.setResponseCode(Constants.USER_ALREADY_EXIST);
			toRet.setResponseMessage(Constants.USER_ALREADY_EXIST_MSG);
		}
		
 		return toRet;
	}
}
