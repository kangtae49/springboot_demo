package com.example.demo.sample.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.sample.vo.SampleVO;

@Mapper
public interface SampleMapper {
	
	public List<SampleVO> selectSampleList(SampleVO param) throws Exception;
}
