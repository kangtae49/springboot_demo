package com.example.demo.sample_dev.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.auth.vo.LoginVO;
import com.example.demo.sample.vo.SampleVO;

@Mapper
public interface SampleMapperDev {
	
	public List<SampleVO> selectSampleListDev(SampleVO param) throws Exception;
	
	public void insertSampleDev(LoginVO param) throws Exception;

}
