package com.thirdware.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import com.thirdware.entity.T_Appl_Dtl_004;
import com.thirdware.entity.T_Module_005;
import com.thirdware.repositories.IAppDtlDAO;
import com.thirdware.repositories.IModuleDAO;
import com.thirdware.util.CommonUtil;
import com.thirdware.vo.module.ModuleVO;
import com.thirdware.vo.SelectedItem;

@Service
public class ConfigModuleService {
	
	
	@Autowired
	IAppDtlDAO appdtldao;

	@Autowired
	IModuleDAO moduledao;
	
	public List<String> getItmsNoWithAcrnym(){
		List<String> itmsList = new ArrayList<String>();
		Iterable<T_Appl_Dtl_004> entitylist = appdtldao.findAll();

		for (T_Appl_Dtl_004 app : entitylist) {
			itmsList.add(String.valueOf(app.getKda004ApplItmsNum()) + "-" + app.getKda004ApplAcrnymCode());
		}
		return itmsList;
	}
	
	public List<String> getModule(List<String> list) {
		List<String> modList = new ArrayList<String>();
		for(String itms:list) {
			List<T_Module_005> modules = moduledao
					.findAllModules(appdtldao.findBydat001ApplItmsNum(Integer.valueOf(itms.split("-")[0])));
			for (T_Module_005 module : modules) {
				modList.add(module.getKda005MdulName());
			}
		}
		

		

		return modList;
	}
	
	public List<SelectedItem> getItmsNoWithAppName() {
		List<SelectedItem> itmsList = new ArrayList<SelectedItem>();
		Iterable<T_Appl_Dtl_004> entitylist = appdtldao.findAll();
		if (entitylist != null) {
			SelectedItem firstitem = new SelectedItem();
			firstitem.setKey(null);
			firstitem.setValue("Please Select");
			itmsList.add(firstitem);
			for (T_Appl_Dtl_004 app : entitylist) {
				SelectedItem item = new SelectedItem();
				item.setKey(String.valueOf(app.getKda004ApplItmsNum()));
				item.setValue(String.valueOf(app.getKda004ApplItmsNum()) + "-" + app.getKda004ApplAcrnymCode());
				itmsList.add(item);
			}
		}

		return itmsList;
	}
	
	public List<String> getModuleCode(List<String> list) {
		List<String> modList = new ArrayList<String>();
		for(String itms:list) {
			List<T_Module_005> modules = moduledao
					.findAllModules(appdtldao.findBydat001ApplItmsNum(Integer.valueOf(itms.split("-")[0])));
			for (T_Module_005 module : modules) {
				modList.add(module.getKda005ModuleCode());
			}
		}
		

		

		return modList;
	}
	
	public List<String> getModuleCodeName(List<String> list) {
		List<String> modList = new ArrayList<String>();
		for(String itms:list) {
			List<T_Module_005> modules = moduledao
					.findAllModules(appdtldao.findBydat001ApplItmsNum(Integer.valueOf(itms.split("-")[0])));
			for (T_Module_005 module : modules) {
				modList.add(module.getKda005ModuleCode()+"-"+module.getKda005MdulName());
			}
		}
		

		

		return modList;
	}
	
	public void processSave(List<ModuleVO> voList) {
		for (ModuleVO vo : voList) {
			Integer modulecode = Integer.valueOf(vo.getAppItmsNoName().getKey());
			T_Module_005 module = new T_Module_005();
			module.setKda005MdulName(vo.getModule());
			module.setKda005ScrnDesc(null);
			module.setKda005ParntMdulKey(null);
			module.setKda005SeqNumber(1);
			module.setKda005StatusFlag('Y');
			module.setKda005CrtUsrC("DA_USER");
			module.setKda005CrtS(new Timestamp(System.currentTimeMillis()));
			module.setKda005CrtProcC("Save");
			module.setKda005LstUpdtUsrC("DA_USER");
			module.setKda005LstUpdtS(new Timestamp(System.currentTimeMillis()));
			module.setKda005LstUpdtProcC("Save");
			T_Appl_Dtl_004 appl = appdtldao.findBydat001ApplItmsNum(modulecode);
			module.setKda004ApplItmsNum(appl);
			module.setKda005ModuleCode(vo.getModulecode());
			module.setKda005ModuleFlag("MD");
			moduledao.save(module);
		

		}
	}
	
