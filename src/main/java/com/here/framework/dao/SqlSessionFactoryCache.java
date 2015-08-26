package com.here.framework.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;

/**
 * sql sessionFactory缓存池
 * @author koujp
 *
 */
public class SqlSessionFactoryCache {
	private Map<String,SqlSessionFactory> sessionFactoryCache=new HashMap<String, SqlSessionFactory>();
	private Map<String,CustomSqlSession> sqlSessionProxyCache=new HashMap<String, CustomSqlSession>();
	
	private static final SqlSessionFactoryCache instance = new SqlSessionFactoryCache();
	
	private static final Map<String,SqlSessionFactoryCache> instanceCache = new ConcurrentHashMap<String, SqlSessionFactoryCache>();
	private SqlSessionFactoryCache(){
	}
	public static SqlSessionFactoryCache getInstance(){
		return instance;
	}
	/**
	 * 线程安全调用方法
	 * @param instanceName
	 * @return
	 */
	public static synchronized SqlSessionFactoryCache getInstanceSync(String instanceName){
		SqlSessionFactoryCache cache=instanceCache.get(instanceName);
		if(null == cache){
			cache = new SqlSessionFactoryCache();
			instanceCache.put(instanceName, cache);
		}
		return cache;
	}
	/**
	 * 非线程安全调用
	 * @param instanceName
	 * @return
	 */
	public static SqlSessionFactoryCache getInstance(String instanceName){
		SqlSessionFactoryCache cache=instanceCache.get(instanceName);
		if(null == cache){
			cache = new SqlSessionFactoryCache();
			instanceCache.put(instanceName, cache);
		}
		return cache;
	}
	public void cacheSqlSessionFactory(String dataSourceId,SqlSessionFactory sqlSessionFactory){
		if(!sqlSessionProxyCache.containsKey(dataSourceId)){
			sqlSessionProxyCache.put(dataSourceId, new CustomSqlSession(new SqlSessionTemplate(sqlSessionFactory)));
		}
		sessionFactoryCache.put(dataSourceId, sqlSessionFactory);
	}
	public CustomSqlSession getCustomSqlSession(String dataSourceId){
		return sqlSessionProxyCache.get(dataSourceId);
	}
}
