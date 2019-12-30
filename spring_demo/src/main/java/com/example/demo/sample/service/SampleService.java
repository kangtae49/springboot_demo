package com.example.demo.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.sample.mapper.SampleMapper;
import com.example.demo.sample.vo.SampleVO;

@Service
public class SampleService {
	
	@Autowired
	SampleMapper sampleMapper;
	
	public List<SampleVO> selectSampleList(SampleVO param) throws Exception{
		return sampleMapper.selectSampleList(param);
	}

}
