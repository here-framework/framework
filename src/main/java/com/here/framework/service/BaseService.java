package com.here.framework.service;


import com.here.framework.bean.InstanceManager;
import com.here.framework.dao.CustomSqlSession;
import com.here.framework.dao.DaoConstants;
import com.here.framework.dao.SqlSessionFactoryCache;
import com.here.framework.service.client.ServiceProxy;
import com.here.framework.service.client.ServiceProxyImpl;
/**
 * 基础服务类提供访问数据功能
 * @author koujp
 *
 */
public class BaseService{
	private ServiceProxy serviceProxy;
	
	SqlSessionFactoryCache sqlSessionFactoryCache = null;
	
	public BaseService(){
		sqlSessionFactoryCache = SqlSessionFactoryCache.getInstance();
	}
	public BaseService(String sessionCacheName){
		sqlSessionFactoryCache = SqlSessionFactoryCache.getInstance(sessionCacheName);
	}
	private void initServiceProxy(){
		
		serviceProxy = InstanceManager.getInstance(ServiceProxyImpl.class);
	}
	
	public <T> T getService(Class<T> interfaceClazz){
		if(null == serviceProxy){
			initServiceProxy();
		}
		return serviceProxy.lookup(interfaceClazz);
	}
	
	public <T> T getMapper(Class<T> mapperInterface){
		return getMetaDataSqlSession().getMapper(mapperInterface);
	}
	protected CustomSqlSession getCustomSqlSession(String dataSourceId){
		return sqlSessionFactoryCache.getCustomSqlSession(dataSourceId);
	}
	protected CustomSqlSession getMetaDataSqlSession(){
		return getCustomSqlSession(DaoConstants.DEFAULT_DATASOURCE);
	}
}
