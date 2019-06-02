package com.dreams.users.model;

import java.io.Serializable;
import java.util.List;

import com.dreams.users.entities.Privileges;

public class UserRoles implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7203970627189711189L;

	private String userId;
	
	private List<Privileges> privileges;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Privileges> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(List<Privileges> privileges) {
		this.privileges = privileges;
	}
	
	
	
}
