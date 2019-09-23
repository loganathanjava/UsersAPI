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
	
	public Response createUser(UsersEntity user) {
		
		Response toRet = new Response();
		try
		{	
			
			UsersEntity entity = userRepository.save(user);
			
			
			if(entity != null)
			{
				toRet.setResponseCode(Constants.USER_CREATE_SUCCESS_MSG);
				toRet.setResponseMessage(Constants.USER_CREATED_SUCCESS_MSG);
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
