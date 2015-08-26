package com.here.framework.init;

import com.here.framework.BaseException;

public class InitException extends BaseException {
	private static final long serialVersionUID = 3590322192319132812L;

	public InitException() {
	}

	public InitException(String message) {
		super(message);
	}

	public InitException(Throwable cause) {
		super(cause);
	}

	public InitException(String message, Throwable cause) {
		super(message, cause);
	}

	public InitException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
