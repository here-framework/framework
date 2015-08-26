package com.here.framework.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletRegistration;

import org.springframework.web.servlet.DispatcherServlet;

import com.here.framework.init.listener.AbsFrameworkListener;
/**
 * web监听器配置
 * @author koujp
 *
 */
public class FrameworkWebListener extends AbsFrameworkListener {
	public static final String WEB_URL_PATTERRN="web-url-pattern";
	public static final String contextConfigLocation="classpath:framework/config/web/framework-web.xml";
	@Override
	public void contextDestroyed(ServletContextEvent servletcontextevent) {

	}

	@Override
	public void contextInitialized(ServletContextEvent servletcontextevent) {
		servletContext=servletcontextevent.getServletContext();
		String initUrlPattern=servletContext.getInitParameter(WEB_URL_PATTERRN);
		if(null!=initUrlPattern && !initUrlPattern.trim().equals("")){
			defaultUrlPattern=initUrlPattern;
		}
		initWebNeedServlet();
	}
	private void initWebNeedServlet(){
		ServletRegistration.Dynamic dymamic=servletContext.addServlet("springDispatchServlet",DispatcherServlet.class);
		dymamic.setInitParameter("contextConfigLocation", contextConfigLocation);
		
	}
}
