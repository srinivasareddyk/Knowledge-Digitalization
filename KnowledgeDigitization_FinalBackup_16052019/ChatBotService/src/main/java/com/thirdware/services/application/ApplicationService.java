package com.thirdware.services.application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.sql.Date;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.thirdware.entity.T_APPLICATION_004;
import com.thirdware.repositories.application.ApplicationCustomQry;
import com.thirdware.repositories.application.I_APPLI_DTL_001DAO;
import com.thirdware.util.CommonUtil;
import com.thirdware.vo.SelectedItem;
import com.thirdware.vo.application.ApplicationVo;

@Service
public class ApplicationService {
	@Autowired
	I_APPLI_DTL_001DAO appliDaoService;

	@Autowired
	ApplicationCustomQry applicationCustQryService;

	HashSet<String> entrydupUpldSet = new HashSet<String>();
	
	public List<String>  getAllItmsNo(){
		Iterable<T_APPLICATION_004> itmsNoList  = 	appliDaoService.findAll();
		List<String> itmsStringList = new ArrayList<String>();

		if(itmsNoList !=null )
		{
			for(T_APPLICATION_004  appliBo : itmsNoList){
				itmsStringList.add(appliBo.getKda004AppliITMSNo());
			}
		}
		return itmsStringList;
	}
	
	
	public Map<String,String>  getAllItmsNoValidate(){
		Iterable<T_APPLICATION_004> itmsNoList  = 	appliDaoService.findAll();
		Map<String,String> appliMaintenMap = new HashMap<String,String>();
		if(itmsNoList !=null )
		{
			for(T_APPLICATION_004  appliBo : itmsNoList){
				appliMaintenMap.put(appliBo.getKda004AppliITMSNo(),appliBo.getKda004AppliITMSNo());
			}
		}
		return appliMaintenMap;

	}
	
	public List<String> searchApplicationName(String pn) {
		List<String> obj = applicationCustQryService.searchApplicationName(pn);
		return obj;
	}
	
	
	public boolean itmsNoCheck(String itmsNo){

		boolean flag = false;

		T_APPLICATION_004 appliExists = appliDaoService.findBy(itmsNo);
		if(appliExists != null){
			flag = true;
		}

		return flag;
	}
	
	
	public List<ApplicationVo> getApplicationMaintenSrchInfo(ApplicationVo applicationVo)
	{

		/*SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");*/
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		List<ApplicationVo> appliDataList = new ArrayList<ApplicationVo>();
		int  lineNo = 1;
		List<T_APPLICATION_004> applicationMaintenDaoList = null ;



		if((applicationVo.getSearchApplicationName() == null || applicationVo.getSearchApplicationName().equals("")) &&
				(applicationVo.getSearchApplicationAcronym() == null || applicationVo.getSearchApplicationAcronym().equals("")) &&
				(applicationVo.getSearchApplicationDesc() == null || applicationVo.getSearchApplicationDesc().equals("")) &&
				(applicationVo.getSearchItmsId() == null || applicationVo.getSearchItmsId().isEmpty())){
			
			applicationMaintenDaoList= (List<T_APPLICATION_004>) appliDaoService.findAllOrderBy();

		}else{
			applicationVo.setSearchStrItmsNo(getItmsNoList(applicationVo.getSearchItmsId()));// changing list of itms No to string with comma  appended
			
			applicationMaintenDaoList = applicationCustQryService.getApplicationEntity(applicationVo);
		}
		if(applicationMaintenDaoList !=null && ! applicationMaintenDaoList.isEmpty())
		{
			for(T_APPLICATION_004  appliBo : applicationMaintenDaoList){


				ApplicationVo appliVoObj = new ApplicationVo();
				appliVoObj.setRowId(lineNo);
				appliVoObj.setItmsNo(appliBo.getKda004AppliITMSNo());
				appliVoObj.setApplicationName(appliBo.getKda004AppliName());
				appliVoObj.setApplicationAcronym(appliBo.getKda004AppliAcrnmNo());
				appliVoObj.setApplicationDesc(appliBo.getKda004AppliDesc());
				appliVoObj.setCreateDateTime(dateFormat.format(appliBo.getKda004CreateDate()));
				appliVoObj.setLastUpdateDateTime(dateFormat.format(appliBo.getKda004LastUpdateDate()));

				lineNo++;
				appliDataList.add(appliVoObj);

			}
		}
		return appliDataList;
	}
	
	
	public String getItmsNoList (List<String> valueList)
	{
		StringBuilder query = new StringBuilder();
		System.out.println(valueList);
		if (valueList.size() > 1) {
			query.append(" (");
			StringBuilder strB = new StringBuilder();
			for (String val : valueList) {
				strB.append("'" + val + "',");
			}
			strB.deleteCharAt(strB.lastIndexOf(","));
			query.append(strB.toString() + ")");
		} else {
			if (valueList.size() == 0)
				query.append("");
			else{
				for (String value : valueList) {
					query.append(" ('" + value + "')");
				}

			}
		}

		System.out.println(query.toString());

		return query.toString();
	} 
	
	
	
