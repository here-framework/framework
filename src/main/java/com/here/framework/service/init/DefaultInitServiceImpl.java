package com.here.framework.service.init;
/**
 * 必须有一个默认的service
 * @author koujp
 *
 */
public class DefaultInitServiceImpl implements DefaultInitService{

	public DefaultInitServiceImpl(){
		System.out.println("defaultInitService inited!");
	}
	@Override
	public void inited() {
		
	}

}
