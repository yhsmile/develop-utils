package com.zhaokewen.develop.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.eval.ErrorEval;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * 
* Filename: ExcelUtils.java  
* Description: EXCEL 处理工具类
* Copyright:Copyright (c)2016
* Company:  ZITO 
* @author:  yang_hui
* @version: 1.0  
* @Create:  2016年8月2日  
* Modification History:  
* Date								Author			Version
* ------------------------------------------------------------------  
* 2016年8月2日 上午11:01:33				yang_hui  	1.0
 */
public class ExcelUtils {
	
	public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
	public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
	public static final String EMPTY = "";
	public static final String POINT = ".";
	public static final String NOT_EXCEL_FILE = " : Not the Excel file!";
	private static HSSFWorkbook hssfWorkbook;
	private static XSSFWorkbook xssfWorkbook;

	public static List<?> readExcel(String path){
		if(null == path || EMPTY.equals(path)){
			return null;
		}
		String postfix = getPostfix(path);
		if(!EMPTY.equals(postfix)){
			if(OFFICE_EXCEL_2003_POSTFIX.equals(postfix)){
				return readXls(path);
			}else if(OFFICE_EXCEL_2010_POSTFIX.equals(postfix)){
				return readXlsx(path);
			}
		}else{
			System.out.println(path + NOT_EXCEL_FILE);
		}
		
		
		try {
			InputStream is = ExcelUtils.class.getClassLoader().getResourceAsStream("newDai1.xls");
			Workbook workBook = Workbook.getWorkbook(is); //读取EXCEL表格
			Sheet[] sheets = workBook.getSheets(); // Excel的页数
			for(int i=0;i<sheets.length;i++){
				Sheet sheet = sheets[i];
				String sheetName = sheet.getName(); //每个sheet的名字
				System.out.println("每个Sheet的名字：" + sheetName);
				
				
				
			}
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static List<?> readXls(String path) {
		System.out.println(" 读取2003版本的excel文件");
		try {
			InputStream is = new FileInputStream(path);
			hssfWorkbook = new HSSFWorkbook(is);
			//Read the Sheet
			for(int i=0;i<hssfWorkbook.getNumberOfSheets();i++){
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(i);
				if(null == hssfSheet){
					continue;
				}
				
				// Read the Row
				for(int k=1;k<=hssfSheet.getLastRowNum();k++){
					HSSFRow hssfRow = hssfSheet.getRow(k);
					if(null != hssfRow){
						HSSFCell cell = hssfRow.getCell(0);
						String v = getValue(cell);
						System.out.println(v);
					}
				}
				
			}
			
		} catch (Exception e) {
		}
		
		return null;
	}

	private static List<?> readXlsx(String path) {
		System.out.println(" 读取2007版本的excel文件");
		try {
			InputStream is = new FileInputStream(path);
			xssfWorkbook = new XSSFWorkbook(is);
			// Read the Sheet
			System.out.println("sheet数：" + xssfWorkbook.getNumberOfSheets());
			for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				// Read the Row
				System.out.println("行数：" + xssfSheet.getLastRowNum());
				for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
					System.out.println(rowNum);
					XSSFRow xssfRow = xssfSheet.getRow(rowNum);
					if (xssfRow != null) {
						for(int n=0;n<16;n++){
							XSSFCell no = xssfRow.getCell(n);
							String v = getValue(no);
							System.out.println(v);
						}
					}
				}
			}

		} catch (Exception e) {
		}
		return null;
	}
	
	private static String getValue(XSSFCell xssfRow) {
		switch (xssfRow.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			return EMPTY;
		case Cell.CELL_TYPE_BOOLEAN:
			return xssfRow.getBooleanCellValue()?"TRUE":"FALSE";
		case Cell.CELL_TYPE_ERROR:
			return ErrorEval.getText(xssfRow.getErrorCellValue());
		case Cell.CELL_TYPE_FORMULA:
			return xssfRow.getCellFormula();
		case Cell.CELL_TYPE_NUMERIC:
			if(DateUtil.isCellDateFormatted(xssfRow)){
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				return sdf.format(xssfRow.getDateCellValue());
			}
			return xssfRow.getNumericCellValue() + "";
		case Cell.CELL_TYPE_STRING:
			return xssfRow.getStringCellValue();
		default:
			return EMPTY;
		}
        /*if (xssfRow.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
             return String.valueOf(xssfRow.getBooleanCellValue());
         } else if (xssfRow.getCellType() == Cell.CELL_TYPE_NUMERIC) {
             return String.valueOf(xssfRow.getNumericCellValue());
         } else {
             return String.valueOf(xssfRow.getStringCellValue());
         }*/
	}
	
    private static String getValue(HSSFCell hssfCell) {
    	switch (hssfCell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			return EMPTY;
		case Cell.CELL_TYPE_BOOLEAN:
			return hssfCell.getBooleanCellValue()?"TRUE":"FALSE";
		case Cell.CELL_TYPE_ERROR:
			return ErrorEval.getText(hssfCell.getErrorCellValue());
		case Cell.CELL_TYPE_FORMULA:
			return hssfCell.getCellFormula();
		case Cell.CELL_TYPE_NUMERIC:
			if(DateUtil.isCellDateFormatted(hssfCell)){
				DateFormat sdf = new SimpleDateFormat("");
				return sdf.format(hssfCell.getDateCellValue());
			}
			return hssfCell.getNumericCellValue() + "";
		case Cell.CELL_TYPE_STRING:
			return hssfCell.getStringCellValue();
		default:
			return EMPTY;
		}
    	
//        if (hssfCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
//             return String.valueOf(hssfCell.getBooleanCellValue());
//         } else if (hssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//             return String.valueOf(hssfCell.getNumericCellValue());
//         } else {
//         return String.valueOf(hssfCell.getStringCellValue());
//         }
     }

	public static String getPostfix(String path) {
		if (null == path || EMPTY.equals(path.trim())) {
			return EMPTY;
		}
		if (path.contains(POINT)) {
			return path.substring(path.lastIndexOf(POINT) + 1, path.length());
		}
		return EMPTY;
	}
	
	
	public static void main(String[] args) {
		String path = "D:\\newDai.xlsx";
		readExcel(path);
	}
}
