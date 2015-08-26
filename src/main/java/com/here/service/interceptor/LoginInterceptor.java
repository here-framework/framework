package com.here.service.interceptor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptor;

/**
 * 登录拦截器
 * @author koujp
 *
 */
@SuppressWarnings("rawtypes")
public class LoginInterceptor implements PhaseInterceptor{
	private String username;
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private String password;
	@Override
	public void handleFault(Message message) {
	}
	@Override
	public void handleMessage(Message message) throws Fault {
		/*if (true) return; 
		AuthorizationPolicy policy = (AuthorizationPolicy)message.get(AuthorizationPolicy.class);
		 if(null==policy){
			 throw new AuthorityException("invalid username or password!");
		 }
		 String username=policy.getUserName();
		 String password=policy.getPassword();
		 if(!checkAuthoity(username,password)){
			 throw new AuthorityException("invalid username or password!");
		 }*/
	}
	private boolean checkAuthoity(String username,String password){
		if(null==this.username){
			return true;
		}
		if(null==this.password){
			return this.username.trim().equals(username);
		}
		return this.username.trim().equals(username) && this.password.trim().equals(password);
	}
	@Override
	public Collection getAdditionalInterceptors() {
		return Collections.emptySet();
	}
	@Override
	public Set getAfter() {
		return Collections.emptySet();
	}
	@Override
	public Set getBefore() {
		return Collections.emptySet();
	}
	@Override
	public String getId() {
		return "loginInterceptor";
	}
	@Override
	public String getPhase() {
		return "unmarshal";
	}

}
