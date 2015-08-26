package com.here.framework.service.config;

import com.here.framework.http.HttpConfig;
/**
 * 服务配置
 * @author koujp
 *
 */
public class ServiceConfig extends HttpConfig {
	/**
	 * 客户端连接socket握手时间
	 * in second
	 */
	private long connectionTimeout=5;
	/**
	 * 请求超时时间
	 * in second
	 */
	private long receiveTimeout=10;
	/**
	 * 寻找服务时优先从本地bean池中查找
	 */
	private boolean localFind=true;
	public long getConnectionTimeout() {
		return connectionTimeout;
	}
	public void setConnectionTimeout(long connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	public long getReceiveTimeout() {
		return receiveTimeout;
	}
	public void setReceiveTimeout(long receiveTimeout) {
		this.receiveTimeout = receiveTimeout;
	}
	public boolean isLocalFind() {
		return localFind;
	}
	public void setLocalFind(boolean localFind) {
		this.localFind = localFind;
	}
}
