package com.here.framework.config;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.here.framework.log.HLogger;

/**
 * 框架配置
 * @author koujp
 *
 */
public class BaseConfig implements Map<String,Object>{
	
	private Map<String,Object> _private_config_00_map=new HashMap<String, Object>();
	private static final String ingnoreKey_private_00_keyx00=getIngnoreKey();
	private static String getIngnoreKey(){
		Field[] fs=BaseConfig.class.getDeclaredFields();
		for(Field f:fs){
			if(f.getType().isAssignableFrom(Map.class)){
				return f.getName();
			}
		}
		return null;
	}
	@Override
	public void clear() {
		Field[] fs=getAllFields();
		Class<?> clazz=this.getClass();
		Object obj=null;
		try {
			obj=clazz.newInstance();
			
		} catch (InstantiationException | IllegalAccessException e) {
			error(e);
		}
		if(null!=obj){
			for(Field f:fs){
				setFieldValue(f, getFieldValue(f,obj));
			}
		}
		getPrivateMap().clear();
	}
	private Map<String,Object> getPrivateMap(){
		return _private_config_00_map;
	}
	private boolean setFieldValueByMethod(Field field,Object value){
		String fieldName=field.getName();
		String methodName="set"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
		Method[] methods=this.getClass().getDeclaredMethods();
		if(null==methods){
			return false;
		}
		for(Method method:methods){
			if(method.getName().equals(methodName)){
				try{
					method.setAccessible(true);
					method.invoke(this, value);
					return true;
				}catch(Exception e){
					HLogger.getInstance(this.getClass()).error(e);
					System.out.println("error--method:"+methodName+"not find!");
				}
			}
		}
		return false;
	}
	private Object setFieldValue(Field field,Object value){
		if(setFieldValueByMethod(field,value)){
			return value;
		}
		try {
			field.setAccessible(true);
			field.set(this, value);
			return value;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			error(e);
		}
		return null;
	}
	private Object getFieldValue(Field field){
		return getFieldValue(field,this);
	}
	private static Object getFieldValue(Field field,Object target){
		String fieldName=field.getName();
		String methodName="get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1);
		Object fieldValue=null;
		try {
			Method m=target.getClass().getMethod(methodName);
			m.setAccessible(true);
			fieldValue=m.invoke(target);
		} catch (Exception e) {
			try {
				field.setAccessible(true);
				fieldValue=field.get(target);
			} catch (IllegalArgumentException | IllegalAccessException e1) {
				error(target,e1);
			}
		}
		return fieldValue;
	}
	private Field[] getAllFields(){
		Class<?> clazz=this.getClass();
		Map<String,Field> fmap=new HashMap<String, Field>();
		
		while(true){
			Field[] fs=clazz.getDeclaredFields();
			if(null!=fs){
				for(Field f:fs){
					if(fmap.containsKey(f.getName())){
						continue;
					}
					if(!this.isIgnore(f.getName())){
						fmap.put(f.getName(), f);
					}
				}
			}
			if(null==clazz || (clazz=clazz.getSuperclass())==Object.class){
				break;
			}
			
		}
		Field[] fields=fmap.values().toArray(new Field[0]);
		return fields;
	}
	private void error(Exception e){
		error(this,e);
	}
	private static void error(Object target,Exception e){
		HLogger.getInstance(target.getClass()).error(e);
	}
	private boolean isIgnore(Object key){
		return ingnoreKey_private_00_keyx00.equals(key);
	}
	@Override
	public boolean containsKey(Object key) {
		
		for(Field f : getAllFields()){
			if(f.getName().equals(key)){
				return true;
			}
		}
		return getPrivateMap().containsKey(key);
	}
	@Override
	public boolean containsValue(Object value) {
		
		if(null==value){
			return false;
		}
		for(Field f : getAllFields()){
			try {
				f.setAccessible(true);
				if(value.equals(getFieldValue(f))){
					return true;
				}
			} catch (Exception e) {
				error(e);
			}
		}
		return getPrivateMap().containsKey(value);
	}
	private Map.Entry<String, Object> createEntry(Field f){
		f.setAccessible(true);
		final Object value=getFieldValue(f);
		Map.Entry<String, Object> entry=new Map.Entry<String, Object>() {
			private Object val=value;
			@Override
			public String getKey() {
				return f.getName();
			}

			@Override
			public Object getValue() {
				return val;
			}
			@Override
			public Object setValue(Object value) {
				val=value;
				return val;
			}
		};
		return entry;
	}
	@Override
	public Set<Map.Entry<String, Object>> entrySet() {
		Set<java.util.Map.Entry<String, Object>> set=new HashSet<Map.Entry<String,Object>>();
		
		for(Field f : getAllFields()){
			Map.Entry<String, Object> entry=createEntry(f);
			if(null==entry){
				continue;
			}
			set.add(entry);
		}
		set.addAll(getPrivateMap().entrySet());
		return set;
	}
	@Override
	public Object get(Object key) {
		for(Field f : getAllFields()){
			if(!key.equals(f.getName())){
				continue;
			}
			return getFieldValue(f);
		}
		return getPrivateMap().get(key);
	}
	@Override
	public boolean isEmpty() {
		Field[] fs=getAllFields();
		return getPrivateMap().isEmpty() && (null==fs || fs.length==0);
	}
	@Override
	public Set<String> keySet() {
		Set<String> keys=new HashSet<String>();
		for(Field f : getAllFields()){
			keys.add(f.getName());
		}
		keys.addAll(getPrivateMap().keySet());
		return keys;
	}
	@Override
	public Object put(String key, Object value) {
		for(Field f : getAllFields()){
			if(f.getName().equals(key)){
				return this.setFieldValue(f, value);
			}
		}
		return getPrivateMap().put(key, value);
	}
	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		Set<? extends String> keys=m.keySet();
		for(String key:keys){
			Object value=m.get(key);
			this.put(key, value);
		}
	}
	@Override
	public Object remove(Object key) {
		return getPrivateMap().remove(key);
	}
	@Override
	public int size() {
		Field[] fs=getAllFields();
		return fs.length+getPrivateMap().size();
	}
	@Override
	public Collection<Object> values() {
		List<Object> values=new ArrayList<Object>();
		for(Field f : getAllFields()){
			Object value= this.getFieldValue(f);
			if(null!=value){
				values.add(value);
			}
		}
		values.addAll(getPrivateMap().values());
		return values;
	}
}
