package com.here.framework.service.heart;

public class HeartBeat implements IHeartBeat {

	@Override
	public SeedInfo heartBeat() {
		SeedInfo seedInfo=new SeedInfo();
		seedInfo.setAlive(true);
		return seedInfo;
	}

}
