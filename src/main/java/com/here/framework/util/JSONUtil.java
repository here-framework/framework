package com.here.framework.util;

import java.util.Set;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.introspect.AnnotatedClass;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;

public class JSONUtil {
	private static ObjectMapper mapper;

	/**
	 * 获取ObjectMapper实例
	 * 
	 * @param createNew
	 *            方式：true，新实例；false,存在的mapper实例
	 * @return
	 */
	public static synchronized ObjectMapper getMapperInstance(boolean createNew) {
		if (createNew) {
			return new ObjectMapper();
		} else if (mapper == null) {
			mapper = new ObjectMapper();
		}
		return mapper;
	}
	
	/**
	 * 将java对象转换成json字符串
	 * 
	 * @param obj 准备转换的对象
	 * @return json字符串
	 * @throws ConvertException
	 */
	public static String toJson(Object obj) throws ConvertException {
		return toJson(obj, (Set<String>) null);
	}

	/**
	 * 将java对象转换成json字符串
	 * 
	 * @param obj 准备转换的对象
	 * @param ignoreFields 忽略的字段集合
	 * @return json字符串
	 * @throws ConvertException
	 */
	public static String toJson(Object obj, Set<String> ignoreFields) throws ConvertException {
		return toJson(obj, ignoreFields, false);
	}

	/**
	 * 将java对象转换成json字符串
	 * 
	 * @param obj 准备转换的对象
	 * @param createNew
	 *            ObjectMapper实例方式:true，新实例;false,存在的mapper实例
	 * @return json字符串
	 * @throws ConvertException
	 */
	public static String toJson(Object obj, Boolean createNew) throws ConvertException {
		return toJson(obj, null, createNew);
	}
	
	/**
	 * 将java对象转换成json字符串
	 * 
	 * @param obj 准备转换的对象
	 * @param ignoreFields 忽略的字段集合
	 * @param createNew
	 *            ObjectMapper实例方式:true，新实例;false,存在的mapper实例
	 * @return json字符串
	 * @throws ConvertException
	 */
	public static String toJson(Object obj, Set<String> ignoreFields, Boolean createNew) throws ConvertException {
		try {
			ObjectMapper objectMapper = getMapperInstance(createNew);
			if (ignoreFields != null) {
				objectMapper.setSerializationConfig(objectMapper.getSerializationConfig().withSerializationInclusion( 
	                    JsonSerialize.Inclusion.NON_NULL)); 
	
	            FilterProvider filters = new SimpleFilterProvider().addFilter(obj.getClass().getName(), 
	                    SimpleBeanPropertyFilter.serializeAllExcept(ignoreFields)); 
	            objectMapper.setFilters(filters); 
	
	            objectMapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector() { 
	                @Override 
	                public Object findFilterId(AnnotatedClass ac) { 
	                    return ac.getName(); 
	                } 
	            }); 
			}
			
			String json = objectMapper.writeValueAsString(obj);
			return json;
		} catch (Exception e) {
			throw new ConvertException(e);
		}
	}

	/**
	 * 将json字符串转换成java对象
	 * 
	 * @param json
	 *            准备转换的json字符串
	 * @param cls
	 *            准备转换的类
	 * @return
	 * @throws ConvertException
	 */
	public static <T> T toBean(String json, Class<T> cls) throws ConvertException {
		T bean=null;
		try {
			if(null==json || "".equals(json)){
				throw new ConvertException("json content is null or empty string.");
			}
			ObjectMapper objectMapper = getMapperInstance(false);
			objectMapper.getDeserializationConfig()
			.without(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
			
			
			bean=objectMapper.readValue(json, cls);
		} catch (Exception e) {
			throw new ConvertException(e);
		}
		return bean;
	}

	/**
	 * 将json字符串转换成java对象
	 * 
	 * @param json
	 *            准备转换的json字符串
	 * @param cls
	 *            准备转换的类
	 * @param createNew
	 *            ObjectMapper实例方式:true，新实例;false,存在的mapper实例
	 * @return
	 * @throws Exception
	 */
	public static <T> T toBean(String json, Class<T> cls, Boolean createNew)
			throws ConvertException {
		try {
			ObjectMapper objectMapper = getMapperInstance(createNew);
			return objectMapper.readValue(json, cls);
		} catch (Exception e) {
			throw new ConvertException(e);
		}
	}
}