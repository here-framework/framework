package com.here.framework.service;

import com.here.framework.BaseException;

public class ServiceException extends BaseException {
	private static final long serialVersionUID = 1L;

	public ServiceException(String msg, Throwable t) {
		super(msg, t);
	}
	
	public ServiceException(String msg) {
		super(msg);
	}
	
	public ServiceException(Throwable t) {
		super(t);
	}
}
