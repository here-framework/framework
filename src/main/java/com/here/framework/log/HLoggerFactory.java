package com.here.framework.log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 日志实现类获取
 * @author koujp
 *
 */
public class HLoggerFactory {
	static {
		init();
	}
	
	public static IHereLogger getLogger(Class<?> clazz){
		return HLogger.getInstance(clazz);
	}
	
	public static IHereLogger getLogger(String name){
		return HLogger.getInstance(name);
	}
	
	/**
	 * 加载日志配置
	 */
	private static void init() {
        URL url = HLoggerConfig.class.getResource("/" + "config/logback.xml");
        
        if (url == null) {
            url = HLoggerConfig.class.getResource("/framework/config/logback.xml");
        }
        InputStream in = null;
        try {
        	in = new FileInputStream(url.getPath());
            HLoggerConfig.initLoggerConfig(in);
        } catch (FileNotFoundException e) {
        	try {
				in = url.openStream();
			} catch (FileNotFoundException e1) {
				System.out.println("logback.xml没有发现！");
			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println("读取logback.xml异常！" + e.getMessage());
			}
            HLoggerConfig.initLoggerConfig(in);
//            throw new InitRuntimeException("日志不能成功初始化！", e);
        }
    }
}
