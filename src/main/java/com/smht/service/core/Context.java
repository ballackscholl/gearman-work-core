package com.smht.service.core;

import java.util.HashMap;
import java.util.Map;

public class Context {
	
	
	static Context context = new Context();
	
	private Map<String, Object> core = new HashMap<String, Object>();
	
	private Context(){
		
	}
	
	synchronized public void put(String key, Object value){
		core.put(key, value);
	}
	
	public Object get(String key){
		return core.get(key);
	}
	
	public static Context getInstance(){
		return context;
	}
	
}
