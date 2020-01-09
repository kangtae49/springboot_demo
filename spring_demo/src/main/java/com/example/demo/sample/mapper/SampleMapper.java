package com.example.demo.sample.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.ResultHandler;

import com.example.demo.auth.vo.LoginVO;
import com.example.demo.sample.vo.SampleVO;

@Mapper
public interface SampleMapper {
	
	public List<SampleVO> selectSampleList(SampleVO param) throws Exception;
	
	public List<Map<?,?>> selectSample2List(SampleVO param) throws Exception;

	public void insertSample(LoginVO param) throws Exception;
	

	public void excelDownload(SampleVO param, ResultHandler<?> handler) throws Exception;
}
