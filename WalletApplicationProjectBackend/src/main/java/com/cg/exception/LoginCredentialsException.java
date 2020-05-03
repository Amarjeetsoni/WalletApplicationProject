package com.cg.exception;

public class LoginCredentialsException  extends Exception{   
	private static final long serialVersionUID = 1L;

public LoginCredentialsException() {
	  super();
  }
  
  public LoginCredentialsException(String message) {
	  super(message);
  }
}
