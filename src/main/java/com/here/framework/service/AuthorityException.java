package com.here.framework.service;
/**
 * 权限校验异常
 * @author koujp
 *
 */@SuppressWarnings("serial")

public class AuthorityException extends RuntimeException {
	public AuthorityException(){
	}
	public AuthorityException(String message){
		super(message);
	}
}	
