package com.dreams.users.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dreams.users.entities.SecurityQuestions;
import com.dreams.users.entities.UsersEntity;
import com.dreams.users.model.Response;
import com.dreams.users.repository.UserRepository;


@Service
public class ValidateServiceImpl {

	@Autowired UserRepository userRepo;
	
	public Response validateSecurityQuestion(String question, String answer, String email) {

		Response toRet = new Response();
		boolean isValid = false;
		try {
			UsersEntity user = userRepo.findByEmail(email);
			if(user != null) {
				List<SecurityQuestions> questions = user.getSecurityQuestions();
				for(SecurityQuestions que : questions) {
					if(que.getQuestion().equalsIgnoreCase(question) && que.getAnswer().equalsIgnoreCase(answer)) {
						isValid = true;
					}
				}
				
				if(isValid) {
					toRet.setResponseCode(0);
					toRet.setResponseMessage("Security question and answer is corret");
				} else {
					toRet.setResponseCode(1);
					toRet.setResponseMessage("Security question and answer is wrong");
				}
			} else {
				toRet.setResponseCode(2);
				toRet.setResponseMessage("User does not exist!");
			}
		} catch(Exception e) {
			toRet.setResponseCode(2);
			toRet.setResponseMessage("Something went wrong!!!");
		}
		
		return toRet;
	} 
}
