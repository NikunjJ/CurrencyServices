package com.common.domain.framework;

import java.util.List;

public class ApiResponse {

	private Object data;
	
	private List<ApiError> errors;
	
	private boolean success;

	public ApiResponse(Object data, List<ApiError> errors) {
		
		super();
		
		this.data = data;
		this.errors = errors;
		
		if(this.errors!=null && this.errors.isEmpty())
		{
			this.success = true;
		}
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public List<ApiError> getErrors() {
		return errors;
	}

	public void setErrors(List<ApiError> errors) {
		this.errors = errors;
	}

	public boolean isSuccess() {
		return success;
	}
	
}
