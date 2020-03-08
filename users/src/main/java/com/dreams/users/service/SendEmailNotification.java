package com.dreams.users.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.dreams.users.entities.UsersEntity;
import com.dreams.users.model.EmailInfo;
import com.dreams.users.model.Response;

@Component
public class SendEmailNotification {

	
	@Autowired
	RestTemplate restTemplate;
	
	public boolean sendWelcomeMail(UsersEntity user) {
	
		String url = "http://marketing/api/v1/sendEmail"; 
		EmailInfo emailinfo = new EmailInfo();
		emailinfo.setFilename("AccountCreation.vm");
		emailinfo.setSubject("Account Created for SolutionsAdept!");
		
		Map<String,String> item = new HashMap<>();
		item.put("image", "https://pbs.twimg.com/profile_images/549710166762213376/7kvALvDC.jpeg");
		item.put("firstname", user.getFirstname());
		item.put("lastname", user.getLastname());
		item.put("email", user.getEmail());
		item.put("url", "https://www.google.co.in/?gws_rd=ssl");
		List<Map> list = new ArrayList<>();
		list.add(item);
		emailinfo.setModel(list);
		ResponseEntity<Response> toRet = restTemplate.postForEntity(url, emailinfo, Response.class);
		
		if(toRet != null && toRet.getBody() != null && toRet.getBody().getResponseMessage() != null && toRet.getBody().getResponseMessage().equalsIgnoreCase("success")) {
			return true;
		}
		return false;
	}
}
