package com.here.framework.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.phase.PhaseInterceptor;
/**
 * rest服务注册类
 * @author koujp
 *
 */
@SuppressWarnings("rawtypes")
public class ServiceRegister {
	private RSServerConfig rsConfig = null;
	private Map<Class<?>,Object> rsBeanCache=new HashMap<Class<?>, Object>();
	
	public ServiceRegister(){
		rsConfig = RSServerConfig.getInstance();
	}
	public ServiceRegister(String configName){
		RSServerConfig.getInstanceSync(configName);
	}
	public void setServiceBean(Object service){
		rsConfig.setServiceBean(service);
	}
	public void addServiceBean(Object service){
		rsConfig.addServiceBean(service);
	}
	public void setServiceBeans(List<Object> services){
		rsConfig.addServiceBean(services);
	}
	public void setInInterceptor( PhaseInterceptor inInterceptor){
		rsConfig.setInInterceptor(inInterceptor);
	}
	public void setInInterceptors(List<PhaseInterceptor> inInterceptors){
		rsConfig.setInInterceptors(inInterceptors);
	}
	public void setOutInterceptor(PhaseInterceptor outInterceptor){
		rsConfig.setOutInterceptor(outInterceptor);
	}
	public void setOutInterceptors(List<PhaseInterceptor> outInterceptors){
		rsConfig.setOutInterceptors(outInterceptors);
	}
	public Object[] getServiceBeans(){
		return rsConfig.getServiceBeans();
	}
	public PhaseInterceptor[] getInInterceptors(){
		
		return rsConfig.getInInterceptors();
	}
	public PhaseInterceptor[] getOutInterceptors(){
		return rsConfig.getOutInterceptors();
	}
	@SuppressWarnings("unchecked")
	public synchronized <T> T getLocaLService(Class<T> clazz){
		Object bean=rsBeanCache.get(clazz);
		Object[] objs=rsConfig.getServiceBeans();
		if(null==bean && null!=objs){
			for(Object obj:objs){
				if(clazz.isInstance(obj)){
					bean=obj;
					rsBeanCache.put(clazz, bean);
				}
			}
		}
		if(null!=bean){
			return (T)bean;
		}
		return null;
	}
}
