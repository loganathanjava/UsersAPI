package com.dreams.users.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreams.users.constants.Constants;
import com.dreams.users.entities.UsersEntity;
import com.dreams.users.model.Response;
import com.dreams.users.repository.UserRepository;
import com.dreams.users.utils.Utilities;
import com.dreams.utils.utils.Crypto;
import com.dreams.utils.utils.SessionUtils;

@Service
public class LoginServiceImpl {
	static Logger logger = Logger.getLogger(LoginServiceImpl.class);
	@Autowired private UserRepository userRepository;
	
	public Response login(HttpServletRequest request, HttpServletResponse response, String username, String password) {
		//logger.info("Enter:: " + this.getClass().getName() + " " + this.getClass().getEnclosingMethod().getName());
		
		Response toRet = new Response();
		try
		{
			UsersEntity user = userRepository.findByEmail(username);
			if(user != null && !user.getPassword().equals("") && user.getPassword().equals(password))
			{
				generateUserCk(response, username);
				toRet.setResponseCode(Constants.LOGIN_SUCCESS);
				toRet.setResponseMessage(Constants.LOGIN_SUCCESS_MSG);
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
		//logger.info("Exit :: " + this.getClass().getName() + " " + this.getClass().getEnclosingMethod().getName());
		return toRet;
	} 
	
	
	public void generateUserCk(HttpServletResponse response, String value) {
		logger.info("Inside generateUserCk");
		Date dNow = new Date(System.currentTimeMillis()+5*60*1000);
		String val = value + "/" + dNow.getTime();
		SessionUtils.setCookie(response, "dtCk", Crypto.encrypt(val));			
	}
}
