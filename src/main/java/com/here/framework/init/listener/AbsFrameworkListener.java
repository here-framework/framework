package com.here.framework.init.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
/**
 * 监听器基类
 * @author koujp
 *
 */
public abstract class AbsFrameworkListener implements ServletContextListener {
	protected ServletContext servletContext;
	protected String defaultUrlPattern="/*";
}
