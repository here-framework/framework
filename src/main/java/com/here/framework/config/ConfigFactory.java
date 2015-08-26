package com.here.framework.config;

import org.springframework.beans.factory.FactoryBean;

import com.here.framework.bean.InstanceManager;
import com.here.framework.file.FileUtil;
import com.here.framework.log.HLogger;
import com.here.framework.script.engine.HScriptEngine;
import com.here.framework.util.JSONUtil;
/**
 * 配置工厂类
 * @author koujp
 *
 * @param <T>
 */
public class ConfigFactory<T> implements FactoryBean<T>{
	private Object configFile;
	private String name;
	private String className;
	private Class<?> clazz=null;
	private boolean singleton=true;
	private synchronized Class<?> getClazz(){
		if(null!=clazz){
			return clazz;
		}
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			HLogger.getInstance(this.getClass()).error(e);
		}
		return clazz;
	}
	@SuppressWarnings("unchecked")
	@Override
	public T getObject() throws Exception {
		if(null==configFile){
			throw new ConfigException("configFile is null!");
		}
		String script=null;
		if(configFile instanceof FileResource){
			FileResource fileResouce=(FileResource)configFile;
			script=fileResouce.getContent();
		}else if(configFile instanceof String){
			script=FileUtil.contentFromClasspath(configFile.toString());
		}else{
			throw new ConfigException("configFile type is invalid!");
		}
		HScriptEngine engine=HScriptEngine.getInstance();
		engine.eval(script);
		String json=engine.getJson(name);
		
		T object=(T)JSONUtil.toBean(json, getClazz());
		
		return object;
	}
	public static <E> E getConfig(Class<E> configClazz){
		return InstanceManager.getInstance(configClazz);
	}
	public static <E> E getConfig(String configName,Class<E> configClazz){
		return InstanceManager.getInstance(configName,configClazz);
	}
	@Override
	public Class<?> getObjectType() {
		return clazz;
	}

	@Override
	public boolean isSingleton() {
		return singleton;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public void setSingleton(boolean singleton) {
		this.singleton = singleton;
	}
	public Object getConfigFile() {
		return configFile;
	}
	public void setConfigFile(Object configFile) {
		this.configFile = configFile;
	}

	
}
