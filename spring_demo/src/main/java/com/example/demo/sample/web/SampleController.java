package com.example.demo.sample.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.auth.vo.LoginVO;
import com.example.demo.sample.service.SampleService;
import com.example.demo.sample.vo.SampleVO;

@Controller
@RequestMapping("/sample")
public class SampleController {
	
	@Autowired
	SampleService sampleService;
	
	@Autowired
	SchedulerFactoryBean schedulerFactoryBean;
	
	@RequestMapping("/selectSampleList")
	public String selectSampleList() throws Exception {
		
		SampleVO param = new SampleVO();
		List<SampleVO> list = sampleService.selectSampleList(param);
		
		System.out.println("cnt:" + list.size());
		System.out.println("cnt:" + list.get(0).getUserNm());
		
		return "/index";
	}

	@RequestMapping("/selectSample2List")
	public String selectSample2List() throws Exception {
		
		SampleVO param = new SampleVO();
		List<Map<?,?>> list = sampleService.selectSample2List(param);
		
		System.out.println("cnt:" + list.size());
		System.out.println("user_nm:" + list.get(0).get("USER_NM"));
		
		return "/index";
	}

	@RequestMapping("/insertSample")
	public String insertSample() throws Exception {
		
		LoginVO param = new LoginVO();
		param.setUsername("hello");
		param.setPassword("hello");
		
		sampleService.insertSample(param);
		
		
		return "/index";
	}
	
	@RequestMapping("/insertSampleDev")
	public String insertSampleDev() throws Exception {
		
		LoginVO param = new LoginVO();
		param.setUsername("hello");
		param.setPassword("hello");
		
		sampleService.insertSampleDev(param);
		
		
		return "/index";
	}
	
	@RequestMapping("/insertSampleChained")
	public String insertSampleChained() throws Exception {
		
		LoginVO param = new LoginVO();
		param.setUsername("hello");
		param.setPassword("hello");
		sampleService.insertSampleChained(param);
		
		
		
		return "/index";
	}
	
	@RequestMapping("/restTest")
	public String restTest() throws Exception {
		return "/restTest";
	}

	@PostMapping("/formTest")
	public String formTest(@ModelAttribute SampleVO param) throws Exception {
		System.out.println("param:" + param.getNm());
		
		return "/restTest";
	}
	
	@GetMapping("/schdTest")
	public String schdTest() throws Exception {
		System.out.println("isAutoStartup:" + schedulerFactoryBean.isAutoStartup());
		System.out.println("isStarted:" + schedulerFactoryBean.getScheduler().isStarted());
		return "/index";
	}

}