	public ApplicationVo validate(List<ApplicationVo> applicationMaintenanceList, boolean saveorUpdate){

		HashSet<String> entrydupSet = new HashSet<String>();	

		List<String> errorList = new ArrayList<String>();
		ApplicationVo appliVo = new ApplicationVo();
		for(ApplicationVo vo : applicationMaintenanceList){
			String errorMsg = null ;
			if(entrydupSet.add(vo.getItmsNo())){
				if(saveorUpdate ==  true ? !itmsNoCheck(vo.getItmsNo()) : true){

				}else{
					errorMsg = "{{\"appliMain.LineNo.Lable\" | translate}}" + ".: ["+vo.getRowId()+"] "+ "{{\"appliMain.itmsNo.Lable\" | translate}} " + "'"+vo.getItmsNo() + "'"+ " {{\"appliMain.itmsNoExist.err\" | translate}}";
				}

			}else{
				/*if(errorMsg != null){
					errorMsg  = errorMsg + ",";
				}*/
				errorMsg = "{{\"appliMain.LineNo.Lable\" | translate}}" + ".: ["+vo.getRowId()+"] "+ "{{\"appliMain.itmsNo.Lable\" | translate}} " + "'"+vo.getItmsNo() + "'"+ " {{\"appliMain.select.dup.err\" | translate}}";
			}
			
			if(errorMsg != null)
			errorList.add(errorMsg);
		}

		appliVo.setErrors(errorList);

		return appliVo ;
	}
	
	
	public void saveAppliMainten(List<ApplicationVo> data){

		List<T_APPLICATION_004> saveList = new ArrayList<T_APPLICATION_004>();

		for(ApplicationVo dataObj : data){
			T_APPLICATION_004  appliMaintenBo = new T_APPLICATION_004();

			appliMaintenBo.setKda004AppliITMSNo(dataObj.getItmsNo());
			appliMaintenBo.setKda004AppliName(dataObj.getApplicationName());
			appliMaintenBo.setKda004AppliAcrnmNo(dataObj.getApplicationAcronym());
			appliMaintenBo.setKda004AppliDesc(dataObj.getApplicationDesc());
			
			appliMaintenBo.setKda004CreateProcess("Save");
			appliMaintenBo.setKda004CreateUser("TestUser");
			appliMaintenBo.setKda004CreateDate(new Timestamp(System.currentTimeMillis()));
			appliMaintenBo.setKda004LastUpdateProcess("Save");
			appliMaintenBo.setKda004LastUpdateDate(new Timestamp(System.currentTimeMillis()));
			appliMaintenBo.setKda004LastUpdateUser("esipUser");

			saveList.add(appliMaintenBo);
		}

		appliDaoService.saveAll(saveList);

	}
	
	
	public void updateAppliMainten(List<ApplicationVo> data){

		/*List<T_APPLICATION_004> updateList = new ArrayList<T_APPLICATION_004>();*/

		for(ApplicationVo dataObj : data  ){
			T_APPLICATION_004  appliMaintenBo = new T_APPLICATION_004();

			appliMaintenBo.setKda004AppliITMSNo(dataObj.getItmsNo());
			appliMaintenBo.setKda004AppliName(dataObj.getApplicationName());
			appliMaintenBo.setKda004AppliAcrnmNo(dataObj.getApplicationAcronym());
			appliMaintenBo.setKda004AppliDesc(dataObj.getApplicationDesc());
			
			
			appliMaintenBo.setKda004LastUpdateProcess("Save");
			appliMaintenBo.setKda004LastUpdateDate(new Timestamp(System.currentTimeMillis()));
			appliMaintenBo.setKda004LastUpdateUser("esipUser");

			//updateList.add(dsidBo);
			appliDaoService.updateAppliMaintenVo(appliMaintenBo);
		}
	}
	
	
	public File dowloadTemplate() {
		String [] headerNames = {"ITMS No*", "Application Name*","Application Acronym*","Application Description"};
        File downloadFile=downloadExcelTemplate("Application Maitenance Template",headerNames,2);             
     return downloadFile;
	}
	
	
	public static File downloadExcelTemplate(String fileName, String[] headerNames,int column) {
                  
           File templateFile =new File(fileName+".xlsx");            
            try{                    
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
                
                for(int i=0;i<headerNames.length;i++) {
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
                  
             }catch(Exception e) {
                e.printStackTrace();
           }
    
           return templateFile;
    }
	
	public static XSSFSheet setDefaultTextCoulmns(XSSFSheet sheet, XSSFWorkbook workbook,int startCo,int endCol) {
		DataFormat fmt = workbook.createDataFormat();
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setDataFormat(fmt.getFormat("@"));
		for (int i = startCo; i <= endCol; i++) {
			sheet.setDefaultColumnStyle(i, cellStyle);
		}
		return sheet;
	}
	
	
	public File dowloadExcel(List<ApplicationVo> appliVo) {
		// TODO Auto-generated method stub

		String [] headerNames = {"ITMS No", "Application Name","Application Acronym","Application Description"};
        File downloadFile=downloadAppliMaintenExportExcell("Application Maitenance",headerNames,3,appliVo);         
        return downloadFile;

	}
	
	public static File downloadAppliMaintenExportExcell(String fileName, String[] headerNames, int column, List<ApplicationVo> appliVo) {
		
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
				for(ApplicationVo vo : appliVo)
				{
					rows = sheet.createRow(row);
					row++;
					
					XSSFCell cell00 = rows.createCell(0);
					cell00.setCellValue(vo.getItmsNo()!=null?vo.getItmsNo():"");
					XSSFCell cell01 = rows.createCell(1);
					cell01.setCellValue(vo.getApplicationName()!=null?vo.getApplicationName():"");
					XSSFCell cell02 = rows.createCell(2);
					cell02.setCellValue(vo.getApplicationAcronym()!=null?vo.getApplicationAcronym():"");
					XSSFCell cell03 = rows.createCell(3);
					cell03.setCellValue(vo.getApplicationDesc()!=null?vo.getApplicationDesc():"");
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
	
	
public File fileUpload(MultipartFile file) throws Exception{
		
		

		List<ArrayList<Object>> sheetData = new ArrayList<ArrayList<Object>>();
		int totalRecords = 0;
		int successRecords = 0;
		int failureRecords = 0;
		entrydupUpldSet = new HashSet<>();
		List<String> allItmsNoList = new ArrayList<String>();
		Map<String,String> allItmsNo =  getAllItmsNoValidate();
		List<ApplicationVo> appliMaintenRecordList = new ArrayList<ApplicationVo>();
		List<ApplicationVo> successRecordList = new ArrayList<ApplicationVo>();
		List<ApplicationVo> failedRecordList = new ArrayList<ApplicationVo>();
		List<ApplicationVo> insertRecordList = new ArrayList<ApplicationVo>();
		List<ApplicationVo> updationRecordList = new ArrayList<ApplicationVo>();

		InputStream fis = null;
		File finalFile = null;
		try {                
			fis = file.getInputStream();				
			boolean isXlsx = file.getOriginalFilename().contains(".xlsx")  ;				
			sheetData = uploadedFile(fis, 4, isXlsx, file.getOriginalFilename(), "ApplicationMaintenance Log");
			String [] headerNames = {"ITMS No", "Application Name","Application Acronym","Application Description"};

			for (int i = 1; i <sheetData.size(); i++) {	
				totalRecords++;				
				List<Object> list = sheetData.get(i);
				final String itmsNo = (list.get(0) != null &&  (list.get(0).toString() != "")) ? list.get(0).toString().trim() : "";
				final String applicatioName = (list.get(1) != null && (list.get(1).toString()  != "")) ? list.get(1).toString().trim() : "";
				final String applicationAcronm = (list.get(2) != null && (list.get(2).toString()  != "")) ? list.get(2).toString().trim() : "";
				final String applicationDesc = (list.get(3) != null && (list.get(3).toString()  != "")) ? list.get(3).toString().trim() : "";

				ApplicationVo appliVo = new ApplicationVo();		
				appliVo.setExcelItmsNo(replacingDecimalValue(itmsNo));
				appliVo.setExcelAppliName(applicatioName);
				appliVo.setExcelAppliAcronym(applicationAcronm);
				appliVo.setExcelAppliDesc(applicationDesc);
				
				
				Set<String> set = allItmsNo.keySet();
				allItmsNoList = new ArrayList<>(set);
				String errorMsg = validateUpload(appliVo,allItmsNoList);
				if(errorMsg.length() == 0){
					successRecords++;
					appliVo.setExcelItmsNo(String.valueOf((Integer.parseInt(appliVo.getExcelItmsNo()))));
					successRecordList.add(appliVo);
				}else{
					appliVo.setErrorMsg(errorMsg);
					failureRecords++;
					failedRecordList.add(appliVo);
				}
				appliMaintenRecordList.add(appliVo);
			}

			for(ApplicationVo vo :successRecordList){

				if(allItmsNoList.contains(vo.getExcelItmsNo())){
					SelectedItem s = new SelectedItem();
					s.setKey(allItmsNo.get(vo.getExcelItmsNo()));
					
					ApplicationVo appliVoObj=new ApplicationVo();
					appliVoObj.setItmsNo(vo.getExcelItmsNo());
					appliVoObj.setApplicationName(vo.getExcelAppliName());
					appliVoObj.setApplicationAcronym(vo.getExcelAppliAcronym());
					appliVoObj.setApplicationDesc(vo.getExcelAppliDesc());
					
					updationRecordList.add(appliVoObj);
				}else{
					
					ApplicationVo appliVoObj=new ApplicationVo();
					appliVoObj.setItmsNo(vo.getExcelItmsNo());
					appliVoObj.setApplicationName(vo.getExcelAppliName());
					appliVoObj.setApplicationAcronym(vo.getExcelAppliAcronym());
					appliVoObj.setApplicationDesc(vo.getExcelAppliDesc());
					
					insertRecordList.add(appliVoObj);
				}
			}

			if(insertRecordList.size() > 0)
				saveAppliMainten(insertRecordList); // save and update records
			if(updationRecordList.size() > 0)

				updateAppliMainten(updationRecordList);

			finalFile= logExcelGeneration(appliMaintenRecordList, "TESTUSER", headerNames, sheetData.size(),  failureRecords, successRecords ,  failedRecordList , insertRecordList , updationRecordList );
			System.out.println(successRecordList);
			System.out.println(failedRecordList);
			
			return finalFile;
		} catch (final IOException e) {
			e.printStackTrace();
		} 
		catch (Exception e) {
			e.printStackTrace();
			//throw new Exception("FAIL! Maybe You had uploaded the file before or the file's size > 500KB");
		}finally {
			if (fis != null) 
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return finalFile;
	}

public List<ArrayList<Object>> uploadedFile(InputStream fis, int lastColumn, boolean isXlsx, String fileN, String param) throws IOException
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

		for(int cellCounter = 0; cellCounter < lastColumn; cellCounter++)
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

public static String replacingDecimalValue(String str)
{

	if(str!=null)
	{
		String value=String.valueOf(str);
		if(value.contains("."))
		{
			StringBuffer sb=new StringBuffer(value);
			sb.delete(sb.lastIndexOf("."), sb.length());
			value=sb.toString();        
		}
		return value;
	}
	return null;
}

public String validateUpload(ApplicationVo dataVo,List<String> allItmsNoList){


	String errorMsg = "";

	if(dataVo.getExcelItmsNo() == null || dataVo.getExcelItmsNo() ==""){
		errorMsg += "ITMS No. is mandatory.";
	}else if (!CommonUtil.isNumeric(dataVo.getExcelItmsNo())){
		if(!errorMsg.equals("")){
			errorMsg  += ",";
		}
		errorMsg += " ITMS No. should be Numeric." ;
	}else if(dataVo.getExcelItmsNo().length() != 5){
		if(!errorMsg.equals("")){
			errorMsg  += ",";
		}
		//errorMsg += " ITMS No should not be greater than 5" ;
		errorMsg+=" ITMS No. length should be equal to 5.";
	}else if(Integer.parseInt(dataVo.getExcelItmsNo())<0){
		errorMsg +=  "ITMS No. should be positive.";
	}else if(!entrydupUpldSet.add(String.valueOf(Integer.parseInt(dataVo.getExcelItmsNo())))){
		if(!errorMsg.equals("")){
			errorMsg  += ",";
		}
		errorMsg += dataVo.getExcelItmsNo() +" is a duplicate entry." ;
	}

	
	if(dataVo.getExcelAppliName() == null || dataVo.getExcelAppliName() ==""){
		if(!errorMsg.equals("")){
			errorMsg  += ",";
		}
		errorMsg += "Application Name is mandatory.";
	}else if(dataVo.getExcelAppliName().length() > 100){
		if(!errorMsg.equals("")){
			errorMsg  += ",";
		}
		errorMsg += "Application Name should not be greater than 100." ;
	}
	
	
	if(dataVo.getExcelAppliAcronym() == null || dataVo.getExcelAppliAcronym() ==""){
		if(!errorMsg.equals("")){
			errorMsg  += ",";
		}
		errorMsg += "Application Acronym is mandatory.";
	}else if(dataVo.getExcelAppliAcronym().length() > 5){
		if(!errorMsg.equals("")){
			errorMsg  += ",";
		}
		errorMsg += "Application Acronym should not be greater than 5." ;
	}else if (!CommonUtil.isalpha(dataVo.getExcelAppliAcronym())) {
		if(!errorMsg.equals("")){
			errorMsg  += ",";
		}
		errorMsg += "Application Acronym should contain only Alphabets." ;
	}
	
	
	if(dataVo.getExcelAppliDesc() == null || dataVo.getExcelAppliDesc() ==""){
		if(!errorMsg.equals("")){
			errorMsg  += ",";
		}
		errorMsg += "Application Description is mandatory.";
	}else if(dataVo.getExcelAppliDesc().length() > 200){
		if(!errorMsg.equals("")){
			errorMsg  += ",";
		}
		errorMsg += "Application Description should not be greater than 200." ;
	}
	

	return errorMsg ;
}

public File logExcelGeneration(List<ApplicationVo> appliMaintenanceVo , String user, String[] headerNames,int totalRecords,
		int failureRecords,int successRecords ,  List<ApplicationVo> failedRecordList ,List<ApplicationVo> insertRecordList ,List<ApplicationVo> updationRecordList) throws Exception 
{
	File logFile=new File("ApplicationMaintenace.xlsx");
	try
	{

		XSSFWorkbook workbook = new XSSFWorkbook();

		final XSSFSheet worksheet1 = workbook.createSheet("Summary");
		final XSSFRow rowReportName = worksheet1.createRow((short) 0);
		final XSSFCell cellReportNameH = rowReportName.createCell(0);
		cellReportNameH.setCellValue("Report Name");
		final XSSFCell cellReportNameV = rowReportName.createCell(1);
		cellReportNameV.setCellValue("Application Maintenance Log");
		worksheet1.autoSizeColumn(0);
		DateFormat dateTimeformat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
		String currentDate =  dateTimeformat.format(new Date(System.currentTimeMillis()));

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
		final XSSFCell cellTotNoOfRecordVal= totalNoOfRecords.createCell(1);
		cellTotNoOfRecordVal.setCellValue(String.valueOf(totalRecords-1));
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

		sheet=setDefaultTextCoulmns(sheet,workbook,0,1);


		CellStyle editableCellStyle = workbook.createCellStyle();
		editableCellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		editableCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		editableCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		editableCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		editableCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		editableCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		editableCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);

		XSSFRow row0 = sheet.createRow(0);
		
		/*XSSFRow row01 = sheet.createRow(1);*/

		for(int i=0;i<headerNames.length;i++)
		{
			XSSFCell cell00 = row0.createCell(i);
			cell00.setCellStyle(editableCellStyle);		
			cell00.setCellValue(headerNames[i]);	
		}

		XSSFCell cellRmks = row0.createCell(4);
		cellRmks.setCellStyle(editableCellStyle);		
		cellRmks.setCellValue("Remarks");	

		XSSFRow rows=null;
		int row=1;
		for(ApplicationVo vo : failedRecordList  )
		{
			rows = sheet.createRow(row);
			row++;

			XSSFCell cell00 = rows.createCell(0);
			cell00.setCellValue(vo.getExcelItmsNo()!=null?vo.getExcelItmsNo():"");
			XSSFCell cell01 = rows.createCell(1);
			cell01.setCellValue(vo.getExcelAppliName()!=null?vo.getExcelAppliName():"");
			XSSFCell cell02 = rows.createCell(2);
			cell02.setCellValue(vo.getExcelAppliAcronym()!=null?vo.getExcelAppliAcronym():"");
			XSSFCell cell03 = rows.createCell(3);
			cell03.setCellValue(vo.getExcelAppliDesc()!=null?vo.getExcelAppliDesc():"");
			XSSFCell cell04 = rows.createCell(4);
			cell04.setCellValue(vo.getErrorMsg()!=null?vo.getErrorMsg():"");

		}

		for (int columnPosition = 0; columnPosition < headerNames.length+1; columnPosition++) {
			sheet.autoSizeColumn(columnPosition);
		}
		
		FileOutputStream out = new FileOutputStream(logFile);
		workbook.write(out);
		
		/*OutputStream out = null;
		out = excelGeneration(getResourceMessage("label.massSpec.xls.download.fileName")+"_Log","application/vnd.ms-excel", ".xlsx");*/

		
		return logFile;

	}catch(Exception e)
	{
		e.printStackTrace();
		return null;
	}
}

}
