package com.here.framework.config;

import org.springframework.beans.factory.FactoryBean;

import com.here.framework.file.FileUtil;

/**
 *  文件资源
 * @author koujp
 *
 */
public class FileResourceFactory implements FactoryBean<FileResource>{
	//文件类路径
	private String filePath;

	@Override
	public FileResource getObject() throws Exception {
		String script=FileUtil.contentFromClasspath(filePath);
		return new FileResource(filePath,script);
	}

	@Override
	public Class<?> getObjectType() {
		return String.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
