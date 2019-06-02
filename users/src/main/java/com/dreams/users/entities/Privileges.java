package com.dreams.users.entities;

import java.io.Serializable;

public class Privileges implements Serializable{

	/**
	 * Constant Serial id
	 */
	private static final long serialVersionUID = 1L;
	
	private String privilegeId;
	
	private String privilegeName;

	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}
	
	
	
}
