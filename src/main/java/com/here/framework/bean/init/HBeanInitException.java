package com.here.framework.bean.init;
/**
 * 初始化异常
 * @author koujp
 *
 */
public class HBeanInitException extends RuntimeException {

	private static final long serialVersionUID = 7414756222446688265L;
	
	public HBeanInitException(){
		
	}
	public HBeanInitException(String message){
		super(message);
	}
}
