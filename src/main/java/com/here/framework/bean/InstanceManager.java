package com.here.framework.bean;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import com.here.framework.log.HLogger;

/**
 * 实例管理
 * @author koujp
 *
 */
public class InstanceManager {
	public static class Register{
		public Register(String instanceName,Object instance){
			instanceCache.put(getInstanceKey(instanceName,instance.getClass()), instance);
		}
		public Register(Object instance){
			instanceCache.put(getInstanceKey(null,instance.getClass()), instance);
		}
	}
	private static Map<String,Object> instanceCache=new HashMap<String, Object>();
	
	private static String getInstanceKey(String instanceName,Class<?> clazz){
		if(null==instanceName){
			return clazz.getName();
		}
		return instanceName+"-"+clazz.getName();
	}
	
	public static <T> T getInstance(Class<T> clazz){
		return getInstance(clazz,false);
	}
	public static <T> T getInstance(Class<T> clazz,boolean init){
		return getInstance(null,clazz,init);
	}
	public static <T> T getInstance(String instanceName,Class<T> clazz){
		return getInstance(instanceName,clazz,false);
	}
	@SuppressWarnings("unchecked")
	public static <T> T getInstance(String instanceName,Class<T> clazz,boolean create){
		String instanceKey=getInstanceKey(instanceName,clazz);
		T instance=(T)instanceCache.get(instanceKey);
		
		if(create && null==instance){
			try {
				Constructor<T> constructor=clazz.getDeclaredConstructor();
				if(null!=constructor){
					constructor.setAccessible(true);
					instance=constructor.newInstance();
					instanceCache.put(instanceKey, instance);
				}
			} catch (Exception e) {
				HLogger.getInstance(InstanceManager.class).info("instance create info:", e);
			}
		}
		
		return instance;
	}
}
