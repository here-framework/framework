package com.here.framework.script.engine;


import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.here.framework.log.HLogger;
/**
 * 脚本引擎
 * @author koujp
 *
 */
public class HScriptEngine {
	private ScriptEngine engine;
	private HScriptEngine(){
		initEngine();
	}
	public HScriptEngine(String script){
		initEngine(script);
	}
	private ScriptEngine initEngine(){
		engine=getScriptEngine();
		return engine;
	}
	private ScriptEngine initEngine(String script){
		initEngine();
		try {
			engine.eval(script);
		} catch (ScriptException e) {
			HLogger.getInstance(HScriptEngine.class).error(e);
		}
		return engine;
	}
	public static HScriptEngine getInstance(){
		return new HScriptEngine();
	}
	public ScriptEngine eval(String script){
		engine=getScriptEngine();
		try {
			 engine.eval(script);
		} catch (ScriptException e) {
			HLogger.getInstance(HScriptEngine.class).error(e);
		}
		return engine;
	}
	public Object getVariable(String name){
		Object obj=engine.get(name);
		
		return obj;
	}
	public String getJson(String name){
		Invocable invoc=(Invocable)engine;
		Object JSON=getVariable("JSON");
		String json=null;
		try {
			json=invoc.invokeMethod(JSON, "stringify",getVariable(name)).toString();
			if("null".equals(json)){
				json=null;
			}
		} catch (Exception e) {
			HLogger.getInstance(HScriptEngine.class).error(e);
		}
		return json;
	};
	private static ScriptEngine getScriptEngine(){
		ScriptEngineManager manager=new ScriptEngineManager();
		ScriptEngine engine=manager.getEngineByExtension("js");
		return engine;
	}
	public <T> T getInterface(Class<T> clasz){
		Invocable invoc=(Invocable)engine;
		return invoc.getInterface(clasz);
	}
}
