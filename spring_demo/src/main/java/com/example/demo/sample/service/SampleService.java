package com.example.demo.sample.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.auth.vo.LoginVO;
import com.example.demo.sample.mapper.SampleMapper;
import com.example.demo.sample.vo.SampleVO;
import com.example.demo.sample_dev.mapper.SampleMapperDev;

@Service
public class SampleService {
	
	@Autowired
	SampleMapper sampleMapper;

	@Autowired
	SampleMapperDev sampleMapperDev;
	
	public void cronTest() throws Exception{
		System.out.println("cronTest");
	}

	public List<SampleVO> selectSampleList(SampleVO param) throws Exception{
		return sampleMapper.selectSampleList(param);
	}
	
	public List<Map<?,?>> selectSample2List(SampleVO param) throws Exception{
		return sampleMapper.selectSample2List(param);
	}
	
	@Transactional(transactionManager="transactionManager", rollbackFor = Exception.class)
	public void insertSample(LoginVO param) throws Exception {
		sampleMapper.insertSample(param);
//		if(true) {
//			throw new Exception("transaction test");
//		}
		sampleMapper.insertSample(param);
	}

	@Transactional(transactionManager="transactionManagerDev", rollbackFor = Exception.class)
	public void insertSampleDev(LoginVO param) throws Exception {
		sampleMapperDev.insertSampleDev(param);
//		if(true) {
//			throw new Exception("transaction test");
//		}
		sampleMapperDev.insertSampleDev(param);
	}

	@Transactional(transactionManager="chainedTransactionManager", rollbackFor = Exception.class)
	public void insertSampleChained(LoginVO param) throws Exception {
		sampleMapperDev.insertSampleDev(param);
//		if(true) {
//			throw new Exception("transaction chained test");
//		}
		sampleMapper.insertSample(param);
	}
	
	
}
