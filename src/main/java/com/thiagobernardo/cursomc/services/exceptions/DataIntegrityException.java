package com.thiagobernardo.cursomc.services.exceptions;

public class DataIntegrityException extends RuntimeException {

	private static final long serialVersionUID = 6683813502562562555L;
	
	public DataIntegrityException(String msg) {
		super(msg);
	}
	
	public DataIntegrityException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
