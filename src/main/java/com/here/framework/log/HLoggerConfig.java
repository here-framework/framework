package com.here.framework.log;

import java.io.InputStream;

/**
 * logback日志配置
 * @author koujp
 *
 */
public class HLoggerConfig{ 
	private static final String LOGBACK_IMPL="ch.qos.logback.classic.LoggerContext";
	
	/**
	 * 用流初始化logback
	 * @param inputStream
	 */
	static void initLoggerConfig(InputStream inputStream){
		IHLoggerConfig loggerConfigProxy=getLoggerConfig();
		if(null==loggerConfigProxy){
			return;
		}
		loggerConfigProxy.initLoggerConfig(inputStream);
	}
	
	private static IHLoggerConfig getLoggerConfig(){
		IHLoggerConfig loggerConfigProxy=null;
		if(checkClassExist(LOGBACK_IMPL)){
			loggerConfigProxy=new HLoggerConfig4Logback();
		}
		return loggerConfigProxy;
	}
	
	private static boolean checkClassExist(String className){
		boolean blnExsit=false;
		try {
			Class.forName(className);
			blnExsit=true;
		} catch (ClassNotFoundException e) {
		}
		return blnExsit;
	}
}
