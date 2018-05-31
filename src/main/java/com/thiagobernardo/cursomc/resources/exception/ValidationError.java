package com.thiagobernardo.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

	private static final long serialVersionUID = -3646100490728576109L;
	
	private List<FieldMessage> errors = new ArrayList<>();
	
	public ValidationError(Long timestamp, Integer status, String error, String message, String path) {
		super(timestamp, status, error, message, path);
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void setFieldMessages(List<FieldMessage> fieldMessages) {
		this.errors = fieldMessages;
	}
	
	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage(fieldName, message));
	}
}
