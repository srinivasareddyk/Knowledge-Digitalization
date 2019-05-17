package com.thirdware.utils.dbconfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.thirdware.vo.dbconfig.DBconfigdatatableVo;

public class ExcelUtils {
	
	public static XSSFSheet setDefaultTextCoulmns(XSSFSheet sheet, XSSFWorkbook workbook,int startCo,int endCol) {
		DataFormat fmt = workbook.createDataFormat();
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setDataFormat(fmt.getFormat("@"));
		for (int i = startCo; i <= endCol; i++) {
			sheet.setDefaultColumnStyle(i, cellStyle);
		}
		return sheet;
	} 
	
	 public   static File downloadExcelTemplate(String fileName, String[] headerNames,int column)
     {
                   
            File templateFile =new File(fileName+".xls");            
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
                         

                         XSSFRow row01 = sheet.createRow(0);
                         
                         for(int i=0;i<headerNames.length;i++)
                         {
                                XSSFCell cell00 = row01.createCell(i);
                                cell00.setCellStyle(editableCellStyle);       
                                cell00.setCellValue(headerNames[i]);   
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

	public static File downloadExcel(String fileName, String[] headerNames, int column, List<DBconfigdatatableVo> dbvo) {

		
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
				

				XSSFRow row01 = sheet.createRow(0);
				
				for(int i=0;i<headerNames.length;i++)
				{
					XSSFCell cell00 = row01.createCell(i);
					cell00.setCellStyle(editableCellStyle);		
					cell00.setCellValue(headerNames[i]);	
				}
				
				
				
				XSSFRow rows=null;
				int row=1;
				for(DBconfigdatatableVo vo : dbvo )
				{
					rows = sheet.createRow(row);
					row++;
					//"Line No.", "ITMS No", "Database Name", "Database Type", "HostName", "Port Number",
					//"Driver Name", "Ip Address", "UserName","Password","Created Date & Time", "Last Updated Date & Time"
					DateFormat dateTimeformat = new SimpleDateFormat("dd-MM-yyyy");
					String createDate =  dateTimeformat.format(vo.getCreatedDateAndTime());
					String updateDate =  dateTimeformat.format(vo.getLastUpdatedDateAndTime());
					XSSFCell cell00 = rows.createCell(0);
					cell00.setCellValue(vo.getId()!=null?vo.getId().toString():"");
					XSSFCell cell01 = rows.createCell(1);
					cell01.setCellValue(vo.getItmsNo()!=null?vo.getItmsNo().toString():"");
					XSSFCell cell02 = rows.createCell(2);
					cell02.setCellValue(vo.getApplicationName()!=null?vo.getApplicationName():"");
					XSSFCell cell03 = rows.createCell(3);
					cell03.setCellValue(vo.getApplicationAcronym()!=null?vo.getApplicationAcronym():"");
					XSSFCell cell04 = rows.createCell(4);
					cell04.setCellValue(vo.getDatabaseName()!=null?vo.getDatabaseName():"");
					XSSFCell cell05 = rows.createCell(5);
					cell05.setCellValue(vo.getDatabaseType()!=null?vo.getDatabaseType():"");
					XSSFCell cell06 = rows.createCell(6);
					cell06.setCellValue(vo.getHostName()!=null?vo.getHostName():"");
					XSSFCell cell07 = rows.createCell(7);
					cell07.setCellValue(vo.getPortNumber()!=null?vo.getPortNumber().toString():"");
					XSSFCell cell08 = rows.createCell(8);
					cell08.setCellValue(vo.getDriverName()!=null?vo.getDriverName():"");
					XSSFCell cell09 = rows.createCell(9);
					cell09.setCellValue(vo.getIpAddress()!=null?vo.getIpAddress():"");
					XSSFCell cell10 = rows.createCell(10);
					cell10.setCellValue(vo.getUserName()!=null?vo.getUserName():"");
					XSSFCell cell11 = rows.createCell(11);
					cell11.setCellValue(vo.getPassword()!=null?vo.getPassword():"");
		
					XSSFCell cell12 = rows.createCell(12);
					cell12.setCellValue(vo.getCreatedDateAndTime()!=null?createDate:"");
					XSSFCell cell13 = rows.createCell(13);
					cell13.setCellValue(vo.getLastUpdatedDateAndTime()!=null?updateDate:"");
					
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

	public static File logExcelGeneration(List<DBconfigdatatableVo> totalRecordList, String user,
			String[] headerNames, int totalRecords, int failureRecords, int successRecords,
			List<DBconfigdatatableVo> failedRecordList, List<DBconfigdatatableVo> insertRecordList,
			List<DBconfigdatatableVo> updationRecordList) {

		File logFile = new File("MP.xlsx");
		try {

			XSSFWorkbook workbook = new XSSFWorkbook();

			final XSSFSheet worksheet1 = workbook.createSheet("Summary");
			final XSSFRow rowReportName = worksheet1.createRow((short) 0);
			final XSSFCell cellReportNameH = rowReportName.createCell(0);
			cellReportNameH.setCellValue("Report Name");
			final XSSFCell cellReportNameV = rowReportName.createCell(1);
			cellReportNameV.setCellValue("Database configuration Log");
			worksheet1.autoSizeColumn(0);
			DateFormat dateTimeformat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
			String currentDate = dateTimeformat.format(new Date(System.currentTimeMillis()));

			final XSSFRow rowDate = worksheet1.createRow((short) 1);
			final XSSFCell cellDateH = rowDate.createCell(0);
			cellDateH.setCellValue("Report Generated Date");
			final XSSFCell cellDateV = rowDate.createCell(1);
			cellDateV.setCellValue(currentDate);
			worksheet1.autoSizeColumn(1);
			final XSSFRow rowUserName = worksheet1.createRow((short) 2);
			final XSSFCell cellUserNameH = rowUserName.createCell(0);
			cellUserNameH.setCellValue("Generated By");
			final XSSFCell cellUserNameV = rowUserName.createCell(1);
			cellUserNameV.setCellValue(user);
			worksheet1.autoSizeColumn(2);

			final XSSFRow totalNoOfRecords = worksheet1.createRow((short) 3);
			final XSSFCell cellTotalNoOfRec = totalNoOfRecords.createCell(0);
			cellTotalNoOfRec.setCellValue("Total No. Of Records :");
			final XSSFCell cellTotNoOfRecordVal = totalNoOfRecords.createCell(1);
			cellTotNoOfRecordVal.setCellValue(String.valueOf(totalRecords - 1));
			worksheet1.autoSizeColumn(0);

			final XSSFRow totalNoOfinsertedRecords = worksheet1.createRow((short) 4);
			final XSSFCell celltotalNoOfinsertedRecords = totalNoOfinsertedRecords.createCell(0);
			celltotalNoOfinsertedRecords.setCellValue("Number of new records");
			final XSSFCell cellTotNoOfRecordsValinserted = totalNoOfinsertedRecords.createCell(1);
			cellTotNoOfRecordsValinserted.setCellValue(String.valueOf(insertRecordList.size()));
			worksheet1.autoSizeColumn(0);

			final XSSFRow totalNoOfupdatedRecords = worksheet1.createRow((short) 5);
			final XSSFCell celltotalNoOfUpdatedRecords = totalNoOfupdatedRecords.createCell(0);
			celltotalNoOfUpdatedRecords.setCellValue("Number of updated records");
			final XSSFCell cellTotNoOfRecordsValUpdate = totalNoOfupdatedRecords.createCell(1);
			cellTotNoOfRecordsValUpdate.setCellValue(String.valueOf(updationRecordList.size()));
			worksheet1.autoSizeColumn(0);

			final XSSFRow totalNoOffailedRecords = worksheet1.createRow((short) 6);
			final XSSFCell celltotalNoOffailedRecords = totalNoOffailedRecords.createCell(0);
			celltotalNoOffailedRecords.setCellValue("Number of  failed records");
			final XSSFCell cellTotNoOfRecordsfailed = totalNoOffailedRecords.createCell(1);
			cellTotNoOfRecordsfailed.setCellValue(String.valueOf(failedRecordList.size()));
			worksheet1.autoSizeColumn(0);

			XSSFSheet sheet = null;
			sheet = workbook.createSheet("Error Log");

			sheet = setDefaultTextCoulmns(sheet, workbook, 0, 1);

			CellStyle editableCellStyle = workbook.createCellStyle();
			editableCellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
			editableCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			editableCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			editableCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			editableCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
			editableCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			editableCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);

			XSSFRow row0 = sheet.createRow(0);

			/* XSSFRow row01 = sheet.createRow(1); */

			for (int i = 0; i < headerNames.length; i++) {
				XSSFCell cell00 = row0.createCell(i);
				cell00.setCellStyle(editableCellStyle);
				cell00.setCellValue(headerNames[i]);
			}

			XSSFCell cellRmks = row0.createCell(9);
			cellRmks.setCellStyle(editableCellStyle);
			cellRmks.setCellValue("Remarks");

			XSSFRow rows = null;
			int row = 1;
			for (DBconfigdatatableVo vo : failedRecordList) {
				rows = sheet.createRow(row);
				row++;
				
				XSSFCell cell00 = rows.createCell(0);
				cell00.setCellValue(vo.getItmsNo() != null ? vo.getItmsNo().toString(): "");
				XSSFCell cell01 = rows.createCell(1);
				cell01.setCellValue(vo.getDatabaseType() != null ? vo.getDatabaseType() : "");
				XSSFCell cell02 = rows.createCell(2);
				cell02.setCellValue(vo.getHostName() != null ? vo.getHostName() : "");
				XSSFCell cell03 = rows.createCell(3);
				cell03.setCellValue(vo.getIpAddress() != null ? vo.getIpAddress() : "");
				XSSFCell cell04 = rows.createCell(4);
				cell04.setCellValue(vo.getPortNumber() != null ? vo.getPortNumber().toString() : "");
				XSSFCell cell05 = rows.createCell(5);
				cell05.setCellValue(vo.getDriverName() != null ? vo.getDriverName() : "");
				XSSFCell cell06 = rows.createCell(6);
				cell06.setCellValue(vo.getDatabaseName() != null ? vo.getDatabaseName() : "");
				XSSFCell cell07=rows.createCell(7);
				cell07.setCellValue(vo.getUserName() !=null ? vo.getUserName(): "");
				
				XSSFCell cell08=rows.createCell(8);
				cell08.setCellValue(vo.getPassword() !=null ? vo.getPassword(): "");
				XSSFCell cell09 = rows.createCell(9);
				cell09.setCellValue(vo.getErrorMsg() != null ? vo.getErrorMsg() : "");

			}

			for (int columnPosition = 0; columnPosition < headerNames.length + 1; columnPosition++) {
				sheet.autoSizeColumn(columnPosition);
			}
			

			FileOutputStream out = new FileOutputStream(logFile);
			workbook.write(out);


			return logFile;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}
	 
}