package com.here.framework.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

import com.here.framework.log.HLogger;
/**
 * socket 池
 * @author koujp
 *
 */
public class SocketPool {
	public static class SocketKey{
		private String host;
		private int port;
		public SocketKey(String host,int port){
			this.host=host;
			this.port=port;
		}
		public String getHost() {
			return host;
		}
		public int getPort() {
			return port;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((host == null) ? 0 : host.hashCode());
			result = prime * result + port;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			SocketKey other = (SocketKey) obj;
			if (host == null) {
				if (other.host != null)
					return false;
			} else if (!host.equals(other.host))
				return false;
			if (port != other.port)
				return false;
			return true;
		}
	}
	public static class SocketInfo{
		private Socket socket=null;
		public SocketInfo(Socket socket){
			this.socket=socket;
		}
		public Socket getSocket() {
			return socket;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((socket == null) ? 0 : socket.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object other) {
			if (this == other){
				return true;
			}
			if(null==other || other.getClass()!=this.getClass()){
				return false;
			}
			if(other instanceof Socket){
				return this.socket==other;
			}
			if(other instanceof SocketInfo){
				SocketInfo oSocketInfo=(SocketInfo)other;
				return oSocketInfo.getSocket()==this.socket;
			}
			return false;
		}
	}
	private int maxSocketPoolSize=20;
	
	//unused
	private int poolSize=20;
	// in seconds
	private int networkTimeout=3;
	// in seconds
	private int connectTimeout=3;
	
	
	protected ConcurrentMap<SocketKey,Queue<SocketInfo>> socketCache=new ConcurrentHashMap<SocketKey,Queue<SocketInfo>>();
	/**
	 * 
	 * @param socketKey
	 * @param socketConfig
	 * @return
	 */
	public  SocketInfo getSocketInfo(SocketKey socketKey){
		Queue<SocketInfo> socketInfos=getSocketInfos(socketKey);
		
		SocketInfo socketInfo=socketInfos.poll();
		
		if(null==socketInfo){
			socketInfo=createNewSocket(socketKey);
			
		}else{
			try {
				if(!socketInfo.getSocket().getKeepAlive()){
					socketInfo=createNewSocket(socketKey);
				}else{
					System.out.println("------valid----"+socketInfo+"---count:"+socketInfos.size());
				}
			} catch (SocketException e) {
				HLogger.getInstance(SocketPool.class).error(e);
			}
		}
		return socketInfo;
	}
	public  Socket getSocket(SocketKey socketKey){
		SocketInfo socketInfo=getSocketInfo(socketKey);
		return socketInfo.getSocket();
	}
	public Socket getSocket(String host,int port){
		SocketInfo socketInfo=getSocketInfo(new SocketKey(host,port));
		return socketInfo.getSocket();
	}
	private SocketInfo createNewSocket(SocketKey socketKey){
		Socket socket = new Socket();
		try {
			socket.setReuseAddress(true);
			socket.setKeepAlive(true);
			socket.setSoTimeout(this.networkTimeout*1000);
			socket.connect(new InetSocketAddress(socketKey.getHost(), socketKey.getPort()),this.connectTimeout*1000);
		} catch (Exception e) {
			HLogger.getInstance(SocketPool.class).error(e);
			try {
				socket.close();
			} catch (IOException e1) {
				HLogger.getInstance(SocketPool.class).error(e1);
			}
			throw new RuntimeException("socket connect error:"+e.getMessage());
		}
		return new SocketInfo(socket);
	}
	private synchronized Queue<SocketInfo> getSocketInfos(SocketKey socketKey){
		Queue<SocketInfo> socketInfos=socketCache.get(socketKey);
		if(null==socketInfos){
			socketInfos=new ConcurrentLinkedQueue<SocketPool.SocketInfo>();
			socketCache.put(socketKey, socketInfos);
		}
		return socketInfos;
	}
	/**
	 * 释放socket到池中
	 * @param socket
	 */
	public  void releaseSocket(Socket socket){
		if(null==socket){
			return;
		}
		InetAddress inetAddr=socket.getInetAddress();
		String host=inetAddr.getHostAddress();
		int port=socket.getPort();
		
		SocketKey socketKey=new SocketKey(host,port);
		Queue<SocketInfo> socketInfos=getSocketInfos(socketKey);
		SocketInfo socketInfo=new SocketInfo(socket);
		boolean isValid=false;
		synchronized(this){
			if(socketInfos.size()<maxSocketPoolSize && !socketInfos.contains(socketInfo)){
				socketInfos.add(socketInfo);
				isValid=true;
			}
		}
		if(!isValid){
			try {
				socket.close();
				
			} catch (IOException e) {
				HLogger.getInstance(SocketPool.class).error(e);
			}
		}
		
	}
	@Override
	protected void finalize() throws Throwable{
		for(Map.Entry<SocketKey,Queue<SocketInfo>> entry:socketCache.entrySet()){
			Queue<SocketInfo> socketInfos=entry.getValue();
			for(SocketInfo socketInfo:socketInfos){
				try{
					socketInfo.getSocket().close();
				}catch(Exception e){
					HLogger.getInstance(SocketPool.class).error(e);
				}
			}
		}
	}
	public int getMaxSocketPoolSize() {
		return maxSocketPoolSize;
	}
	public void setMaxSocketPoolSize(int maxSocketPoolSize) {
		this.maxSocketPoolSize = maxSocketPoolSize;
	}
	public int getNetworkTimeout() {
		return networkTimeout;
	}
	public void setNetworkTimeout(int networkTimeout) {
		this.networkTimeout = networkTimeout;
	}
	public int getConnectTimeout() {
		return connectTimeout;
	}
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}
	public int getPoolSize() {
		return poolSize;
	}
	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}
}
