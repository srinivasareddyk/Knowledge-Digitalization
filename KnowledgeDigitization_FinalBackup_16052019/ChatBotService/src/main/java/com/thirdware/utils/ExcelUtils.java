package com.thirdware.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.thirdware.vo.ScreenVO;
import com.thirdware.vo.SelectedItem;
import com.thirdware.vo.module.ModuleVO;
import com.thirdware.vo.submodule.SubModuleVO;

public class ExcelUtils {

	public static File downloadExportExcellforScreen(String fileName, String[] headerNames, int column,
			List<ScreenVO> voList) {
		
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		File templateFile = new File(fileName + ".xlsx");
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = null;
			sheet = workbook.createSheet(fileName);

			sheet = setDefaultTextCoulmns(sheet, workbook, 0, column);

			CellStyle editableCellStyle = workbook.createCellStyle();
			editableCellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
			editableCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			editableCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			editableCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			editableCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
			editableCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			editableCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);

			XSSFRow row01 = sheet.createRow(0);

			for (int i = 0; i < headerNames.length; i++) {
				XSSFCell cell00 = row01.createCell(i);
				cell00.setCellStyle(editableCellStyle);
				cell00.setCellValue(headerNames[i]);
			}

			XSSFRow rows = null;
			int row = 1;
			for (ScreenVO vo : voList) {
				rows = sheet.createRow(row);
				row++;

				XSSFCell cell00 = rows.createCell(0);
				cell00.setCellValue(vo.getAppItmsNoName() != null ? (vo.getAppItmsNoName().getValue()) : "");
				XSSFCell cell01 = rows.createCell(1);
				cell01.setCellValue(vo.getModuleCodeName().getKey() != null ? vo.getModuleCodeName().getValue() : "");

				XSSFCell cell02 = rows.createCell(2);
				cell02.setCellValue(vo.getSubmoduleCodeName().getKey() != null ? vo.getSubmoduleCodeName().getValue() : "");
				XSSFCell cell03 = rows.createCell(3);
				cell03.setCellValue(vo.getScreenCode() != null ? vo.getScreenCode() : "");
				XSSFCell cell04 = rows.createCell(4);
				cell04.setCellValue(vo.getScreenName()!= null ? vo.getScreenName() : "");
				XSSFCell cell05 = rows.createCell(5);
				cell05.setCellValue(vo.getScreenPurpose() != null ? vo.getScreenPurpose() : "");
				
				XSSFCell cell06 = rows.createCell(6);
				cell06.setCellValue(vo.getCreatedDate() != null ? simpleDateFormat.format(vo.getCreatedDate()) : "");
				XSSFCell cell07 = rows.createCell(7);
				cell07.setCellValue(vo.getLastUpdatedDate() != null ? simpleDateFormat.format(vo.getLastUpdatedDate()) : "");
				
			}

