package com.jin.business.exception;

import com.jin.business.exception.APIException;

public class ResourceAccessException extends APIException {

	private static final long serialVersionUID = 2002177481583537530L;

	public ResourceAccessException(String message) {
		super(message);
	}

}
