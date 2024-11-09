package com.qa.api.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
	private static Properties prop=new Properties();
	
	static {
		try(InputStream input=ConfigManager.class.getClassLoader().getResourceAsStream("./Config/config.properties")){
				if(input!=null) {
					prop.load(input);
				}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String get(String key) {
		return prop.getProperty(key);
	}
	public static void set(String key,String value) {
		 prop.setProperty(key,value);
	}

}
