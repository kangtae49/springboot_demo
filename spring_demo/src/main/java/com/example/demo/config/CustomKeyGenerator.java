package com.example.demo.config;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Qualifier("customKeyGenerator")
public class CustomKeyGenerator implements KeyGenerator {
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Override
	public Object generate(Object target, Method method, Object... params) {
		List<String> jParams = new ArrayList<>();
		
		for(int i=0; i<params.length; i++) {
			String jStr = "";
			try {
				jStr = objectMapper.writeValueAsString(params[i]);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			jParams.add(jStr);
		}
		
		String key = target.getClass().getSimpleName() + "_" + method.getName() + "_"
//				+ StringUtils.arrayToDelimitedString(params, "_");
				+ String.join("_", jParams);
		return key;
	}

}
