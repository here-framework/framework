package com.here.framework.init;
/**
 * bean 创建监听器接口
 * @author koujp
 *
 */
public interface BeanInitListener {
	public boolean isEnable(Object bean,Object beanName);
	public Object beforeBeanCreate(Object bean,Object beanName);
	public Object afterBeanCreate(Object bean,Object beanName);
}
