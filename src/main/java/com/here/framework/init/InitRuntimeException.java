package com.here.framework.init;

import com.here.framework.BaseRuntimeException;

public class InitRuntimeException extends BaseRuntimeException {
	
	private static final long serialVersionUID = -604224179948200761L;

	public InitRuntimeException() {
	}

	public InitRuntimeException(String message) {
		super(message);
	}

	public InitRuntimeException(Throwable cause) {
		super(cause);
	}

	public InitRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public InitRuntimeException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
