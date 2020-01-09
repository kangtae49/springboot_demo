package com.example.demo.common.view;

import org.apache.ibatis.session.ResultHandler;

public interface IReady {
	
	public void process(ResultHandler<?> handler) throws Exception;

}
