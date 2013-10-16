package com.gamephone.acs.exception;

public class AcsException extends Exception {

	private static final long serialVersionUID = -9149099761296783769L;
	
	public AcsException(){
        super();
    }
	
	public AcsException(String msg){
	    super(msg);
	}

	public AcsException(String msg, Throwable e){
	    super(msg, e);
	}
	
	public AcsException(Throwable e){
        super(e);
    }
}
