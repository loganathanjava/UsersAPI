package com.dreams.users.utils;

public class AuthenticationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3411078331253698063L;
	
	String errMsg;
	AuthenticationException(String errMsg) {
		super(errMsg);
	}
}