	public void processUpdate(List<ModuleVO> voList) {
		
		for (ModuleVO vo : voList) {
			T_Module_005 module = new T_Module_005();
			module.setKda005MdulName(vo.getModule());
			module.setKda005LstUpdtProcC("Update");
			module.setKda005LstUpdtS(new Timestamp(System.currentTimeMillis()));
			module.setKda005LstUpdtUsrC("DA_USER");
			module.setKda005ParntMdulKey(null);
			module.setKda005ScrnDesc(null);
			module.setKda005MdulKey(vo.getModuleKey());
			module.setKda004ApplItmsNum(appdtldao.findBydat001ApplItmsNum(Integer.valueOf(vo.getAppItmsNoName().getKey())));
			moduledao.updateModule(module);
			 
			
			

			
		}
		
	}
	
	@SuppressWarnings("null")
	public List<ModuleVO> searchForModules(ModuleVO srchvo) {
		List<ModuleVO> searchList = new ArrayList<ModuleVO>();
		List<ModuleVO> templist=new ArrayList<ModuleVO>();
		List<T_Module_005> moduleDaoList=new ArrayList<T_Module_005>();
		
		/*boolean moduleFlag=(!srchvo.getModuleList().isEmpty() &&srchvo.getModuleList().size()>0)?true:false;
		boolean modulecodeflag=(!srchvo.getModuleCodeList().isEmpty() &&srchvo.getModuleCodeList().size()>0)?true:false;*/
		boolean modulecodenameflag=(!srchvo.getModuleCodeNameList().isEmpty() &&srchvo.getModuleCodeNameList().size()>0)?true:false;
		
		if((srchvo.getItmsList().size()==0 && srchvo.getItmsList().isEmpty()) && 
			(srchvo.getModuleList().size()==0 && srchvo.getModuleList().isEmpty()) &&
			(srchvo.getModuleCodeList().size()==0 && srchvo.getModuleCodeList().isEmpty())) {
			moduleDaoList=moduledao.findAllOrderBydat004LstUpdtS();
			
		}
		else {
			if(srchvo.getItmsList().size()>0) {
				for (String itms:srchvo.getItmsList()) {
					moduleDaoList .addAll(moduledao
							.findAllModules(appdtldao.findBydat001ApplItmsNum(Integer.valueOf(itms.split("-")[0]))));
					
				}
			}
			
		}
			 
		templist=processModuleData(moduleDaoList,srchvo);
		if(modulecodenameflag) {
			for(String modulecodename:srchvo.getModuleCodeNameList()) {
				for(ModuleVO vo :templist) {
					if(vo.getModulecode().equalsIgnoreCase(modulecodename.split("-")[0])) {
						searchList.add(vo);
						
					}
				}
				
			}
		}
		else {
			searchList=templist;
		}
		
		
		/*if(modulecodeflag) {
			for(String modulecode:srchvo.getModuleCodeList()) {
				for(ModuleVO vo :templist) {
					if(vo.getModulecode().equalsIgnoreCase(modulecode)) {
						searchList.add(vo);
						
					}
				}
				
			}
		}
		 if(moduleFlag) {
				if(modulecodeflag) {
					for(String module:srchvo.getModuleList()) {
					for(ModuleVO vo :searchList) {
						if(vo.getModule().equalsIgnoreCase(module)) {
							searchList.add(vo);
							
						}
					}
				}
				}
				
					for(String module:srchvo.getModuleList()) {
						for(ModuleVO vo :templist) {
							if(vo.getModule().equalsIgnoreCase(module)) {
								searchList.add(vo);
								
							}
						}
						
					}
				
				
			}
		
		if(!moduleFlag&&!modulecodeflag) {
			searchList=templist;
		}*/

		return searchList;

	}
	public List<ModuleVO> processModuleData(List<T_Module_005> moduleDaoList,ModuleVO srchvo) {
		List<ModuleVO> list=new ArrayList<ModuleVO>();
		int count=0;
		for (T_Module_005 module : moduleDaoList) {
						
						ModuleVO vo = new ModuleVO();

						count = count + 1;

						vo.setModuleKey(module.getKda005MdulKey());
						vo.setModulecode(module.getKda005ModuleCode());
						SelectedItem item = new SelectedItem();
						item.setKey(module.getKda004ApplItmsNum().getKda004ApplItmsNum().toString());
						item.setValue(module.getKda004ApplItmsNum().getKda004ApplItmsNum().toString() + "-"
								+ module.getKda004ApplItmsNum().getKda004ApplAcrnymCode());
						vo.setAppItmsNoName(item);

						vo.setModule(module.getKda005MdulName());
						vo.setCreatedDate(module.getKda005CrtS());
						vo.setLastUpdatedDate(module.getKda005LstUpdtS());
						vo.setId(count);
						list.add(vo);

					}
				

		
		return list;
		
	}
	public ModuleVO validate(List<ModuleVO> moduleList, boolean saveorUpdate){

		HashSet<String> entrydupSet = new HashSet<String>();

		List<String> errorList = new ArrayList<String>();
		ModuleVO VO = new ModuleVO();
		for(ModuleVO vo : moduleList){
			String errorMsg = null ;
			if(entrydupSet.add(vo.getModulecode())){
				if(saveorUpdate ==  true ? !moduleCheck(vo.getModulecode(),vo.getAppItmsNoName()) : true){

				}else{
					errorMsg = "{{\"common.LineNo.Lable\" | translate}}" + ".: ["+vo.getId()+"] "+ "{{\"conifgmodule.modulecode.label\" | translate}} " + "'"+vo.getModulecode() + "'"+ " {{\"common.select.exist.err\" | translate}}";
				}

			}else{
				errorMsg = "{{\"common.LineNo.Lable\" | translate}}" + ".: ["+vo.getId()+"] "+ "{{\"conifgmodule.modulecode.label\" | translate}} " + "'"+vo.getModulecode() + "'"+ " {{\"appliMain.select.dup.err\" | translate}}";
			}
		
			
			if(errorMsg != null)
			errorList.add(errorMsg);
		}

		VO.setErrors(errorList);

		return VO ;
	}
	
