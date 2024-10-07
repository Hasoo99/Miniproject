package com.miniproject.util;

import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.io.Resources;

public class PropertiesTask {
	public static String getPropertiesValue(String resource, String key) throws IOException {
		
		resource = "naverapi.properties";
		
		Properties prop = new Properties(); // 비어 있는 Properties 객체 생성
		
		Reader reader = Resources.getResourceAsReader(resource);
		
		
		prop.load(reader); // reader가 가리키는 파일을 읽어서 prop객체에 key, value를 구분하여 넣어준다.
	
		return (String) prop.get(key);
	}
}
