package com.currencyexchange.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DataNotFoundException(String errorCode, String message)
	{
		super(errorCode+":"+message);
	}
}
