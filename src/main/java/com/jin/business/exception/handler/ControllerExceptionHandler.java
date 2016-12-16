package com.jin.business.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.jin.business.exception.ResourceAccessException;

@RestControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(value = {ResourceAccessException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ErrorResource handleConflict(Exception ex, WebRequest request) { 
		return handleResponse(ex, request, "401", "CustomError", "Wrong Source of the user");
    }
	
	@ExceptionHandler(value = {AccessDeniedException.class, BadCredentialsException.class, AuthenticationException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ErrorResource handleNoAuth(Exception ex, WebRequest request) { 
		return handleResponse(ex, request, "401", "Unauthorized", "User are not authorized to access");
    }
		
	@ExceptionHandler(value = {Exception.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorResource handleAllException(Exception ex, WebRequest request) { 
        return handleResponse(ex, request, "500", "Internal Server Error", "Unexpected internal error");
    }
	
	private ErrorResource handleResponse(Exception ex, WebRequest request, String status, String error, String message){
		ErrorResource errorResource = new ErrorResource(status, error, message);
		return errorResource;
	}
	
}
