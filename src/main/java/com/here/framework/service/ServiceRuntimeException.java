package com.here.framework.service;

import com.here.framework.BaseRuntimeException;

public class ServiceRuntimeException extends BaseRuntimeException {
	private static final long serialVersionUID = 1L;

	public ServiceRuntimeException(String msg, Throwable t) {
		super(msg, t);
	}
	
	public ServiceRuntimeException(String msg) {
		super(msg);
	}
	
	public ServiceRuntimeException(Throwable t) {
		super(t);
	}
}
