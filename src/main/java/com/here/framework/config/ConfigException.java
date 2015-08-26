package com.here.framework.config;

import com.here.framework.BaseException;
/**
 * 配置异常
 * @author koujp
 *
 */
public class ConfigException extends BaseException {
	private static final long serialVersionUID = 6620037683381687457L;
	public ConfigException(){
		super("framework config exception");
	}
	public ConfigException(String message){
		super("framework config exception:"+message);
	}
}
