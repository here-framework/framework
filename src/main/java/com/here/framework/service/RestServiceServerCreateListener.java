package com.here.framework.service;

import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.jaxrs.spring.JAXRSServerFactoryBeanDefinitionParser.SpringJAXRSServerFactoryBean;
import org.apache.cxf.phase.PhaseInterceptor;

import com.here.framework.init.BeanInitListener;
/**
 * rest服务对象代理创建 监听器
 * @author koujp
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class RestServiceServerCreateListener implements BeanInitListener {

	private String beanName;
	private RSServerConfig reServerConfig=null;
	
	private String restServerConfigName;
	@Override
	public boolean isEnable(Object bean, Object beanName) {
		return beanName.equals(this.beanName);
	}
	@Override
	public Object beforeBeanCreate(Object bean, Object beanName) {
		if(bean instanceof SpringJAXRSServerFactoryBean){
			
			if(null == restServerConfigName){
				reServerConfig = RSServerConfig.getInstance();
			}else{
				reServerConfig = RSServerConfig.getInstanceSync(restServerConfigName);
			}
			
			SpringJAXRSServerFactoryBean serverBean=(SpringJAXRSServerFactoryBean)bean;
			Object[] serviceBeans=reServerConfig.getServiceBeans();
			if(null!=serviceBeans && serviceBeans.length>0){
				serverBean.setServiceBeanObjects(serviceBeans);
			}
			PhaseInterceptor[] inInterceptors=reServerConfig.getInInterceptors();
			if(null!=inInterceptors){
				for(Interceptor interceptor:inInterceptors){
					serverBean.getInInterceptors().add(interceptor);
				}
			}
			PhaseInterceptor[] outInterceptors=reServerConfig.getInInterceptors();
			if(null!=outInterceptors){
				for(Interceptor interceptor:outInterceptors){
					serverBean.getInInterceptors().add(interceptor);
				}
			}
			
		}
		return bean;
	}

	@Override
	public Object afterBeanCreate(Object bean, Object beanName) {	
		return bean;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public String getRestServerConfigName() {
		return restServerConfigName;
	}
	public void setRestServerConfigName(String restServerConfigName) {
		this.restServerConfigName = restServerConfigName;
	}

}
