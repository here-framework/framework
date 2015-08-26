package com.here.framework.service.heart;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
/**
 * 检测节点情况
 * @author koujp
 *
 */
@Path("/")
public interface IHeartBeat {
	@GET
	@Path("/heart")
	public SeedInfo heartBeat();
}
