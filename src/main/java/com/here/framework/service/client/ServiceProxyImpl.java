package com.here.framework.service.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.cxf.jaxrs.client.Client;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.here.framework.log.HLogger;
import com.here.framework.service.RSServerConfig;
import com.here.framework.service.ServiceExceptionMapper;
import com.here.framework.service.config.ServiceConfig;

/**
 * 服务客户端
 * 
 * @author koujp
 *
 */
public class ServiceProxyImpl implements ServiceProxy,ApplicationContextAware {
	private ApplicationContext context;
	private ServiceConfig serviceClientConfig;
	private Map<String, String> headers = new HashMap<String, String>();
	private Map<Class<?>,Object> serviceCache=new ConcurrentHashMap<Class<?>, Object>();
	
	/**
	 * rest服务配置的实例名字,不配置则使用默认
	 */
	private String restConfigName;
	
	private RSServerConfig rsServerConfig = null;
	public ServiceProxyImpl(ServiceConfig serviceClientConfig) {
		init(serviceClientConfig);
	}
	private void init(ServiceConfig serviceClientConfig){
		this.serviceClientConfig = serviceClientConfig;
		addHeader("Content-Type", "application/json");
	}
	private JAXRSClientFactoryBean getClientProxy(Class<?> clazz) {
		JAXRSClientFactoryBean jaxrsClientFactoryBean = new JAXRSClientFactoryBean();
		
		
		jaxrsClientFactoryBean.setUsername(serviceClientConfig.getUsername());
		jaxrsClientFactoryBean.setPassword(serviceClientConfig.getPassword());

		List<Object> providers = new ArrayList<Object>();
		providers.add(new ServiceExceptionMapper());
        JacksonJsonProvider provider = new JacksonJsonProvider();
        provider.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        providers.add(provider);
		jaxrsClientFactoryBean.setProviders(providers);

		return jaxrsClientFactoryBean;
	}

	/* (non-Javadoc)
	 * @see com.here.framework.service.client.ServiceProxy#lookup(java.lang.Class)
	 */
	@Override
	public <T> T lookup(Class<T> clazz) {
		return lookup(clazz, null);
	}
	
	/* (non-Javadoc)
	 * @see com.here.framework.service.client.ServiceProxy#lookup(java.lang.Class, java.lang.String)
	 */
	@Override
	public <T> T lookup(Class<T> clazz, String address) {
		
		if (address == null) {
			address=serviceClientConfig.getAddress();
		}
		if(serviceClientConfig.isLocalFind()){
			T bean = lookupFromLocal(clazz);
			if(null!=bean){
				return bean;
			}
		}
		return lookupFromRemote(clazz,address);
	}
	private void initRSServerConfig(){
		if(null != this.rsServerConfig){
			return;
		}
		if(null == this.restConfigName){
			RSServerConfig.getInstance();
		}else{
			RSServerConfig.getInstance(restConfigName);
		}
	}
	@SuppressWarnings("unchecked")
	private <T> T lookupFromLocal(Class<T> clazz){
		Object bean=serviceCache.get(clazz);
		if(null != bean){
			return (T)bean;
		}
		
		initRSServerConfig();
		for(Object b :rsServerConfig.getServiceBeans()){
			if(clazz.isInstance(b)){
				serviceCache.put(clazz, b);
				return (T)b;
			}
		}
		return null;
	}
	/* (non-Javadoc)
	 * @see com.here.framework.service.client.ServiceProxy#getBean(java.lang.Class)
	 */
	@Override
	public <T> T getBean(Class<T> clazz){
		if(null == context){
			return null;
		}
		Map<String,T> beanMap=context.getBeansOfType(clazz, true, true);
		if(beanMap.size()>1){
			throw new RuntimeException("exist more than one bean with type "+clazz.getName()+" exist!");
		}
		for(Map.Entry<String,T> entry:beanMap.entrySet()){
			return entry.getValue();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	private  <T> T lookupFromRemote(Class<T> clazz, String address) {
		JAXRSClientFactoryBean clientProxy = getClientProxy(clazz);
		clientProxy.setAddress(address);
		clientProxy.setServiceClass(clazz);

		if (null != headers) {
			clientProxy.setHeaders(headers);
		}
		Client cli=clientProxy.create();
		try{
			ClientConfiguration cliConfig=WebClient.getConfig(cli);
			HTTPConduit con=(HTTPConduit)(cliConfig.getConduit());
			HTTPClientPolicy policy=con.getClient();
			policy.setConnectionTimeout(serviceClientConfig.getConnectionTimeout()*1000);
			policy.setReceiveTimeout(serviceClientConfig.getReceiveTimeout()*1000);
			
		}catch(Exception e){
			HLogger.getInstance(this.getClass()).error(e);
		}
		
		return (T)cli;
	}
	/* (non-Javadoc)
	 * @see com.here.framework.service.client.ServiceProxy#getServiceConfig()
	 */
	@Override
	public ServiceConfig getServiceConfig() {
		return serviceClientConfig;
	}

	/* (non-Javadoc)
	 * @see com.here.framework.service.client.ServiceProxy#addHeader(java.lang.String, java.lang.String)
	 */
	@Override
	public void addHeader(String name, String value) {
		headers.put(name, value);
	}
	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;
	}
	public String getRestConfigName() {
		return restConfigName;
	}
	public void setRestConfigName(String restConfigName) {
		this.restConfigName = restConfigName;
	}
}
