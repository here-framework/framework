package com.here.framework.init;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
/**
 * rest  bean处理器
 * @author koujp
 *
 */
public class BeanInitProcessor implements BeanPostProcessor {
	private List<BeanInitListener> listeners=new ArrayList<BeanInitListener>();
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		for(BeanInitListener listener:listeners){
			if(!listener.isEnable(bean, beanName)){
				continue;
			}
			bean=listener.afterBeanCreate(bean, beanName);
		}
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		for(BeanInitListener listener:listeners){
			if(!listener.isEnable(bean, beanName)){
				continue;
			}
			bean=listener.beforeBeanCreate(bean, beanName);
		}
		return bean;
	}
	public void setListener(BeanInitListener listener){
		this.listeners.add(listener);
	}
	public void setListeners(List<BeanInitListener> listeners) {
		this.listeners = listeners;
	}

}