	public boolean moduleCheck(String modulecode,SelectedItem appitmsnoname){

		boolean flag = false;
		T_Appl_Dtl_004 app=appdtldao.findBydat001ApplItmsNum(Integer.valueOf(appitmsnoname.getKey()));
		T_Module_005 moduleExists = moduledao.findBydat004ModuleCode(app,modulecode);
		if(moduleExists != null){
			flag = true;
		}

		return flag;
	}
	
	public File dowloadExcel(List<ModuleVO> voList) {
		// TODO Auto-generated method stub

		String[] headerNames = { "ITMS No", "Module Code", "Module", "Created Date", "Last Updated Date" };
		File downloadFile = com.thirdware.utils.ExcelUtils.downloadExportExcellforModule("Module Configuration", headerNames, 5,
				voList);
		return downloadFile;

	}
	
	public File dowloadTemplate() {
		String[] headerNames = { "ITMS No*", "Module Code*", "Module*", };
		File downloadFile = com.thirdware.utils.ExcelUtils.downloadExcelTemplate("Module Maintenance Template",
				headerNames, 3);
		return downloadFile;
	}
	
	public Map<Integer,List<String>> getAllModCode(){
		Map<Integer,List<String>> allModCode=new HashMap<Integer,List<String>>();
		Iterable<T_Module_005> moduleList= moduledao.findAllOrderByApplItmsNum();
		if(moduleList!=null) {
			for(T_Module_005 module:moduleList) {
				List<String> modcode=moduledao.findAllModuleCodeByApplication(module.getKda004ApplItmsNum());
				allModCode.put(module.getKda004ApplItmsNum().getKda004ApplItmsNum(), modcode);
			}
		}
		
		
		return allModCode;
	}
	
public File fileUpload(MultipartFile file) throws Exception{

		List<ArrayList<Object>> sheetData = new ArrayList<ArrayList<Object>>();
		int totalRecords = 0;
		int successRecords = 0;
		int failureRecords = 0;
		//entrydupUpldSet = new HashSet<>();
		Map<Integer,List<String>> allModCode =  getAllModCode();
		List<String> allModNamesList = moduledao.findBydat004ModuleCode();
		List<ModuleVO> totalRecordList = new ArrayList<ModuleVO>();
		List<ModuleVO> successRecordList = new ArrayList<ModuleVO>();
		List<ModuleVO> failedRecordList = new ArrayList<ModuleVO>();
		List<ModuleVO> insertRecordList = new ArrayList<ModuleVO>();
		List<ModuleVO> updationRecordList = new ArrayList<ModuleVO>();

		InputStream fis = null;
		File finalFile = null;
		try {                
			fis = file.getInputStream();				
			boolean isXlsx = file.getOriginalFilename().contains(".xlsx")  ;				
			sheetData = uploadedFile(fis, 3, isXlsx, file.getOriginalFilename(), "Module Log");
			String [] headerNames = {"ITMS No", "Module Code", "Module*"};


			for (int i = 1; i <sheetData.size(); i++) {	
				totalRecords++;				
				List<Object> list = sheetData.get(i);
				final String itms  = (list.get(0) != null &&  (list.get(0).toString() != "")) ? list.get(0).toString().trim() : "";
				final String modulecode = (list.get(1) != null && (list.get(1).toString()  != "")) ? list.get(1).toString().trim() : "";
				final String module = (list.get(2) != null && (list.get(2).toString()  != "")) ? list.get(2).toString().trim() : "";
				

				ModuleVO vo = new ModuleVO();
				vo.setModulecode(modulecode);
				vo.setModule(module);
				
				if(itms.contains("-")) {
					SelectedItem item=new SelectedItem();
					item.setKey(itms.split("-")[0]);
					item.setValue(itms.split("-")[1]);
					vo.setAppItmsNoName(item);
					
				}
				else {
					
					SelectedItem item=new SelectedItem();
					item.setKey(itms);
					item.setValue(null);
					vo.setAppItmsNoName(item);
				}
				
				
				
				String errorMsg = validateUpload(vo);
				if(errorMsg.equals("")||errorMsg.length()==0){
					successRecords++;
					successRecordList.add(vo);
				}else{
					vo.setRemarks(errorMsg);
					failureRecords++;
					failedRecordList.add(vo);
				}
				totalRecordList.add(vo);
			}

			for( ModuleVO vo :successRecordList){
				//
				List<String> modcodeList=allModCode.get(Integer.valueOf(vo.getAppItmsNoName().getKey()));
				if(modcodeList!=null && !modcodeList.isEmpty() && modcodeList.contains(vo.getModulecode()))
				{
					vo.setModuleKey(moduledao.findBydat004ModuleCode(vo.getModulecode()).getKda005MdulKey());
					updationRecordList.add(vo);
				}
				else{
					insertRecordList.add(vo);
				}
				//
				/*if(allModNamesList.contains(vo.getModulecode())){
					vo.setModuleKey(moduledao.findBydat004ModuleCode(vo.getModulecode()).getKda005MdulKey());
					updationRecordList.add(vo);
				}else{
					insertRecordList.add(vo);
				}*/
			}

			if(insertRecordList.size() > 0) {
				processSave(insertRecordList);
				 // save and update records
				
			}
				
			if(updationRecordList.size() > 0)
				processUpdate(updationRecordList);

			finalFile= logExcelGeneration(totalRecordList, "NUSBRA12", headerNames, sheetData.size(),  failureRecords, successRecords ,  failedRecordList , insertRecordList , updationRecordList );
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
	

public File logExcelGeneration(List<ModuleVO> dsidVo , String user, String[] headerNames,int totalRecords,
		int failureRecords,int successRecords ,  List<ModuleVO> failedRecordList ,List<ModuleVO> insertRecordList ,List<ModuleVO> updationRecordList  ) throws Exception 
{
	File logFile=new File("Module.xlsx");
	try
	{

		XSSFWorkbook workbook = new XSSFWorkbook();

		final XSSFSheet worksheet1 = workbook.createSheet("Summary");
		final XSSFRow rowReportName = worksheet1.createRow((short) 0);
		final XSSFCell cellReportNameH = rowReportName.createCell(0);
		cellReportNameH.setCellValue("Report Name");
		final XSSFCell cellReportNameV = rowReportName.createCell(1);
		cellReportNameV.setCellValue("Module Maintenance Log");
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

		XSSFCell cellRmks = row0.createCell(3);
		cellRmks.setCellStyle(editableCellStyle);		
		cellRmks.setCellValue("Remarks");	

		XSSFRow rows=null;
		int row=1;
		for(ModuleVO vo : failedRecordList  )
		{
			rows = sheet.createRow(row);
			row++;

			XSSFCell cell00 = rows.createCell(0);
			cell00.setCellValue(vo.getAppItmsNoName().getKey()!=null?vo.getAppItmsNoName().getKey():"");
			XSSFCell cell01 = rows.createCell(1);
			cell01.setCellValue(vo.getModulecode()!=null?vo.getModulecode():"");
			XSSFCell cell02 = rows.createCell(2);
			cell02.setCellValue(vo.getModule()!=null?vo.getModule():"");
			XSSFCell cell03 = rows.createCell(3);
			cell03.setCellValue(vo.getRemarks()!=null?vo.getRemarks():"");
			
		}

		for (int columnPosition = 0; columnPosition < headerNames.length+1; columnPosition++) {
			sheet.autoSizeColumn(columnPosition);
		}
		
		FileOutputStream out = new FileOutputStream(logFile);
		workbook.write(out);
		
		

		
		return logFile;

	}catch(Exception e)
	{
		e.printStackTrace();
		return null;
	}
}

public String validateUpload(ModuleVO vo)
{
	
	
	String errorMsg="";
	
	if(vo.getAppItmsNoName().getKey()==null||vo.getAppItmsNoName().getKey()==""){
		errorMsg+=("ITMS NO is mandatory");
		
	}else if(!CommonUtil.isNumeric(vo.getAppItmsNoName().getKey())) {
		if(!errorMsg.equals("")){
			errorMsg  += ",";
		}
		errorMsg += " ITMS No. should be Numeric" ;
	}
	else if(vo.getAppItmsNoName().getKey().length()>5) {
		if(!errorMsg.equals("")){
			errorMsg  += ",";
		}
		errorMsg += " ITMS No. should not be greater than 5" ;
		
	}else if(Integer.parseInt(vo.getAppItmsNoName().getKey())<0){
		errorMsg +=  "ITMS No. should be positive";
	}else {
		try{
			T_Appl_Dtl_004 app=appdtldao.findBydat001ApplItmsNum(Integer.valueOf(vo.getAppItmsNoName().getKey()));
			if(app==null||app.getKda004ApplItmsNum()==null)
			{
				if(!errorMsg.equals("")){
					errorMsg  += ",";
				}
				errorMsg+="ITMS Number not available";
			}
		}
		catch(Exception e)
		{
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg+="ITMS Number not available";
		}
	}
	
	
	 if(vo.getAppItmsNoName().getValue()!=null && vo.getAppItmsNoName().getValue().length() > 5){
		if(!errorMsg.equals("")){
			errorMsg  += ",";
		}
		errorMsg += "Application Acronym should not be greater than 5" ;
	}else {
		if(vo.getAppItmsNoName().getValue()!=null) {
			try{
				T_Appl_Dtl_004 app=appdtldao.findBydat001ApplItmsNum(Integer.valueOf(vo.getAppItmsNoName().getKey()));
				if(!app.getKda004ApplAcrnymCode().equalsIgnoreCase(vo.getAppItmsNoName().getValue()))
				{
					if(!errorMsg.equals("")){
						errorMsg  += ",";
					}
					errorMsg+="ITMS No. and Acronym does not match";
				}
			}
			catch(Exception e)
			{
				if(!errorMsg.equals("")){
					errorMsg  += ",";
				}
				errorMsg+="ITMS No. and Acronym does not match";
			}
		}
		
	}
	
	 
	 if(vo.getModulecode()==null|| vo.getModulecode()==""){
		 if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += "Module Code is mandatory" ;
			
		}else if(vo.getModulecode().matches("[^a-zA-Z]")) {
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += "Module Code should contain alphabets" ;
			
		}else if(vo.getModulecode().length()>5) {
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += "Module Code should not be greater than 5" ;
		}
	
	if(vo.getModule()==null|| vo.getModule()==""){
		
		 if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += "Module  is mandatory" ;
	}else if(vo.getModule().matches("[^a-zA-Z]")) {
		if(!errorMsg.equals("")){
			errorMsg  += ",";
		}
		errorMsg += "Module  should contain alphabets" ;
		
	}else if(vo.getModule().length()>150) {
		if(!errorMsg.equals("")){
			errorMsg  += ",";
		}
		errorMsg += "Module Code should not be greater than 150 characters" ;
	}
	
	
	
	return errorMsg.toString();
}

public XSSFSheet setDefaultTextCoulmns(XSSFSheet sheet, XSSFWorkbook workbook,int startCo,int endCol) {
	DataFormat fmt = workbook.createDataFormat();
	CellStyle cellStyle = workbook.createCellStyle();
	cellStyle.setDataFormat(fmt.getFormat("@"));
	for (int i = startCo; i <= endCol; i++) {
		sheet.setDefaultColumnStyle(i, cellStyle);
	}
	return sheet;
}

public XSSFSheet setDefaultNumericCoulmns(XSSFSheet sheet, XSSFWorkbook workbook,int startCo,int endCol,String format) {
	//DataFormat fmt = workbook.createDataFormat();
	CellStyle cellStyle = workbook.createCellStyle();
	cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat(format));
	for (int i = startCo; i <= endCol; i++) {
		sheet.setDefaultColumnStyle(i, cellStyle);
	}
	return sheet;
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
	
}
