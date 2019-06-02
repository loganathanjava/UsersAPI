package com.dreams.users.utils;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ServerException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -416834259372452981L;
	private HttpStatus status;
	private String message;
	private List<String> error;
	
	public ServerException(HttpStatus status, String message, List<String> error) {
		super();
		this.status = status;
		this.message = message;
		this.error = error;
	}
	
	public ServerException(HttpStatus status, String message, String error) {
		super();
		this.status = status;
		this.message = message;
		this.error = Arrays.asList(error);
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getError() {
		return error;
	}

	public void setError(List<String> error) {
		this.error = error;
	}
	
	
 }
