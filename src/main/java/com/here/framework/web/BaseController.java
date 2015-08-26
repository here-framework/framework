package com.here.framework.web;

import com.here.framework.bean.InstanceManager;
import com.here.framework.service.client.ServiceProxy;
import com.here.framework.service.client.ServiceProxyImpl;

/**
 * 基础请求控制器
 * @author koujp
 *
 */
public class BaseController {
	private ServiceProxy serviceProxy;
	
	/**
	 * 根据类获得服务
	 * @param interfaceClazz
	 * @return
	 */
	public <T> T getService(Class<T> interfaceClazz){
		if(null == serviceProxy){
			initServiceProxy();
		}
		return serviceProxy.lookup(interfaceClazz);
	}
	private void initServiceProxy(){
		serviceProxy = InstanceManager.getInstance(ServiceProxyImpl.class);
	}
}
