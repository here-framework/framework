package com.here.framework.service;

import java.util.Locale;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvokeFaultExceptionMapper<T extends Throwable> implements
		ExceptionMapper<T> {

	public Response toResponse(Throwable ex) {
		StackTraceElement[] trace = new StackTraceElement[1];
		trace[0] = ex.getStackTrace()[0];
		ex.setStackTrace(trace);
		ResponseBuilder rb = Response
				.status(Response.Status.INTERNAL_SERVER_ERROR);
		rb.type("application/json;charset=UTF-8");
		rb.header("exception", ex.getClass().getName());
		rb.entity(ex);
		rb.language(Locale.SIMPLIFIED_CHINESE);
		Response r = rb.build();
		return r;
	}

}