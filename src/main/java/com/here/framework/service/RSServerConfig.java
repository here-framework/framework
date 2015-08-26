package com.here.framework.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.cxf.phase.PhaseInterceptor;

import com.here.framework.bean.InstanceManager;
/**
 * rest 服务发布配置
 * @author koujp
 *
 */
@SuppressWarnings("rawtypes")
public class RSServerConfig{
	protected  List<Object> restServices=Collections.synchronizedList(new ArrayList<Object>());
	protected  List<PhaseInterceptor> restInInterceptors=Collections.synchronizedList(new ArrayList<PhaseInterceptor>());
	protected  List<PhaseInterceptor> restOutInterceptors=Collections.synchronizedList(new ArrayList<PhaseInterceptor>());
	
	private RSServerConfig(){
		
	}
	public static RSServerConfig getInstance(){
		return InstanceManager.getInstance(RSServerConfig.class, true);
	}
	/**
	 * 线程安全方法
	 * @param name
	 * @return
	 */
	public static synchronized RSServerConfig getInstanceSync(String name){
		return InstanceManager.getInstance(name,RSServerConfig.class, true);
	}
	/**
	 * 非线程安全方法
	 * @param name
	 * @return
	 */
	public static RSServerConfig getInstance(String name){
		return InstanceManager.getInstance(name,RSServerConfig.class, true);
	}
	public void setServiceBean(Object service){
		
		restServices.add(service);
	}
	public void addServiceBean(Object service){
		restServices.add(service);
	}
	public void setServiceBeans(List<Object> services){
		restServices.addAll(services);
	}
	public void setInInterceptor(PhaseInterceptor inInterceptor){
		restInInterceptors.add(inInterceptor);
	}
	public void setInInterceptors(List<PhaseInterceptor> inInterceptors){
		restInInterceptors.addAll(inInterceptors);
	}
	public void setOutInterceptor(PhaseInterceptor outInterceptor){
		restOutInterceptors.add(outInterceptor);
	}
	public void setOutInterceptors(List<PhaseInterceptor> outInterceptors){
		restOutInterceptors.addAll(outInterceptors);
	}
	public Object[] getServiceBeans(){
		return restServices.toArray(new Object[0]);
	}
	public PhaseInterceptor[] getInInterceptors(){
		if(null==restInInterceptors || restInInterceptors.size()==0){
			return null;
		}
		return restInInterceptors.toArray(new PhaseInterceptor[0]);
	}
	public PhaseInterceptor[] getOutInterceptors(){
		if(null==restOutInterceptors || restOutInterceptors.size()==0){
			return null;
		}
		return restOutInterceptors.toArray(new PhaseInterceptor[0]);
	}
}
