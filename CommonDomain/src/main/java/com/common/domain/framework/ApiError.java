package com.common.domain.framework;

import java.util.Date;

public class ApiError {

	private String errorCode;
	
	private boolean technicalError;
	
	private String errorMessage;
	
	private Date raiseDateTime;

	public ApiError()
	{
		raiseDateTime = new Date();
	}
	
	public ApiError(String errorCode, boolean technicalError, String errorMessage) {
		
		super();
		
		this.errorCode = errorCode;
		this.technicalError = technicalError;
		this.errorMessage = errorMessage;
		
		raiseDateTime = new Date();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public boolean isTechnicalError() {
		return technicalError;
	}

	public void setTechnicalError(boolean technicalError) {
		this.technicalError = technicalError;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Date getRaiseDateTime() {
		return raiseDateTime;
	}

	@Override
	public String toString() {
		return String.format("ApiError [errorCode=%s, technicalError=%s, errorMessage=%s, raiseDateTime=%s]", errorCode,
				technicalError, errorMessage, raiseDateTime);
	}
	
}
