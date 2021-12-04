package com.chairmo.eshop.rest.exceptions;

public class InvalidExceptionError extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidExceptionError() {
		super();
	}
	public InvalidExceptionError(String message, Throwable cause) {
		super(message, cause);
	}
	public InvalidExceptionError(Throwable cause) {
		super(cause);
	}
	public InvalidExceptionError(String message) {
		super(message);
	}

}
