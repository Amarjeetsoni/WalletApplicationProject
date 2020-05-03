package com.cg.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler{
   
	@ExceptionHandler(LoginCredentialsException.class)
	public final ResponseEntity<Object> checkLoginCredentials(Exception exception){
	return new ResponseEntity<Object>(new ErrorMessage(HttpStatus.UNAUTHORIZED, exception.getMessage()), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(CheckDetailsException.class)
	public final ResponseEntity<Object> checkAddAccountDetails(Exception exception){
		return new ResponseEntity<Object>(new ErrorMessage(HttpStatus.IM_USED, exception.getMessage()), HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<Object> somethingWentWrong(Exception exception){
		return new ResponseEntity<Object>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> internalServerError(Exception exception){
		return new ResponseEntity<Object>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
}

class ErrorMessage{
	private HttpStatus message;
	private String details;
	
	public ErrorMessage(HttpStatus message, String details) {
		super();
		this.message = message;
		this.details = details;
	}

	public HttpStatus getMessage() {
		return message;
	}

	public void setMessage(HttpStatus message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	
}
