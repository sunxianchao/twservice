package com.gamephone.billing.exception;

public class BillingException extends Exception {

	private static final long serialVersionUID = -9149099761296783769L;
	
	public BillingException(){
        super();
    }
	
	public BillingException(String msg){
	    super(msg);
	}

	public BillingException(String msg, Throwable e){
	    super(msg, e);
	}
	
	public BillingException(Throwable e){
        super(e);
    }
}
