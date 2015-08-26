package com.here.framework.service;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
/**
 * spring上下文完成监听
 * @author koujp
 *
 */
public class SpringCtxRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(null==event.getApplicationContext().getParent()){
			
		}
	}

}
