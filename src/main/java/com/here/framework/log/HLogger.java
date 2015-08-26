package com.here.framework.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志类
 * @author koujp
 *
 */
public class HLogger implements IHereLogger{
	private Logger loggerProxy=null;
	public static HLogger getInstance(Class<?> clazz){
		return new HLogger(clazz);
	}
	public static HLogger getInstance(String name){
		return new HLogger(name);
	}
	private HLogger(Class<?> clazz){
		loggerProxy=LoggerFactory.getLogger(clazz);
	}
	private HLogger(String name){
		loggerProxy=LoggerFactory.getLogger(name);
	}
	@Override
	public void debug(String s) {
		loggerProxy.debug(s);
		
	}
	@Override
	public void debug(String s, Object obj) {
		loggerProxy.debug(s, obj);
		
	}
	@Override
	public void debug(String s, Object[] aobj) {
		loggerProxy.debug(s, aobj);
		
	}
	@Override
	public void error(String s) {
		loggerProxy.error(s);
		
	}
	@Override
	public void error(String s, Object obj) {
		loggerProxy.error(s, obj);
		
	}
	@Override
	public void error(String s, Object[] aobj) {
		loggerProxy.error(s, aobj);
		
	}
	@Override
	public void error(String s, Throwable throwable) {
		loggerProxy.error(s, throwable);
		
	}
	@Override
	public String getName() {
		return loggerProxy.getName();
	}
	@Override
	public void info(String s) {
		loggerProxy.info(s);
		
	}
	@Override
	public void info(String s, Object obj) {
		loggerProxy.info(s, obj);
		
	}

	@Override
	public void info(String s, Object[] aobj) {
		loggerProxy.info(s, aobj);
		
	}

	@Override
	public void info(String s, Throwable throwable) {
		loggerProxy.info(s, throwable);
		
	}

	@Override
	public void trace(String s) {
		loggerProxy.trace(s);
		
	}

	@Override
	public void trace(String s, Object obj) {
		loggerProxy.trace(s,obj);
		
	}

	@Override
	public void trace(String s, Object[] aobj) {
		loggerProxy.trace(s, aobj);
		
	}

	@Override
	public void trace(String s, Throwable throwable) {
		loggerProxy.trace(s, throwable);
		
	}

	@Override
	public void warn(String s) {
		loggerProxy.warn(s);
		
	}

	@Override
	public void warn(String s, Object obj) {
		loggerProxy.warn(s, obj);
		
	}

	@Override
	public void warn(String s, Object[] aobj) {
		loggerProxy.warn(s, aobj);
		
	}
	
	@Override
	public void warn(String s, Throwable throwable) {
		loggerProxy.warn(s, throwable);
		
	}
	
	@Override
	public void error(Throwable t) {
		error(t.getMessage(), t);
	}

}
