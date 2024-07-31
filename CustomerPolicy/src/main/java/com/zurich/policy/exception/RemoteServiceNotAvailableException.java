package com.zurich.policy.exception;

public class RemoteServiceNotAvailableException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public RemoteServiceNotAvailableException(String msg) {
		super(msg);
	}
}
