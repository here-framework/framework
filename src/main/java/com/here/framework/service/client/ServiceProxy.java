package com.here.framework.service.client;

import com.here.framework.service.config.ServiceConfig;
/**
 * 服务代理
 * @author koujp
 *
 */
public interface ServiceProxy {
	

	public  <T> T lookup(Class<T> clazz);

	public  <T> T lookup(Class<T> clazz, String address);

	public  <T> T getBean(Class<T> clazz);

	public  ServiceConfig getServiceConfig();

	public  void addHeader(String name, String value);

}