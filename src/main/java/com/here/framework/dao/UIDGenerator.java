package com.here.framework.dao;

import java.util.UUID;
public class UIDGenerator {

	private static UIDGenerator instance = new UIDGenerator();
	
	private UIDGenerator() {
		
	}
	
	/**
	 * 获得实例
	 * @return
	 */
	public static UIDGenerator getInstance() {
		return instance;
	}

	/**
	 * 生成UUID
	 * @return
	 */
	public String generate() {
		return UUID.randomUUID().toString();
	}
}
