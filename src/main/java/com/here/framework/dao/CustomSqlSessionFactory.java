package com.here.framework.dao;

import org.apache.ibatis.session.SqlSessionFactory;
/**
 * 获取sessionFactory的工厂
 * @author koujp
 *
 */
public class CustomSqlSessionFactory{
	private String dataSourceId;
	
	/**
	 * sessionCatch的实例名字
	 */
	private String sessionFactoryCacheName;
	private SqlSessionFactoryCache sqlSessionFactoryCache;
	public CustomSqlSessionFactory(){
		this.dataSourceId=DaoConstants.DEFAULT_DATASOURCE;
		sqlSessionFactoryCache = SqlSessionFactoryCache.getInstance();
	}
	public CustomSqlSessionFactory(String  dataSourceId){
		this.dataSourceId=dataSourceId;
		sqlSessionFactoryCache = SqlSessionFactoryCache.getInstance();
	}
	public CustomSqlSessionFactory(String  dataSourceId,String sessionCacheName){
		this.dataSourceId=dataSourceId;
		sqlSessionFactoryCache = SqlSessionFactoryCache.getInstanceSync(sessionCacheName);
	}
	public void setDataSourceId(String dataSourceId) {
		this.dataSourceId = dataSourceId;
	}
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		sqlSessionFactoryCache.cacheSqlSessionFactory(dataSourceId, sqlSessionFactory);
	}
	public String getSessionFactoryCacheName() {
		return sessionFactoryCacheName;
	}
	public void setSessionFactoryCacheName(String sessionFactoryCacheName) {
		this.sessionFactoryCacheName = sessionFactoryCacheName;
	}
}
