package com.here.framework.http;




/**
 * http配置
 * @author koujp
 *
 */
public class HttpConfig{
	private String host;
	private int port=80;
	private String username;
	private String password;
	private String protocol="http";
	private String context;
	
	public String getProtocol() {
		return protocol;
	}
	public String getContext() {
		return context;
	}
	public String getAddress(){
		String ctx=context;
		if(null==ctx){
			return null;
		}
		if(ctx.startsWith("/")){
			ctx=ctx.substring(1);
		}
		if(ctx.endsWith("/")){
			ctx=ctx.substring(0, ctx.length()-1);
		}
		String address=this.getProtocol()+"://"+this.getHost();
		int port=this.getPort();
		if(port!=80){
			address+=":"+port;
		}
		if(null!=ctx && !ctx.equals("")){
			address+="/"+ctx+"/";
		}
		return address;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
