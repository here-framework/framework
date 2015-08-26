package com.here.framework.file;


import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import com.here.framework.log.HLogger;

/**
 * 文件工具类
 * @author koujp
 *
 */
public class FileUtil {
	private final static Charset charset=Charset.forName("utf-8");
	public static String contentFromClasspath(String fileClassPath){
		StringBuffer contents=new StringBuffer();
		InputStream in=FileUtil.class.getClassLoader().getResourceAsStream(fileClassPath);
		byte[] bytes=new byte[1024];
		int len=0;
		try {
			while((len=in.read(bytes))>0){
				contents.append(new String(bytes,0,len,charset));
			}
		} catch (IOException e) {
			HLogger.getInstance(FileUtil.class).error(e);
		}finally{
			if(null != in){
				try {
					in.close();
				} catch (IOException e) {
					HLogger.getInstance(FileUtil.class).error(e);
				}
			}
		}
		return contents.toString();
	}
}
