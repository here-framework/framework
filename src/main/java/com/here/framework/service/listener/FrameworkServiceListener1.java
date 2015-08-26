package com.here.framework.service.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRegistration;

import org.apache.cxf.transport.servlet.CXFServlet;

import com.here.framework.init.listener.AbsFrameworkListener;

/**
 * 配置服务的监听器
 * @author koujp
 *
 */
public class FrameworkServiceListener1 extends AbsFrameworkListener {
	public static final String WEB_URL_PATTERRN="service-url-pattern";
	private static final String contextConfigLocation="classpath:framework/config/service/framework-service.xml";
	protected String defaultUrlPattern="/service/*";
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {	
	}
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		servletContext=servletContextEvent.getServletContext();
		String initUrlPattern=servletContext.getInitParameter(WEB_URL_PATTERRN);
		if(null!=initUrlPattern && !initUrlPattern.trim().equals("")){
			defaultUrlPattern=initUrlPattern;
		}
		initServiceNeedServlet();
	}
	private void initServiceNeedServlet(){
		ServletRegistration.Dynamic dymamic=servletContext.addServlet("apacheCXFServlet", CXFServlet.class);
		dymamic.addMapping(defaultUrlPattern);
		dymamic.setInitParameter("contextConfigLocation", contextConfigLocation);
	}
}
