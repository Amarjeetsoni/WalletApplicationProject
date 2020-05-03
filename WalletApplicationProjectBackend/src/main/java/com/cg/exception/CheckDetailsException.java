package com.cg.exception;

public class CheckDetailsException extends Exception{
	
	private static final long serialVersionUID = 1L;
    public CheckDetailsException(){
      super();	
    }
    public CheckDetailsException(String message){
    	super(message);
    }
	
}
