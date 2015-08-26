package com.here.framework.service.init;

import javax.ws.rs.Path;

@Path("/")
public interface DefaultInitService {
	@Path("/framework/defaultInit")
	public void inited();
}
