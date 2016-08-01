package com.cj.tank;
import java.io.IOException;
import java.util.Properties;

//避免每次都调用Propetty
public class PropertyMgr {
	static Properties pros = new Properties();
	static {
		try {
			pros.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public static String getProperty(String key){
		return pros.getProperty(key);
	}
}
