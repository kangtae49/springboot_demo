package com.example.demo.sample.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.sample.service.SampleService;
import com.example.demo.sample.vo.SampleVO;

@Controller
@RequestMapping("/sample")
public class SampleController {
	
	@Autowired
	SampleService sampleService;
	
	@RequestMapping("/selectSampleList")
	public String selectSampleList() throws Exception {
		
		SampleVO param = new SampleVO();
		List<SampleVO> list = sampleService.selectSampleList(param);
		
		System.out.println("cnt:" + list.size());
		
		return "/sampleList";
	}


}
