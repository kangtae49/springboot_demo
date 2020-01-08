package com.example.demo.sample.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.RestAuthenticationEntryPoint;
import com.example.demo.sample.service.SampleService;
import com.example.demo.sample.vo.SampleVO;

@RestController
@RequestMapping("/sample")
public class SampleRestController{
	
	@Autowired
	SampleService sampleService;
	
	@PostMapping("/rest")
	@ResponseBody
	public List<SampleVO> rest(@RequestBody SampleVO param) throws Exception {
		System.out.println("param:" + param.getNm());
		
		List<SampleVO> list = sampleService.selectSampleList(param);
		list.get(0).setNm("<script>alert('hi')</sciprt>");
		
		return list;
	}


}
