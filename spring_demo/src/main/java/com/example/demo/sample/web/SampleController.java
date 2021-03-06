package com.example.demo.sample.web;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ResultHandler;
import org.jasypt.encryption.StringEncryptor;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.auth.vo.LoginVO;
import com.example.demo.common.view.ExcelDownloadView;
import com.example.demo.common.view.IReady;
import com.example.demo.common.vo.ExcelInfoVO;
import com.example.demo.sample.service.SampleService;
import com.example.demo.sample.vo.SampleVO;

import schd.ScheduledJob;

@Controller
@RequestMapping("/sample")
public class SampleController {
	
	
	@Autowired
	StringEncryptor stringEncryptor;
	
	@Autowired
	SampleService sampleService;
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	SchedulerFactoryBean schedulerFactoryBean;
	
	@RequestMapping("/selectSampleList")
	public String selectSampleList() throws Exception {
		
		SampleVO param = new SampleVO();
		param.setUserNm("USER1");
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
		String enc = stringEncryptor.encrypt("spring_demo");
		System.out.println("enc:" + enc);
		
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
		TriggerKey triggerKey = new TriggerKey("triggerName", "triggerGroup");
		JobKey jobKey = new JobKey("jobName", "jobGroup");
		
		JobDataMap jobDataMap = new JobDataMap();
		
		Object service = context.getBean("sampleService");
		
		String methodName = "cronTest";
		
		Method method = BeanUtils.findDeclaredMethod(service.getClass(), methodName);
		// SampleVO param = new SampleVO();
		
		jobDataMap.put("service", service);
		jobDataMap.put("method", method);
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		
		Trigger trigger = null;
		if (scheduler.checkExists(triggerKey)) {
			trigger = scheduler.getTrigger(triggerKey);
		} else {
			trigger = TriggerBuilder.newTrigger()
			.withIdentity(triggerKey)
			.withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?"))
			.build()
			;
		}
		
		
		JobDetail jobDetail = JobBuilder.newJob(ScheduledJob.class)
			.withIdentity(jobKey)
			.usingJobData(jobDataMap)
			.build()
			;
		
		schedulerFactoryBean.getScheduler().scheduleJob(jobDetail, trigger);
		
		
		return "/index";
	}
	
	@GetMapping("/cacheTest")
	public String cacheTest() throws Exception {
		SampleVO param = new SampleVO();
		String strDt = sampleService.cacheTest(param);
		System.out.println("strDt:" + strDt);
		return "/index";
	}

	@GetMapping("/cacheClear")
	public String cacheClear() throws Exception {
		sampleService.cacheClear();
		return "/index";
	}

	@GetMapping("/fileDownload")
	public String fileDownload() throws Exception {
		return "/fileDownload";
	}

	@PostMapping("/excelDownload")
	public ModelAndView excelDownload(@ModelAttribute SampleVO param) throws Exception {
//		SampleVO param = new SampleVO();
		ExcelInfoVO excelInfo = new ExcelInfoVO();
		excelInfo.setSaveFilename("엑셀다운로드");
		excelInfo.setSheetName("sheet");
		excelInfo.setReadyProcess((handler) -> {
				sampleService.excelDownload(param, handler);
		});
		excelInfo.setRowProcess((obj, idxRow) ->{
			SampleVO vo = (SampleVO)obj;
			Map<String, Object> map = new LinkedHashMap<>();
			
			map.put("No", (idxRow+1));
			map.put("Val", vo.getIntVal());
			map.put("Str", vo.getStrVal());
			return map;
		});
		
		return new ModelAndView(new ExcelDownloadView(excelInfo));
	}
	
}
