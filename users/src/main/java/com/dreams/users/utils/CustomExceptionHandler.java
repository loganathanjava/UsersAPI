package com.dreams.users.utils;

import java.util.ArrayList;
import java.util.List;

import javax.naming.ServiceUnavailableException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.dreams.users.model.Response;


@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler{

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
																  HttpHeaders headers,
																  HttpStatus status,
																  WebRequest request) 
    {
	    List<String> errors = new ArrayList<String>();
	    for (FieldError error : ex.getBindingResult().getFieldErrors()) 
	    {
	        errors.add(error.getField() + ": " + error.getDefaultMessage());
	    }
	    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) 
	    {
	        errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
	    }
	     
	    ServerException apiError = new ServerException(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
	    return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
	}
	
	@ExceptionHandler(ServiceUnavailableException.class)
	public ResponseEntity<Response> serviceUnavailableEx(ServiceUnavailableException serviceUnavailableEx) {
		Response toRet = new Response();
		toRet.setResponseCode(0);
		toRet.setResponseMessage("Requested Service Unavailable!");
		return new ResponseEntity<Response>(toRet,HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@ExceptionHandler(javax.naming.AuthenticationException.class)
	public ResponseEntity<Response> authenticationEx(javax.naming.AuthenticationException serviceUnavailableEx) {
		Response toRet = new Response();
		toRet.setResponseCode(0);
		toRet.setResponseMessage("User is not authenticated!");
		return new ResponseEntity<Response>(toRet,HttpStatus.FORBIDDEN);
	}
}
