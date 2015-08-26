package com.here.framework.log;
/**
 * 日志接口
 * @author koujp
 *
 */
public interface IHereLogger {

	void debug(String s);

	void debug(String s, Object obj);

	void debug(String s, Object[] aobj);

	void error(String s);

	void error(String s, Object obj);

	void error(String s, Object[] aobj);

	void error(String s, Throwable t);
	
	void error(Throwable t);

	String getName();

	void info(String s);

	void info(String s, Object obj);

	void info(String s, Object[] aobj);

	void info(String s, Throwable throwable);

	void trace(String s);

	void trace(String s, Object obj);

	void trace(String s, Object[] aobj);

	void trace(String s, Throwable throwable);

	void warn(String s);

	void warn(String s, Object obj);

	void warn(String s, Object[] aobj);

	void warn(String s, Throwable throwable);
}