package com.here.framework.config;
/**
 * 文件资源
 * @author koujp
 *
 */
public class FileResource {
	private String filePath;
	private String content;
	public FileResource(String filePath,String content){
		this.filePath=filePath;
		this.content=content;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
