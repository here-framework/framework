package com.here.framework.log;

import java.io.InputStream;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

/**
 * logback日志配置文件
 * @author koujp
 *
 */
public class HLoggerConfig4Logback implements IHLoggerConfig {

	/**
	 * logback日志配置文件自定义加载
	 * @param inputStream
	 * @param loggerFactory
	 */
	@Override
	public void initLoggerConfig(InputStream inputStream){
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();  
        JoranConfigurator configurator = new JoranConfigurator();  
        configurator.setContext(loggerContext);  
        loggerContext.reset();  
        try {
			configurator.doConfigure(inputStream);
		} catch (JoranException e) {
			throw new RuntimeException(e);
		}  
        StatusPrinter.printInCaseOfErrorsOrWarnings(loggerContext); 
	}

}
