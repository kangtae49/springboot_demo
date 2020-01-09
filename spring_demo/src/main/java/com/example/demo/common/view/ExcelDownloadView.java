package com.example.demo.common.view;

import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.springframework.web.servlet.view.document.AbstractXlsxStreamingView;

import com.example.demo.common.vo.ExcelInfoVO;

public class ExcelDownloadView extends AbstractXlsxStreamingView implements ResultHandler<Object>{
	ExcelInfoVO [] excelInfoVO;
	ExcelInfoVO excelInfo;
	Workbook workbook;
	Sheet sheet;
	
	public ExcelDownloadView(ExcelInfoVO ... excelInfoVO) {
		this.excelInfoVO = excelInfoVO;
	}
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		this.workbook = workbook;
		
		for(int i=0; i<this.excelInfoVO.length; i++) {
			this.excelInfo = excelInfoVO[i];
			
			if(i==0) { // 
				String filename = excelInfo.getSaveFilename();
				
				String header = request.getHeader("User-Agent");
//				response.reset();
				if (header.contains("MSIE") || header.contains("Trident") || header.contains("Chrome")) {
					filename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
					response.setHeader("Content-Disposition", "attachment;filename=" + filename + ".xlsx;");
				} else {
					filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
					response.setHeader("Content-Disposition", "attachment;filename=\"" + filename + ".xlsx\"");
				}
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Transfer-Encoding", "binary");
				response.setHeader("Set-Cookie", "fileDownload=true; path=/");  // for jquery.fileDownload
			}
			// make sheet
			//Sheet sheet = workbook.createSheet("test");
			
			// get data
			IReady readyProcess = excelInfo.getReadyProcess();
			readyProcess.process(this);
			// make sheet
			//Sheet sheet = workbook.createSheet("test");
		}
		
	}

	@Override
	public void handleResult(ResultContext<?> resultContext) {
		Object obj = resultContext.getResultObject();
		int idxRow = resultContext.getResultCount()-1;
		System.out.println("handleResult:" + idxRow); // 1~
		
		int idxSheet = idxRow / excelInfo.getLimit();
		if ( idxRow % excelInfo.getLimit() == 0) {
			createSheet(idxSheet);
		}
		try {
			addRow(obj, idxRow);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	void createSheet(int idxSheet) {
		String sheetName = excelInfo.getSheetName();
		if(idxSheet > 0) {
			sheetName = String.format("%s_%s", sheetName, (idxSheet+1));
		}
		
		sheetName = WorkbookUtil.createSafeSheetName(sheetName);
		this.sheet = workbook.createSheet(sheetName);
		
		// TODO: title
	}
	void addRow(Object obj, int idxRow) throws Exception{
		IRow rowProcess = excelInfo.getRowProcess();
		Map<String, Object> data = rowProcess.row(obj, idxRow);
		
		Row row = sheet.createRow(idxRow % excelInfo.getLimit());
		
		int idxCol = 0;
		for (Object key : data.keySet()) {
		   Object value = data.get(key);
		   
			Cell cell = row.createCell(idxCol);
			setCellValue(cell, value);
		   ++idxCol;
		}
	}
	
	void setCellValue(Cell cell, Object val) {
		if (val == null) {
			return;
		}
		
		if(val instanceof Integer) {
			cell.setCellValue((Integer)val);
		} else if (val instanceof Short) {
			cell.setCellValue((Short)val);
		} else if (val instanceof Long) {
			cell.setCellValue((Long)val);
		} else if (val instanceof Float) {
			cell.setCellValue((Float)val);
		} else if (val instanceof Double) {
			cell.setCellValue((Double)val);
		} else if (val instanceof Boolean) {
			cell.setCellValue((Boolean)val);
		} else if (val instanceof String) {
			cell.setCellValue((String)val);
		} else if (val instanceof Date) {
			cell.setCellValue((Date)val);
		} else if (val instanceof byte []) {
			cell.setCellValue(new String((byte [])val));
		} else {
			cell.setCellValue((String)val);
		}
		
	}


}