			OutputStream out = null;
			out = new FileOutputStream(templateFile);
			for (int columnPosition = 0; columnPosition < headerNames.length; columnPosition++) {
				sheet.autoSizeColumn(columnPosition);
			}
			workbook.write(out);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return templateFile;
	}
	public static File downloadExportExcellforModule(String fileName, String[] headerNames, int column,
			List<ModuleVO> voList) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);


		File templateFile = new File(fileName + ".xlsx");
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = null;
			sheet = workbook.createSheet(fileName);

			sheet = setDefaultTextCoulmns(sheet, workbook, 0, column);

			CellStyle editableCellStyle = workbook.createCellStyle();
			editableCellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
			editableCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			editableCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			editableCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			editableCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
			editableCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			editableCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);

			XSSFRow row01 = sheet.createRow(0);

			for (int i = 0; i < headerNames.length; i++) {
				XSSFCell cell00 = row01.createCell(i);
				cell00.setCellStyle(editableCellStyle);
				cell00.setCellValue(headerNames[i]);
			}

			XSSFRow rows = null;
			int row = 1;
			for (ModuleVO vo : voList) {
				rows = sheet.createRow(row);
				row++;

				XSSFCell cell00 = rows.createCell(0);
				cell00.setCellValue(vo.getAppItmsNoName() != null ? (vo.getAppItmsNoName().getValue()) : "");
				XSSFCell cell01 = rows.createCell(1);
				cell01.setCellValue(vo.getModulecode() != null ? vo.getModulecode() : "");

				XSSFCell cell02 = rows.createCell(2);
				cell02.setCellValue(vo.getModule() != null ? vo.getModule() : "");
				XSSFCell cell03 = rows.createCell(3);
				cell03.setCellValue(vo.getCreatedDate() != null ? simpleDateFormat.format(vo.getCreatedDate()): "");
				XSSFCell cell04 = rows.createCell(4);
				cell04.setCellValue(vo.getLastUpdatedDate()!= null ?  simpleDateFormat.format(vo.getLastUpdatedDate()) : "");
				
			}

			OutputStream out = null;
			out = new FileOutputStream(templateFile);
			for (int columnPosition = 0; columnPosition < headerNames.length; columnPosition++) {
				sheet.autoSizeColumn(columnPosition);
			}
			workbook.write(out);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return templateFile;
	}
	
	public static File downloadExportExcellforSubModule(String fileName, String[] headerNames, int column,
			List<SubModuleVO> voList) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);


		File templateFile = new File(fileName + ".xlsx");
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = null;
			sheet = workbook.createSheet(fileName);

			sheet = setDefaultTextCoulmns(sheet, workbook, 0, column);

			CellStyle editableCellStyle = workbook.createCellStyle();
			editableCellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
			editableCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			editableCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			editableCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			editableCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
			editableCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			editableCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);

			XSSFRow row01 = sheet.createRow(0);

			for (int i = 0; i < headerNames.length; i++) {
				XSSFCell cell00 = row01.createCell(i);
				cell00.setCellStyle(editableCellStyle);
				cell00.setCellValue(headerNames[i]);
			}

			XSSFRow rows = null;
			int row = 1;
			for (SubModuleVO vo : voList) {
				rows = sheet.createRow(row);
				row++;

				XSSFCell cell00 = rows.createCell(0);
				cell00.setCellValue(vo.getAppItmsNoName() != null ? (vo.getAppItmsNoName().getValue()) : "");
				XSSFCell cell01 = rows.createCell(1);
				cell01.setCellValue(vo.getModulecodename() != null ? vo.getModulecodename().getValue() : "");

				XSSFCell cell02 = rows.createCell(2);
				cell02.setCellValue(vo.getSubmodulecode() != null ? vo.getSubmodulecode() : "");
				XSSFCell cell03 = rows.createCell(3);
				cell03.setCellValue(vo.getSubmodulename() != null ? vo.getSubmodulename() : "");
				XSSFCell cell04 = rows.createCell(4);
				cell04.setCellValue(vo.getCreatedDate() != null ? simpleDateFormat.format(vo.getCreatedDate()): "");
				XSSFCell cell05 = rows.createCell(5);
				cell05.setCellValue(vo.getLastUpdatedDate()!= null ?  simpleDateFormat.format(vo.getLastUpdatedDate()) : "");
				
			}

			OutputStream out = null;
			out = new FileOutputStream(templateFile);
			for (int columnPosition = 0; columnPosition < headerNames.length; columnPosition++) {
				sheet.autoSizeColumn(columnPosition);
			}
			workbook.write(out);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return templateFile;
	}
	public static XSSFSheet setDefaultTextCoulmns(XSSFSheet sheet, XSSFWorkbook workbook, int startCo, int endCol) {
		DataFormat fmt = workbook.createDataFormat();
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setDataFormat(fmt.getFormat("@"));
		for (int i = startCo; i <= endCol; i++) {
			sheet.setDefaultColumnStyle(i, cellStyle);
		}
		return sheet;
	}
	public static File downloadExcelTemplate(String fileName, String[] headerNames,int column)
    {
                  
           File templateFile =new File(fileName+".xlsx");            
            try
                  {                    
                        XSSFWorkbook workbook = new XSSFWorkbook();
                        XSSFSheet sheet = null;
                        sheet = workbook.createSheet(fileName); 
                        
                        sheet=setDefaultTextCoulmns(sheet,workbook,0,column);
                        
                        CellStyle editableCellStyle = workbook.createCellStyle();
                         editableCellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
                        editableCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
                        editableCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
                        editableCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
                        editableCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
                        editableCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
                        editableCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
                       
                        
                        
                        XSSFRow row02 = sheet.createRow(0);
                        int cell=0;
                        for(int i=0;i<headerNames.length;i++)
                        {
                               XSSFCell cell11 = row02.createCell(cell);
                               cell11.setCellStyle(editableCellStyle);       
                               cell11.setCellValue(headerNames[i]);
                               cell=cell+1;
                        }
                        
                        
                        
                        OutputStream out = null;
                        out =new FileOutputStream(templateFile);
                        for (int columnPosition = 0; columnPosition < headerNames.length; columnPosition++) {
                               sheet.autoSizeColumn(columnPosition);
                        }
                        workbook.write(out);
                  
                  }catch(Exception e)
                  {
                        e.printStackTrace();
                  }
    
           return templateFile;
    }
}
