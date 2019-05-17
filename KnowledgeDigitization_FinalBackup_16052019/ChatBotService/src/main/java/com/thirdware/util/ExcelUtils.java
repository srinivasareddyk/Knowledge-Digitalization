package com.thirdware.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
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

import com.thirdware.vo.SfhVO;
import com.thirdware.vo.user.AddRoleDataTableVo;
import com.thirdware.vo.user.MapRoleDataTableVo;


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
	
	public static XSSFSheet setDefaultNumericCoulmns(XSSFSheet sheet, XSSFWorkbook workbook,int startCo,int endCol,String format) {
		//DataFormat fmt = workbook.createDataFormat();
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat(format));
		for (int i = startCo; i <= endCol; i++) {
			sheet.setDefaultColumnStyle(i, cellStyle);
		}
		return sheet;
	}
	
	
	 public static List<ArrayList<Object>> excelSheetData(InputStream fis, int lastColumn, boolean isXlsx, String fileN, String param) throws IOException
	    {
	          List<ArrayList<Object>> rowData=new ArrayList<ArrayList<Object>>();
	          ArrayList<Object> dataCell=null;
	          
	         
	          XSSFWorkbook myWorkBook = new XSSFWorkbook (fis); 
	          XSSFSheet mySheet = myWorkBook.getSheetAt(0);
	          Iterator<Row> rowIterator = mySheet.iterator();
	          
	          while (rowIterator.hasNext()) 
	          { 
	                Row row = rowIterator.next();
	                dataCell=new ArrayList<Object>();
	                      
	                for(int cellCounter = 0; cellCounter < lastColumn ; cellCounter++)
	                {
	                            Cell cell=row.getCell(cellCounter);
	                            
	                            if(cell!=null)
	                            {
	                                  switch (cell.getCellType()) 
	                            { 
	                                  case Cell.CELL_TYPE_BLANK:
	                                        dataCell.add(null);     
	                                        break;
	                                  case Cell.CELL_TYPE_STRING:
	                                        dataCell.add(!cell.getStringCellValue().trim().equals("")?cell.getStringCellValue().trim():null);
	                                        break; 
	                                  case Cell.CELL_TYPE_NUMERIC: 
	                                        dataCell.add(cell.getNumericCellValue());
	                                        break; 
	                                  
	                            }
	                            }else
	                            {
	                                  dataCell.add(null);     
	                            }
	                    
	                            
	                      }// End Cell Loop
	                            
	                rowData.add(dataCell);
	                //System.out.println();
	          }// End Row Loop

	          return rowData;
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
     
    public static File downloadExportExcelAddRole(String fileName, String[] headerNames, int column, List<AddRoleDataTableVo> addVo) {
        
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
                    for(AddRoleDataTableVo vo : addVo  )
                    {
                           rows = sheet.createRow(row);
                           row++;
                           
                           XSSFCell cell00 = rows.createCell(0);
                           cell00.setCellValue(vo.getRoleCode()!=null?vo.getRoleCode():"");
                           XSSFCell cell01 = rows.createCell(1);
                           cell01.setCellValue(vo.getRoleName()!=null?vo.getRoleName():"");
                           XSSFCell cell02 = rows.createCell(2);
                           cell02.setCellValue(vo.getStatus().getValue()!=null?vo.getStatus().getValue():"");
                           XSSFCell cell03 = rows.createCell(3);
                           cell03.setCellValue(vo.getCreatedDate()!=null?CommonUtil.convertISTtodateFormat(vo.getCreatedDate()).toString():"");
                           XSSFCell cell04 = rows.createCell(4);
                           cell04.setCellValue(vo.getLastUpdatedDate()!=null?CommonUtil.convertISTtodateFormat(vo.getLastUpdatedDate()).toString():"");
                           
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
	
public static File downloadFaultExportExcelMapRole(String fileName, String[] headerNames, int column, List<MapRoleDataTableVo> mapVo) {
    
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
                for(MapRoleDataTableVo vo : mapVo  )
                {
                       rows = sheet.createRow(row);
                       row++;
                       
                       XSSFCell cell00 = rows.createCell(0);
                       cell00.setCellValue(vo.getScreen().getValue()!=null?vo.getScreen().getValue():"");
                       XSSFCell cell01 = rows.createCell(1);
                       String function = "";
                       if((vo.getSelectedFunctionVoList() != null && !vo.getSelectedFunctionVoList().isEmpty())){
                         
                         
                         for(String str:vo.getSelectedFunctionVoList())
                         {
                                function=function+str;
                                function = function + ",";
                         }
                         
                       }
                       cell01.setCellValue(function != null ? function.substring(0,function.length()-1) :"");
                       XSSFCell cell02 = rows.createCell(2);
                       cell02.setCellValue(vo.getRole().getValue()!=null?vo.getRole().getValue():"");
                      XSSFCell cell03 = rows.createCell(3);
                       
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
public static File downloadSFHExportExcell(String fileName, String[] headerNames, int column, List<SfhVO> sfhList) {
	
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
			for(SfhVO vo : sfhList )
			{
				rows = sheet.createRow(row);
				row++;
				XSSFCell cell00 = rows.createCell(0);
				cell00.setCellValue(vo.getItms());
				XSSFCell cell01 = rows.createCell(1);
				cell01.setCellValue(vo.getModule()!=null?vo.getModule():"");
				XSSFCell cell02 = rows.createCell(2);
				cell02.setCellValue(vo.getSubModule()!=null?vo.getSubModule():"");
				XSSFCell cell03 = rows.createCell(3);
				cell03.setCellValue(vo.getSelectedScreen()!=null?vo.getSelectedScreen():"");
				/*
				 * XSSFCell cell04 = rows.createCell(4);
				 * cell04.setCellValue(vo.getStatusSearch()!=null?vo.getStatusSearch():"");
				 */
				XSSFCell cell05 = rows.createCell(4);
				cell05.setCellValue(vo.getHelpDocFileName()!=null&&vo.getHelpDocFileName().length()>2?"Y":"N");
				XSSFCell cell06 = rows.createCell(5);
				cell06.setCellValue(vo.getHelpDocFileName()!=null?vo.getHelpDocFileName():"");
				XSSFCell cell07 = rows.createCell(6);
				cell07.setCellValue(vo.getHelpDocLoc()!=null?vo.getHelpDocLoc():"");
				XSSFCell cell08 = rows.createCell(7);
				cell08.setCellValue(vo.getHelpVideoFileName()!=null&&vo.getHelpVideoFileName().length()>2?"Y":"N");
				XSSFCell cell09 = rows.createCell(8);
				cell09.setCellValue(vo.getHelpVideoFileName()!=null?vo.getHelpVideoFileName():"");
				XSSFCell cell10 = rows.createCell(9);
				cell10.setCellValue(vo.getHelpVideoLoc()!=null?vo.getHelpVideoLoc():"");
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
