package com.thiagobernardo.cursomc.services.exceptions;

public class FileException extends RuntimeException {

	private static final long serialVersionUID = 9008983523477272550L;

	public FileException(String msg) {
		super(msg);
	}
	
	public FileException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
