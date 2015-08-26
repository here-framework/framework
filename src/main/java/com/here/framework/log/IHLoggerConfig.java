package com.here.framework.log;

import java.io.InputStream;
/**
 * 日志配置接口
 * @author koujp
 *
 */
public interface IHLoggerConfig {

	public abstract void initLoggerConfig(InputStream inputStream);

}