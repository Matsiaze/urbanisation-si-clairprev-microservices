package com.urbanisation_si.microservices_assure.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AssureIntrouvableException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8184836551138583157L;

	public AssureIntrouvableException(String msg) {
		super(msg);
		
	}

}
