package com.example.demo.common.vo;

import com.example.demo.common.view.IReady;
import com.example.demo.common.view.IRow;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ExcelInfoVO {
	String saveFilename;
	String sheetName;
	String title;
	//int limit = 1_000_000;
	int limit = 10;
	
	IReady readyProcess;
	IRow rowProcess;
	
}
