package com.thirdware.services.configUsrManual;

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
import java.sql.SQLException;

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
import com.thirdware.entity.T_FIELD_HELP_006;
import com.thirdware.entity.T_Module_005;
import com.thirdware.entity.T_Module_MKDA003;
import com.thirdware.repositories.application.ApplicationCustomQry;
import com.thirdware.repositories.application.I_APPLI_DTL_001DAO;
import com.thirdware.repositories.configUsrManual.ConfigUsrManualCustomQry;
import com.thirdware.repositories.configUsrManual.I_ConfigUsrManual_DTL_001DAO;
import com.thirdware.util.CommonUtil;
import com.thirdware.vo.SelectedItem;
import com.thirdware.vo.application.ApplicationVo;
import com.thirdware.vo.configUsrManual.ConfigUsrManualVo;

@Service
public class ConfigUsrManualService {
	@Autowired
	I_ConfigUsrManual_DTL_001DAO configUsrManualDaoService;

	@Autowired
	ConfigUsrManualCustomQry configUsrManualCustomQry;

	HashSet<String> entrydupUpldSet = new HashSet<String>();
	
	public List<String>  getSearchInputModuleSubMaduleValues(String ModuleTypeCode){
		List<String> inputValuesList = new ArrayList<String>();
		try {
			inputValuesList=configUsrManualCustomQry.getSearchInputModuleSubMaduleValues(ModuleTypeCode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inputValuesList;
	}
	
	public List<SelectedItem>  getAllApplicationItmsNo(){
		List<SelectedItem> applItmsNoList = new ArrayList<SelectedItem>();
		try {
			applItmsNoList=configUsrManualCustomQry.getAllApplicationItmsNo();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return applItmsNoList;
	}
	
	public List<String>  getAllApplItmsNoAndDesc(){
		List<String> applItmsNoList = new ArrayList<String>();
		try {
			applItmsNoList=configUsrManualCustomQry.getApplItmsNoAndDescListOfString();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return applItmsNoList;
	}
	
	
	
public List<SelectedItem>  getModuleListForOnChange(ConfigUsrManualVo configUsrManualVo){
		
		List<SelectedItem> moduleList = new ArrayList<SelectedItem>();
		try {
			moduleList=configUsrManualCustomQry.getModuleDetailsForOnChange(configUsrManualVo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return moduleList;
	}
	
	public List<SelectedItem>  getSubModuleScreenNameListForOnChange(String moduleId){
		
		List<SelectedItem> subModuleList = new ArrayList<SelectedItem>();
		try {
			subModuleList=configUsrManualCustomQry.getSubModuleDetailsForOnChange(moduleId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return subModuleList;
	}
	
	public ConfigUsrManualVo  toValidateModuleSubModuleCombination(String screenNameId){
		ConfigUsrManualVo configVoObj=null;
		Map<String, String> fieldHelpSakeyWithScreenCodeSakey =null;
		String fieldHelpSakey="";
		
			fieldHelpSakeyWithScreenCodeSakey =getFieldHelpSakeyWithScreenCodeSakey();
			fieldHelpSakey=fieldHelpSakeyWithScreenCodeSakey.get(screenNameId);
			if(fieldHelpSakey!=null && !fieldHelpSakey.equalsIgnoreCase("")) {
				configVoObj=new ConfigUsrManualVo();
				configVoObj.setErrorMsg("Following Combination Already Available...!");
			}
		
		return configVoObj;
	}
	
	public ConfigUsrManualVo  toValidateModuleSubModuleCombinationForEdit(String screenNameId,int fieldHelpSakeyId){
		ConfigUsrManualVo configVoObj=null;
		Map<String, String> fieldHelpSakeyWithScreenCodeSakey =null;
		String fieldHelpSakey="";
		
			fieldHelpSakeyWithScreenCodeSakey =getFieldHelpSakeyWithScreenCodeSakey();
			fieldHelpSakey=fieldHelpSakeyWithScreenCodeSakey.get(screenNameId);
			if(fieldHelpSakey!=null && !fieldHelpSakey.equalsIgnoreCase("")) {
				if(Integer.parseInt(fieldHelpSakey)!=fieldHelpSakeyId) {
					configVoObj=new ConfigUsrManualVo();
					configVoObj.setErrorMsg("Following Combination Already Available...!");
				}
			}
		return configVoObj;
	}
	
	
	public List<ConfigUsrManualVo> getConfigUsrManualSrchInfo(ConfigUsrManualVo configUsrManualVo) {

		List<ConfigUsrManualVo> configUsrManualDataList = new ArrayList<ConfigUsrManualVo>();
		try {
			if((configUsrManualVo.getSearchModuleList() == null || configUsrManualVo.getSearchModuleList().isEmpty()) &&
					(configUsrManualVo.getSearchSubModuleList() == null || configUsrManualVo.getSearchSubModuleList().isEmpty()) &&
					(configUsrManualVo.getSearchScreenNameList() == null || configUsrManualVo.getSearchScreenNameList().isEmpty()) && 
					(configUsrManualVo.getSearchApplItmsList() == null || configUsrManualVo.getSearchApplItmsList().isEmpty())){
				
				configUsrManualDataList=configUsrManualCustomQry.getConfigUsrManualSearchResult(configUsrManualVo);
			}else{
				configUsrManualDataList=configUsrManualCustomQry.getConfigUsrManualSearchResultBySelectedInput(configUsrManualVo);
			}
	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return configUsrManualDataList;
	}

	
	public String convertListToString(List<String> valueList)
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
	
	
	
	
	
	
	public void saveConfigUsrManual(List<ConfigUsrManualVo> data){

		List<T_FIELD_HELP_006> saveList = new ArrayList<T_FIELD_HELP_006>();
		
		for(ConfigUsrManualVo dataObj : data){
			T_FIELD_HELP_006  fieldHelpBo = new T_FIELD_HELP_006();
			T_Module_005 moduleObj=new T_Module_005();
			
			moduleObj=configUsrManualDaoService.findByModule(Long.valueOf(dataObj.getScreenNameId()));

			fieldHelpBo.setKda005MdulId(moduleObj);
			fieldHelpBo.setKda006FldCode(dataObj.getFieldCode());
			fieldHelpBo.setKda006FldName(dataObj.getFieldName());
			fieldHelpBo.setKda006AudoHelpFlag(dataObj.isAudioHelpCheck()?"Y":"N");
			fieldHelpBo.setKda006ToolTipFlag(dataObj.isToolTipHelpCheck()?"Y":"N");
			fieldHelpBo.setKda006HelpTextDesc(dataObj.getHelpText());
			
			fieldHelpBo.setKda006CreateProcess("Save");
			fieldHelpBo.setKda006CrtUsrCode("TestUser");
			fieldHelpBo.setKda006CreateDate(new Timestamp(System.currentTimeMillis()));
			fieldHelpBo.setKda006LastUpdateProcess("Save");
			fieldHelpBo.setKda006LastUpdateDate(new Timestamp(System.currentTimeMillis()));
			fieldHelpBo.setKda006LastUpdateUser("esipUser");

			saveList.add(fieldHelpBo);
		}

		configUsrManualDaoService.saveAll(saveList);

	}
	
	public void updateConfigUsrManual(List<ConfigUsrManualVo> data){

		for(ConfigUsrManualVo dataObj : data){
			T_FIELD_HELP_006  fieldHelpBo = new T_FIELD_HELP_006();
			T_Module_005 moduleObj=new T_Module_005();
			
			moduleObj=configUsrManualDaoService.findByModule(Long.valueOf(dataObj.getScreenNameId()));

			fieldHelpBo.setKda005MdulId(moduleObj);
			fieldHelpBo.setKda006FieldId(Long.valueOf(dataObj.getConfigSakeyId()));
			fieldHelpBo.setKda006FldCode(dataObj.getFieldCode());
			fieldHelpBo.setKda006FldName(dataObj.getFieldName());
			fieldHelpBo.setKda006AudoHelpFlag(dataObj.isAudioHelpCheck()?"Y":"N");
			fieldHelpBo.setKda006ToolTipFlag(dataObj.isToolTipHelpCheck()?"Y":"N");
			fieldHelpBo.setKda006HelpTextDesc(dataObj.getHelpText());
			
			fieldHelpBo.setKda006LastUpdateProcess("Update");
			fieldHelpBo.setKda006LastUpdateDate(new Timestamp(System.currentTimeMillis()));
			fieldHelpBo.setKda006LastUpdateUser("Test");

			configUsrManualDaoService.updateConfigUsrManualVo(fieldHelpBo);
		}
	}
	
	
	public ConfigUsrManualVo validate(List<ConfigUsrManualVo> configUsrManualList, boolean saveorUpdate) throws SQLException{

		HashSet<String> entrydupSet = new HashSet<String>();	
		String errorMsg = null;
		List<String> errorList = new ArrayList<String>();
		ConfigUsrManualVo dsidVo = new ConfigUsrManualVo();
		
		if(saveorUpdate) {
			for(ConfigUsrManualVo vo : configUsrManualList){
				errorMsg = null;
				if(configUsrManualCustomQry.validateModuleId(vo.getScreenNameId(),vo.getFieldCode())) {
					errorMsg = "{{\"configUsrManual.LineNo.Lable\" | translate}}" + ".: ["+vo.getRowId()+"] "+ "{{\"configUsrManual.applItmsNo.Lable\" | translate}} " + "'"+configUsrManualCustomQry.getApplItmsNoSakeyOrDescUsingMuduleId(vo.getScreenNameId(),"FetchScreenNameForErrorMsg") + "'"+" - '"+vo.getFieldCode()+"'"+ " {{\"configUsrManual.configUsrExist.err\" | translate}}";
				}
				
				/*if(errorMsg==null || errorMsg.equalsIgnoreCase("")) {
					if(configUsrManualCustomQry.validateFieldCodeUnikey(vo.getFieldCode())) {
						errorMsg = "{{\"configUsrManual.LineNo.Lable\" | translate}}" + ".: ["+vo.getRowId()+"] "+ "{{\"configUsrManual.fieldCode.Lable\" | translate}} " + "'"+vo.getFieldCode()+ "'"+ " {{\"configUsrManual.configUsrExist.err\" | translate}}";
					}
				}*/
				if(errorMsg != null)
					errorList.add(errorMsg);
			}
			
			//validateFieldCodeUnikey
		}else {
			for(ConfigUsrManualVo vo : configUsrManualList){
				errorMsg = null;
				if(configUsrManualCustomQry.validateModuleIdForUpdate(vo.getScreenNameId(),vo.getConfigSakeyId(),vo.getFieldCode())) {
					errorMsg = "{{\"configUsrManual.LineNo.Lable\" | translate}}" + ".: ["+vo.getRowId()+"] "+ "{{\"configUsrManual.applItmsNo.Lableg\" | translate}} " + "'"+configUsrManualCustomQry.getApplItmsNoSakeyOrDescUsingMuduleId(vo.getScreenNameId(),"FetchScreenNameForErrorMsg") + "'"+ " {{\"configUsrManual.configUsrExist.err\" | translate}}";
				}
				/*if(errorMsg==null || errorMsg.equalsIgnoreCase("")) {
					if(configUsrManualCustomQry.validateFieldCodeUnikeyForUpdate(vo.getFieldCode(),vo.getConfigSakeyId()) {
						errorMsg = "{{\"configUsrManual.LineNo.Lable\" | translate}}" + ".: ["+vo.getRowId()+"] "+ "{{\"configUsrManual.fieldCode.Lable\" | translate}} " + "'"+vo.getFieldCode()+ "'"+ " {{\"configUsrManual.configUsrExist.err\" | translate}}";
					}
				}*/
				
				//validateFieldCodeUnikeyForUpdate
				if(errorMsg != null)
					errorList.add(errorMsg);
			}
		}

		dsidVo.setErrors(errorList);

		return dsidVo ;
	}
	
	public File dowloadExcel(List<ConfigUsrManualVo> appliVo) {
		// TODO Auto-generated method stub

		String [] headerNames = {"Application ITMSNo", "Module","SubModule","Screen Name","Field Code","Field Name","Audio Help","Tool Tipe Help","Help Text"};
        File downloadFile=downloadConfigUsrManualExportExcell("CONFIGURE USER MANUAL & VIDEOS",headerNames,3,appliVo);         
        return downloadFile;

	}
	
	public static File downloadConfigUsrManualExportExcell(String fileName, String[] headerNames, int column, List<ConfigUsrManualVo> configUsrManual) {
		
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
				for(ConfigUsrManualVo vo : configUsrManual)
				{
					rows = sheet.createRow(row);
					row++;
					
					XSSFCell cell00 = rows.createCell(0);
					cell00.setCellValue(vo.getAppItmsNoDesc()!=null?vo.getAppItmsNoDesc():"");
					XSSFCell cell01 = rows.createCell(1);
					cell01.setCellValue(vo.getModuleDesc()!=null?vo.getModuleDesc():"");
					XSSFCell cell02 = rows.createCell(2);
					cell02.setCellValue(vo.getSubModuleDesc()!=null?vo.getSubModuleDesc():"");
					XSSFCell cell03 = rows.createCell(3);
					cell03.setCellValue(vo.getScreenNameDesc()!=null?vo.getScreenNameDesc():"");
					
					XSSFCell cell04 = rows.createCell(4);
					cell04.setCellValue(vo.getFieldCode()!=null?vo.getFieldCode():"");
					
					XSSFCell cell05 = rows.createCell(5);
					cell05.setCellValue(vo.getFieldName()!=null?vo.getFieldName():"");
					
					XSSFCell cell06 = rows.createCell(6);
					cell06.setCellValue(vo.getAudioHelp()!=null?vo.getAudioHelp():"");
					
					XSSFCell cell07 = rows.createCell(7);
					cell07.setCellValue(vo.getToolTipHelp()!=null?vo.getToolTipHelp():"");
					
					XSSFCell cell08 = rows.createCell(8);
					cell08.setCellValue(vo.getHelpText()!=null?vo.getHelpText():"");
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
	
	public static XSSFSheet setDefaultTextCoulmns(XSSFSheet sheet, XSSFWorkbook workbook,int startCo,int endCol) {
		DataFormat fmt = workbook.createDataFormat();
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setDataFormat(fmt.getFormat("@"));
		for (int i = startCo; i <= endCol; i++) {
			sheet.setDefaultColumnStyle(i, cellStyle);
		}
		return sheet;
	}
	
	public File dowloadTemplate() {
		String [] headerNames = {"Application ITMSNo*", "Module*","SubModule*","Screen Name*","Field Code*","Field Name*","Audio Help*","Tool Tipe Help*","Help Text"};
        File downloadFile=downloadExcelTemplate("CONFIGURE USER MANUAL & VIDEOS",headerNames,2);             
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
	
	
public File fileUpload(MultipartFile file) throws Exception{
		
		

		List<ArrayList<Object>> sheetData = new ArrayList<ArrayList<Object>>();
		int totalRecords = 0;
		int successRecords = 0;
		int failureRecords = 0;
		entrydupUpldSet = new HashSet<>();
		List<String> allItmsNoList = new ArrayList<String>();
		Map<String,List<String>> allModuleSubModuleNo =  getModuleSubModuleCodeList();
		Map<String, String> screenNameCodeWithSakey=getScreenNameCodeWithSakey();
		Map<String, String> fieldHelpSakeyWithScreenCodeSakey =getFieldHelpSakeyWithScreenCodeSakey();
		
		List<ConfigUsrManualVo> configUsrManualRecordList = new ArrayList<ConfigUsrManualVo>();
		List<ConfigUsrManualVo> successRecordList = new ArrayList<ConfigUsrManualVo>();
		List<ConfigUsrManualVo> failedRecordList = new ArrayList<ConfigUsrManualVo>();
		List<ConfigUsrManualVo> insertRecordList = new ArrayList<ConfigUsrManualVo>();
		List<ConfigUsrManualVo> updationRecordList = new ArrayList<ConfigUsrManualVo>();

		List<String> appItmsNoList=new ArrayList<String>();
		List<String> moduleCodeList=new ArrayList<String>();
		List<String> subModuleCodeList=new ArrayList<String>();
		List<String> screenNameList=new ArrayList<String>();
		InputStream fis = null;
		File finalFile = null;
		try {                
			fis = file.getInputStream();				
			boolean isXlsx = file.getOriginalFilename().contains(".xlsx")  ;				
			sheetData = uploadedFile(fis, 9, isXlsx, file.getOriginalFilename(), "ConfigUsrManual Log");
			String [] headerNames = {"Application ITMSNo*", "Module*","SubModule*","Screen Name*","Field Code*","Field Name*","Audio Help*","Tool Tipe Help*","Help Text"};

			for (int i = 1; i <sheetData.size(); i++) {	
				totalRecords++;				
				List<Object> list = sheetData.get(i);
				final String appItmsNo = (list.get(0) != null &&  (list.get(0).toString() != "")) ? list.get(0).toString().trim() : "";
				final String moduleCode = (list.get(1) != null && (list.get(1).toString()  != "")) ? list.get(1).toString().trim() : "";
				final String subModuleCode = (list.get(2) != null && (list.get(2).toString()  != "")) ? list.get(2).toString().trim() : "";
				final String screenNameCode = (list.get(3) != null && (list.get(3).toString()  != "")) ? list.get(3).toString().trim() : "";
				final String fieldCode = (list.get(4) != null && (list.get(4).toString()  != "")) ? list.get(4).toString().trim() : "";
				final String fieldName = (list.get(5) != null && (list.get(5).toString()  != "")) ? list.get(5).toString().trim() : "";
				final String audioHelp = (list.get(6) != null && (list.get(6).toString()  != "")) ? list.get(6).toString().trim() : "";
				final String toolTipHelp = (list.get(7) != null && (list.get(7).toString()  != "")) ? list.get(7).toString().trim() : "";
				final String helpText = (list.get(8) != null && (list.get(8).toString()  != "")) ? list.get(8).toString().trim() : "";

				ConfigUsrManualVo configVo = new ConfigUsrManualVo();		
				configVo.setExcelAppItmsNo(replacingDecimalValue(appItmsNo));
				configVo.setExcelModuleCode(moduleCode);
				configVo.setExcelSubModuleCode(subModuleCode);
				configVo.setExcelScreenNameCode(screenNameCode);
				configVo.setExcelFieldCode(replacingDecimalValue(fieldCode));
				configVo.setExcelFieldName(fieldName);
				configVo.setExcelAudioHelp(audioHelp);
				configVo.setExcelToolTipHelp(toolTipHelp);
				configVo.setExcelHelpText(helpText);
				
				appItmsNoList=new ArrayList<String>();
				moduleCodeList=new ArrayList<String>();
				subModuleCodeList=new ArrayList<String>();
				screenNameList=new ArrayList<String>();
				
				moduleCodeList= allModuleSubModuleNo.get("MD");
				subModuleCodeList= allModuleSubModuleNo.get("SM");
				screenNameList= allModuleSubModuleNo.get("SC");
				appItmsNoList= allModuleSubModuleNo.get("ITMSNO");
			
				String errorMsg = validateUpload(configVo,moduleCodeList,subModuleCodeList,screenNameList,appItmsNoList);
				if(errorMsg.length() == 0){
					successRecords++;
					configVo.setExcelModuleMasterSakey(screenNameCodeWithSakey.get(configVo.getExcelScreenNameCode()));
					successRecordList.add(configVo);
				}else{
					configVo.setErrorMsg(errorMsg);
					failureRecords++;
					failedRecordList.add(configVo);
				}
				configUsrManualRecordList.add(configVo);
			}
			
			for(ConfigUsrManualVo vo :successRecordList){
				
				String fieldHelpSakey=fieldHelpSakeyWithScreenCodeSakey.get(vo.getExcelModuleMasterSakey());
				
				if(fieldHelpSakey!=null && !fieldHelpSakey.equalsIgnoreCase("")) {
					ConfigUsrManualVo updateRows=new ConfigUsrManualVo();
					
					updateRows.setConfigSakeyId(Integer.parseInt(fieldHelpSakey));
					updateRows.setScreenNameId(vo.getExcelModuleMasterSakey());
					updateRows.setFieldCode(vo.getExcelFieldCode());
					updateRows.setFieldName(vo.getExcelFieldName());
					updateRows.setAudioHelpCheck((vo.getExcelAudioHelp()!=null && !vo.getExcelAudioHelp().equalsIgnoreCase("")
							&& vo.getExcelAudioHelp().toUpperCase().equalsIgnoreCase("YES"))?true:false);
					updateRows.setToolTipHelpCheck((vo.getExcelToolTipHelp()!=null && !vo.getExcelToolTipHelp().equalsIgnoreCase("")
							&& vo.getExcelToolTipHelp().toUpperCase().equalsIgnoreCase("YES"))?true:false);
					updateRows.setHelpText(vo.getExcelHelpText());

					updationRecordList.add(updateRows);
				}else {
					ConfigUsrManualVo insertRows=new ConfigUsrManualVo();
					
					insertRows.setScreenNameId(vo.getExcelModuleMasterSakey());
					insertRows.setFieldCode(vo.getExcelFieldCode());
					insertRows.setFieldName(vo.getExcelFieldName());
					insertRows.setAudioHelpCheck((vo.getExcelAudioHelp()!=null && !vo.getExcelAudioHelp().equalsIgnoreCase("")
							&& vo.getExcelAudioHelp().toUpperCase().equalsIgnoreCase("YES"))?true:false);
					insertRows.setToolTipHelpCheck((vo.getExcelToolTipHelp()!=null && !vo.getExcelToolTipHelp().equalsIgnoreCase("")
							&& vo.getExcelToolTipHelp().toUpperCase().equalsIgnoreCase("YES"))?true:false);
					insertRows.setHelpText(vo.getExcelHelpText());
					
					insertRecordList.add(insertRows);
				}
				
				
			}

			if(insertRecordList.size() > 0)
				saveConfigUsrManual(insertRecordList); // save and update records
			if(updationRecordList.size() > 0)

				updateConfigUsrManual(updationRecordList);

			finalFile= logExcelGeneration(configUsrManualRecordList, "TestUser", headerNames, sheetData.size(),  failureRecords, successRecords ,  failedRecordList , insertRecordList , updationRecordList );
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

	public String validateUpload(ConfigUsrManualVo dataVo,List<String> moduleCodeList,List<String> subModuleCodeList,List<String> screenNameList,List<String> appItmsNoList){
		
		String errorMsg = "";

		if(dataVo.getExcelAppItmsNo() == null || dataVo.getExcelAppItmsNo() ==""){
			errorMsg += "Application ITMS No is mandatory";
		}else if (!CommonUtil.isNumeric(dataVo.getExcelAppItmsNo())){
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += " Application ITMS No should be Numeric" ;
		}else if(dataVo.getExcelAppItmsNo().length() > 5){
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += " Application ITMS No should not be greater than 5" ;
		}else if(Integer.parseInt(dataVo.getExcelAppItmsNo())<0){
			errorMsg +=  " Application ITMS No should be positive";
		}else if(!entrydupUpldSet.add(String.valueOf(Integer.parseInt(dataVo.getExcelAppItmsNo())))){
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += dataVo.getExcelAppItmsNo() +" is a duplicate entry" ;
		}
	
		
		if(dataVo.getExcelModuleCode() == null || dataVo.getExcelModuleCode() ==""){
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += " Module Code is mandatory";
		}else if(dataVo.getExcelModuleCode().length() > 5){
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += " Module Code should not be greater than 5" ;
		}
		
		
		if(dataVo.getExcelSubModuleCode() == null || dataVo.getExcelSubModuleCode() ==""){
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += " SubModule Code is mandatory";
		}else if(dataVo.getExcelSubModuleCode().length() > 5){
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += " Sub Module Code should not be greater than 5" ;
		}
		
		
		if(dataVo.getExcelScreenNameCode() == null || dataVo.getExcelScreenNameCode() ==""){
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += " Screen Name Code is mandatory";
		}else if(dataVo.getExcelScreenNameCode().length() > 5){
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += " Screen Name Code should not be greater than 5" ;
		}
		
		if(dataVo.getExcelFieldCode() == null || dataVo.getExcelFieldCode() ==""){
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += " Field Code is mandatory";
		}else if(dataVo.getExcelFieldCode().length() > 5){
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += " Field Code should not be greater than 5" ;
		}
		
		if(dataVo.getExcelFieldName() == null || dataVo.getExcelFieldName() ==""){
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += " Field Name is mandatory";
		}else if(dataVo.getExcelFieldName().length() > 100){
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += " Field Name should not be greater than 100" ;
		}
		
		if(dataVo.getExcelAudioHelp() == null || dataVo.getExcelAudioHelp() ==""){
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += " Audio Help is mandatory";
		}else if(!dataVo.getExcelAudioHelp().toUpperCase().equalsIgnoreCase("YES") && !dataVo.getExcelAudioHelp().toUpperCase().equalsIgnoreCase("NO")){
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += " Audio Help is Only For Yes Or No." ;
		}
		
		if(dataVo.getExcelToolTipHelp() == null || dataVo.getExcelToolTipHelp() ==""){
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += " Tool Tip is mandatory";
		}else if(!dataVo.getExcelToolTipHelp().toUpperCase().equalsIgnoreCase("YES") && !dataVo.getExcelToolTipHelp().toUpperCase().equalsIgnoreCase("NO")){
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += " Tool Tip is Only For Yes Or No." ;
		}
		
		/*if(dataVo.getExcelHelpText() == null || dataVo.getExcelHelpText() ==""){
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += " Help Text is mandatory";
		}else if(dataVo.getExcelHelpText().length() > 1500){
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += " Help Text should not be greater than 1500" ;
		}*/
		
		if(errorMsg==null || errorMsg.equalsIgnoreCase("")) {
			boolean appItmsNoFlag=validateModuleSubModuleCode(dataVo.getExcelAppItmsNo(), appItmsNoList);
			boolean moduleCodeFlag=validateModuleSubModuleCode(dataVo.getExcelModuleCode(), moduleCodeList);
			boolean subModuleCodeFlag=validateModuleSubModuleCode(dataVo.getExcelSubModuleCode(), subModuleCodeList);
			boolean screenNameFlag=validateModuleSubModuleCode(dataVo.getExcelScreenNameCode(), screenNameList);
			if(!appItmsNoFlag) {
				errorMsg += " Application ITMS No is InValid.";
			}
			if(!moduleCodeFlag) {
				errorMsg += " Module Code is InValid.";
			}
			if(!subModuleCodeFlag) {
				if(!errorMsg.equals("")){
					errorMsg  += ",";
				}
				errorMsg += " Sub Module Code is InValid." ;
			}
			if(!screenNameFlag) {
				if(!errorMsg.equals("")){
					errorMsg  += ",";
				}
				errorMsg += " Screen Name Code is InValid." ;
			}
		}
		
	return errorMsg ;
}
	
	public boolean validateModuleSubModuleCode(String code, List<String> codesList) {
		boolean flag=false;
		if(codesList!=null && !codesList.isEmpty()) {
			for(String strObj:codesList) {
				if(code.equalsIgnoreCase(strObj)) {
					flag=true;
				}
			}
		}
		
		return flag;
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


	public HashMap<String, List<String>> getModuleSubModuleCodeList() {
		HashMap<String, List<String>> moduleSubModuleCodeList=new HashMap<String, List<String>>();
		List<T_Module_MKDA003> moduleMasterDaoList = null;
		List<T_APPLICATION_004> applicationMasterDaoList = null;
		
		moduleMasterDaoList= (List<T_Module_MKDA003>) configUsrManualDaoService.findAllOrderBy();
		if(moduleMasterDaoList!=null && !moduleMasterDaoList.isEmpty()) {
			moduleSubModuleCodeList=new HashMap<String, List<String>>();
			List<String> moduleCodeList=new ArrayList<String>();
			List<String> subModuleCodeList=new ArrayList<String>();
			List<String> screenNameList=new ArrayList<String>();
			for(T_Module_MKDA003 moduleObj:moduleMasterDaoList) {
				if("MD".equalsIgnoreCase(moduleObj.getKda005MdulTypeCode())) {
					moduleCodeList.add(moduleObj.getKda005MdulCode());
				}
				if("SM".equalsIgnoreCase(moduleObj.getKda005MdulTypeCode())) {
					subModuleCodeList.add(moduleObj.getKda005MdulCode());
				}
				if("SC".equalsIgnoreCase(moduleObj.getKda005MdulTypeCode())) {
					screenNameList.add(moduleObj.getKda005MdulCode());
				}
			}
			moduleSubModuleCodeList.put("MD", moduleCodeList);
			moduleSubModuleCodeList.put("SM", subModuleCodeList);
			moduleSubModuleCodeList.put("SC", screenNameList);
		}
		
		applicationMasterDaoList= (List<T_APPLICATION_004>) configUsrManualDaoService.findAllApplItmsNoOrderBy();
		if(applicationMasterDaoList!=null && !applicationMasterDaoList.isEmpty()) {
			List<String> applItmsNoList=new ArrayList<String>();
			for(T_APPLICATION_004 appItmsObj:applicationMasterDaoList) {
				applItmsNoList.add(appItmsObj.getKda004AppliITMSNo());
			}
			moduleSubModuleCodeList.put("ITMSNO", applItmsNoList);
		}
	
		return moduleSubModuleCodeList;
	}
	
	public HashMap<String, String> getScreenNameCodeWithSakey() {
		HashMap<String, String> screenNameCodeWithSakey=new HashMap<String, String>();
		List<T_Module_MKDA003> moduleMasterDaoList = null;
		//T_Module_MKDA003
		moduleMasterDaoList= (List<T_Module_MKDA003>) configUsrManualDaoService.findAllOrderBy();
		if(moduleMasterDaoList!=null && !moduleMasterDaoList.isEmpty()) {
			screenNameCodeWithSakey=new HashMap<String, String>();
			
			for(T_Module_MKDA003 moduleObj:moduleMasterDaoList) {
				if("SC".equalsIgnoreCase(moduleObj.getKda005MdulTypeCode())) {
					screenNameCodeWithSakey.put(moduleObj.getKda005MdulCode(), String.valueOf(moduleObj.getKda005MdulId()));
				}
			}
		}
	
		return screenNameCodeWithSakey;
	}
	
	public HashMap<String, String> getFieldHelpSakeyWithScreenCodeSakey() {
		HashMap<String, String> fieldHelpSakeyWithScreenCodeSakey=new HashMap<String, String>();
		List<T_FIELD_HELP_006> fieldHelpDaoList = null;
		
		fieldHelpDaoList= (List<T_FIELD_HELP_006>) configUsrManualDaoService.findAllFieldHelpOrderBy();
		if(fieldHelpDaoList!=null && !fieldHelpDaoList.isEmpty()) {
			for(T_FIELD_HELP_006 moduleObj:fieldHelpDaoList) {
				fieldHelpSakeyWithScreenCodeSakey.put(String.valueOf(moduleObj.getKda005MdulId().getKda005MdulKey()), String.valueOf(moduleObj.getKda006FieldId()));
			}
		}
	
		return fieldHelpSakeyWithScreenCodeSakey;
	}
	
	public File logExcelGeneration(List<ConfigUsrManualVo> configUsrManualVo , String user, String[] headerNames,int totalRecords,
			int failureRecords,int successRecords ,  List<ConfigUsrManualVo> failedRecordList ,List<ConfigUsrManualVo> insertRecordList ,List<ConfigUsrManualVo> updationRecordList) throws Exception 
	{
		File logFile=new File("ConfigUsrManual.xlsx");
		try
		{

			XSSFWorkbook workbook = new XSSFWorkbook();

			final XSSFSheet worksheet1 = workbook.createSheet("Summary");
			final XSSFRow rowReportName = worksheet1.createRow((short) 0);
			final XSSFCell cellReportNameH = rowReportName.createCell(0);
			cellReportNameH.setCellValue("Report Name");
			final XSSFCell cellReportNameV = rowReportName.createCell(1);
			cellReportNameV.setCellValue("Config User Manual Log");
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
			cellTotalNoOfRec.setCellValue("Total No. Of Records ");
			final XSSFCell cellTotNoOfRecordVal= totalNoOfRecords.createCell(1);
			cellTotNoOfRecordVal.setCellValue(String.valueOf(totalRecords-1));
			worksheet1.autoSizeColumn(0);


			final XSSFRow totalNoOfinsertedRecords = worksheet1.createRow((short) 4);
			final XSSFCell celltotalNoOfinsertedRecords = totalNoOfinsertedRecords.createCell(0);
			celltotalNoOfinsertedRecords.setCellValue("No of new records");
			final XSSFCell cellTotNoOfRecordsValinserted = totalNoOfinsertedRecords.createCell(1);
			cellTotNoOfRecordsValinserted.setCellValue(String.valueOf(insertRecordList.size()));
			worksheet1.autoSizeColumn(0);

			final XSSFRow totalNoOfupdatedRecords = worksheet1.createRow((short) 5);
			final XSSFCell celltotalNoOfUpdatedRecords = totalNoOfupdatedRecords.createCell(0);
			celltotalNoOfUpdatedRecords.setCellValue("No of updated records");
			final XSSFCell cellTotNoOfRecordsValUpdate = totalNoOfupdatedRecords.createCell(1);
			cellTotNoOfRecordsValUpdate.setCellValue(String.valueOf(updationRecordList.size()));
			worksheet1.autoSizeColumn(0);

			final XSSFRow totalNoOffailedRecords = worksheet1.createRow((short) 6);
			final XSSFCell celltotalNoOffailedRecords = totalNoOffailedRecords.createCell(0);
			celltotalNoOffailedRecords.setCellValue("No of  failed records");
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

			XSSFCell cellRmks = row0.createCell(2);
			cellRmks.setCellStyle(editableCellStyle);		
			cellRmks.setCellValue("Remarks");	

			XSSFRow rows=null;
			int row=1;
			for(ConfigUsrManualVo vo : failedRecordList)
			{
				rows = sheet.createRow(row);
				row++;

				XSSFCell cell00 = rows.createCell(0);
				cell00.setCellValue(vo.getExcelAppItmsNo()!=null?vo.getExcelAppItmsNo():"");
				XSSFCell cell01 = rows.createCell(1);
				cell01.setCellValue(vo.getExcelModuleCode()!=null?vo.getExcelModuleCode():"");
				XSSFCell cell02 = rows.createCell(2);
				cell02.setCellValue(vo.getExcelSubModuleCode()!=null?vo.getExcelSubModuleCode():"");
				XSSFCell cell03 = rows.createCell(3);
				cell03.setCellValue(vo.getExcelScreenNameCode()!=null?vo.getExcelScreenNameCode():"");
				
				XSSFCell cell04 = rows.createCell(4);
				cell04.setCellValue(vo.getExcelFieldCode()!=null?vo.getExcelFieldCode():"");
				XSSFCell cell05 = rows.createCell(5);
				cell05.setCellValue(vo.getExcelFieldName()!=null?vo.getExcelFieldName():"");
				XSSFCell cell06 = rows.createCell(6);
				cell06.setCellValue(vo.getExcelAudioHelp()!=null?vo.getExcelAudioHelp():"");
				XSSFCell cell07 = rows.createCell(7);
				cell07.setCellValue(vo.getExcelToolTipHelp()!=null?vo.getExcelToolTipHelp():"");
				XSSFCell cell08 = rows.createCell(8);
				cell08.setCellValue(vo.getExcelHelpText()!=null?vo.getExcelHelpText():"");
				
				XSSFCell cell09 = rows.createCell(9);
				cell09.setCellValue(vo.getErrorMsg()!=null?vo.getErrorMsg():"");

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
