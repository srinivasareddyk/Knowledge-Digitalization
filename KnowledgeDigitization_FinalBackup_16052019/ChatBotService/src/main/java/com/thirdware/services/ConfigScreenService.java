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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
import org.apache.tomcat.util.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.thirdware.entity.T_Appl_Dtl_004;
import com.thirdware.entity.T_Module_005;
import com.thirdware.repositories.IAppDtlDAO;
import com.thirdware.repositories.IModuleDAO;
import com.thirdware.util.CommonUtil;
import com.thirdware.vo.ScreenVO;
import com.thirdware.vo.SearchVO;
import com.thirdware.vo.SelectedItem;

@Service
public class ConfigScreenService {

	@Autowired
	IAppDtlDAO appdtldao;

	@Autowired
	IModuleDAO moduledao;

	public List<Integer> getAllITMSNo() {
		List<Integer> itmsList = new ArrayList<Integer>();
		Iterable<T_Appl_Dtl_004> entitylist = appdtldao.findAll();

		for (T_Appl_Dtl_004 app : entitylist) {
			itmsList.add(app.getKda004ApplItmsNum());
		}
		return itmsList;
	}

	@SuppressWarnings("null")
	public List<ScreenVO> searchForModules(SearchVO srchvo) {
		List<ScreenVO> searchList = new ArrayList<ScreenVO>();
		List<ScreenVO> templist=new ArrayList<ScreenVO>();
		List<T_Module_005> moduleDaoList=new ArrayList<T_Module_005>();
		
		boolean itmsflag=(srchvo.getItmsList().size()>0 && !srchvo.getItmsList().isEmpty()) ?true:false;
		boolean moduleFlag=(!srchvo.getModuleList().isEmpty() &&srchvo.getModuleList().size()>0)?true:false;
		boolean submoduleflag=(!srchvo.getSubmoduleList().isEmpty() &&srchvo.getSubmoduleList().size()>0)?true:false;
		boolean screenflag=(!srchvo.getScreenNames().isEmpty() &&srchvo.getScreenNames().size()>0)?true:false;
		
		if((!itmsflag) && (!moduleFlag) &&(!submoduleflag) && (!screenflag) ){
			moduleDaoList=moduledao.findAllOrderBydat001ApplItmsNum();
			
		}
		else {
			if(itmsflag) {
				for (String itms:srchvo.getItmsList()) {
					moduleDaoList .addAll(moduledao
							.findAllModules(appdtldao.findBydat001ApplItmsNum(Integer.valueOf(itms.split("-")[0]))));
					
				}
			}			
		}
			 
		templist=processModuleData(moduleDaoList,srchvo);
		if(moduleFlag) {
			for(String module:srchvo.getModuleList()) {
				for(ScreenVO vo :templist) {
					if(vo.getModuleCodeName().getValue().split("-")[0].equalsIgnoreCase(module.split("-")[0])) {
						searchList.add(vo);
						
					}
				}
				
			}
		}
		 if(submoduleflag) {
			if(moduleFlag) {
				for(String module:srchvo.getSubmoduleList()) {
				for(ScreenVO vo :searchList) {
					if(vo.getSubmoduleCodeName().getValue().split("-")[0].equalsIgnoreCase(module.split("-")[0])) {
						searchList.add(vo);
						
					}
				}
			}
			}
			
				for(String module:srchvo.getSubmoduleList()) {
					for(ScreenVO vo :templist) {
						if(vo.getSubmoduleCodeName().getValue().split("-")[0].equalsIgnoreCase(module.split("-")[0])) {
							searchList.add(vo);
							
						}
					}
					
				}
			
			
		}
		if(screenflag) {
			if(moduleFlag||submoduleflag) {
				for(String module:srchvo.getScreenNames()) {
					for(ScreenVO vo :searchList) {
						if(vo.getScreenCode().equalsIgnoreCase(module.split("-")[0])) {
							searchList.add(vo);
							
						}
					}
				}
			}
			
				for(String module:srchvo.getScreenNames()) {
					for(ScreenVO vo :templist) {
						if(vo.getScreenCode().equalsIgnoreCase(module.split("-")[0])) {
							searchList.add(vo);
							
						}
					}
					
				}
			
		}
		if(!moduleFlag&&!submoduleflag&&!screenflag) {
			searchList=templist;
		}

		return searchList;

	}
	
	@SuppressWarnings("null")
	public List<ScreenVO> processModuleData(List<T_Module_005> moduleDaoList,SearchVO srchvo) {
		List<ScreenVO> list=new ArrayList<ScreenVO>();
		int count=0;
		for (T_Module_005 module : moduleDaoList) {
			List<T_Module_005> subModuleList = moduledao.findAllSubModules(module,"SM");
			if(subModuleList.size()>0 && !subModuleList.isEmpty()) {
				for (T_Module_005 submodule : subModuleList) {
					List<T_Module_005> screenList = moduledao.findAllSubModules(submodule,"SC");
					if (screenList != null && screenList.size() > 0) {
						for (T_Module_005 screen : screenList) {
							
							ScreenVO vo = new ScreenVO();

							count = count + 1;

							vo.setModuleKey(module.getKda005MdulKey());
							vo.setSubmoduleKey(submodule.getKda005MdulKey());
							vo.setScreenKey(screen.getKda005MdulKey());
							SelectedItem item = new SelectedItem();
							item.setKey(module.getKda004ApplItmsNum().getKda004ApplItmsNum().toString());
							item.setValue(module.getKda004ApplItmsNum().getKda004ApplItmsNum().toString() + "-"
									+ module.getKda004ApplItmsNum().getKda004ApplAcrnymCode());
							vo.setAppItmsNoName(item);
							//setting module code name
							SelectedItem modulecodename=new SelectedItem();
							modulecodename.setKey(module.getKda005MdulKey().toString());
							modulecodename.setValue(module.getKda005ModuleCode()+"-"+module.getKda005MdulName());
							vo.setModuleCodeName(modulecodename);
							//setting submodule code name
							SelectedItem submodulecodename=new SelectedItem();
							submodulecodename.setKey(submodule.getKda005MdulKey().toString());
							submodulecodename.setValue(submodule.getKda005ModuleCode()+"-"+submodule.getKda005MdulName());
							vo.setSubmoduleCodeName(submodulecodename);
							vo.setScreenCode(screen.getKda005ModuleCode());
							vo.setScreenName(screen.getKda005MdulName());
							vo.setScreenPurpose(
									(screen.getKda005ScrnDesc() != null && !screen.getKda005ScrnDesc().equals(""))
											? screen.getKda005ScrnDesc()
											: "");
							vo.setCreatedDate(screen.getKda005CrtS());
							vo.setLastUpdatedDate(screen.getKda005LstUpdtS());
							vo.setId(count);
							vo.setModulecodenameList(getModuleCodeNameByApplication(module.getKda004ApplItmsNum()));//setting for module dropdown list in datatable
							vo.setSubmodulecodenameList(getSubModuleCodeName(vo.getModuleCodeName()));
							list.add(vo);

						}
					} 
				}
			}
			
				List<T_Module_005> screenList = moduledao.findAllSubModules(module,"SC");
				
				if(screenList.size()>0 && !screenList.isEmpty()) {
				
				for(T_Module_005 screen:screenList) {
					
					ScreenVO vo = new ScreenVO();
					count = count + 1;

					vo.setModuleKey(module.getKda005MdulKey());
					vo.setSubmoduleKey(null);
					vo.setScreenKey(screen.getKda005MdulKey());
					SelectedItem item = new SelectedItem();
					item.setKey(module.getKda004ApplItmsNum().getKda004ApplItmsNum().toString());
					item.setValue(module.getKda004ApplItmsNum().getKda004ApplItmsNum().toString() + "-"
							+ module.getKda004ApplItmsNum().getKda004ApplAcrnymCode());
					vo.setAppItmsNoName(item);
					//setting module code name
					SelectedItem modulecodename=new SelectedItem();
					modulecodename.setKey(module.getKda005MdulKey().toString());
					modulecodename.setValue(module.getKda005ModuleCode()+"-"+module.getKda005MdulName());
					vo.setModuleCodeName(modulecodename);
					//setting submodule code name
					SelectedItem submodulecodename=new SelectedItem();
					submodulecodename.setKey(null);
					submodulecodename.setValue("");
					vo.setSubmoduleCodeName(submodulecodename);
					vo.setScreenCode(screen.getKda005ModuleCode());
					vo.setScreenName(screen.getKda005MdulName());
					vo.setScreenPurpose(
							(screen.getKda005ScrnDesc() != null && !screen.getKda005ScrnDesc().equals(""))
									? screen.getKda005ScrnDesc()
									: "");
					vo.setCreatedDate(screen.getKda005CrtS());
					vo.setLastUpdatedDate(screen.getKda005LstUpdtS());
					vo.setId(count);
					vo.setModulecodenameList(getModuleCodeNameByApplication(module.getKda004ApplItmsNum()));//setting for module dropdown list in datatable
					vo.setSubmodulecodenameList(getSubModuleCodeName(vo.getModuleCodeName()));
					list.add(vo);
				}
				

			}


		}
		return list;
		
	}
	public List<SelectedItem> getModuleCodeNameByApplication(T_Appl_Dtl_004 app) {
		List<SelectedItem> itmsList = new ArrayList<SelectedItem>();
		List<T_Module_005> moduleList=moduledao.findAllModules(app);
		for(T_Module_005 module:moduleList) {
			SelectedItem item=new SelectedItem();
			item.setKey(module.getKda005MdulKey().toString());
			item.setValue(module.getKda005ModuleCode()+"-"+module.getKda005MdulName());
			itmsList.add(item);
		}
		

		return itmsList;
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
	public List<String> getItmsNoWithAcrnym(){
		List<String> itmsList = new ArrayList<String>();
		Iterable<T_Appl_Dtl_004> entitylist = appdtldao.findAll();

		for (T_Appl_Dtl_004 app : entitylist) {
			itmsList.add(String.valueOf(app.getKda004ApplItmsNum()) + "-" + app.getKda004ApplAcrnymCode());
		}
		return itmsList;
	}
	
	

	/*public List<String> getModule(SelectedItem item) {
		List<String> modList = new ArrayList<String>();
		List<T_Module_005> modules = moduledao
				.findAllModules(appdtldao.findBydat001ApplItmsNum(Integer.valueOf(item.getKey())));

		for (T_Module_005 module : modules) {
			modList.add(module.getKda005MdulName());
		}

		return modList;
	}*/
	
	public List<String> getModule(List<String> list) {
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

	public List<String> getSubModule(List<String> modlist) {
		List<String> submodList = new ArrayList<String>();
		for (String itms : modlist) {
			List<T_Module_005> submodules = moduledao.findSubModulesByApplication(appdtldao.findBydat001ApplItmsNum(Integer.valueOf(itms.split("-")[0])), "SM");
			for (T_Module_005 submodule : submodules) {
				submodList.add(submodule.getKda005ModuleCode()+"-"+submodule.getKda005MdulName());
			}
		}
	

		return submodList;
	}

	public List<String> getScreen(List<String> submodlist) {
		List<String> screenList = new ArrayList<String>();
		
		for (String itms : submodlist) {
			List<T_Module_005> submodules =  moduledao.findSubModulesByApplication(appdtldao.findBydat001ApplItmsNum(Integer.valueOf(itms.split("-")[0])), "SC");
			for (T_Module_005 screen : submodules) {
				screenList.add(screen.getKda005ModuleCode()+"-"+screen.getKda005MdulName());
			}
			
		}
	
		return screenList;
	}


	public void processUpdate(List<ScreenVO> voList) {
		for (ScreenVO vo : voList) {
			if(vo.getSubmoduleCodeName().getKey()==null||vo.getSubmoduleCodeName().getValue().equalsIgnoreCase("Please Select")) {
				String parentmodcode="";
				if(vo.getModuleCodeName().getValue().contains("-")) {
					parentmodcode= vo.getModuleCodeName().getValue().split("-")[0];
				}else {
					parentmodcode=vo.getModuleCodeName().getValue();
				}
				T_Module_005 parent=moduledao.findBydat004ModuleCode(parentmodcode);
				T_Module_005 screen=moduledao.findBydat004ModuleCode(vo.getScreenCode());
				updateModule(screen, vo, parent);
			}
			else {
				String parentmodcode="";
				if(vo.getSubmoduleCodeName().getValue().contains("-")) {
					parentmodcode= vo.getSubmoduleCodeName().getValue().split("-")[0];
				}else {
					parentmodcode=vo.getSubmoduleCodeName().getValue();
				}
				T_Module_005 parent=moduledao.findBydat004ModuleCode(parentmodcode);
				T_Module_005 screen=moduledao.findBydat004ModuleCode(vo.getScreenCode());
				updateModule(screen, vo, parent);
			}
		}

	}

	public void updateModule(T_Module_005 module, ScreenVO vo,T_Module_005 Parent) {
		module.setKda005LstUpdtProcC("Update");
		module.setKda005LstUpdtS(new Timestamp(System.currentTimeMillis()));
		module.setKda005LstUpdtUsrC("DA_USER");
		module.setKda005MdulName(vo.getScreenName());
		module.setKda005ScrnDesc(vo.getScreenPurpose());
		module.setKda005ParntMdulKey(Parent);
		module.setKda004ApplItmsNum(appdtldao.findBydat001ApplItmsNum(Integer.valueOf(vo.getAppItmsNoName().getKey())));
		module.setKda005MdulKey(vo.getScreenKey());
		moduledao.updateModule(module);
		
	}

	public void processSave(List<ScreenVO> voList) {
		for (ScreenVO vo : voList) {
			Integer itmsno = Integer.valueOf(vo.getAppItmsNoName().getKey());
			if(vo.getSubmoduleCodeName().getKey()==null||vo.getSubmoduleCodeName().getValue().equalsIgnoreCase("please select")) {
				String parent="";
				if(vo.getModuleCodeName().getValue().contains("-")) {
					parent= vo.getModuleCodeName().getValue().split("-")[0];
				}else {
					parent=vo.getModuleCodeName().getValue();
				}
				T_Module_005 parentModule=moduledao.findBydat004ModuleCode(parent);
				insertModule(vo.getScreenCode(),vo.getScreenName(),itmsno,parentModule,vo.getScreenPurpose(),"SC");
			}else {
				String parent="";
				if(vo.getSubmoduleCodeName().getValue().contains("-")) {
					parent= vo.getSubmoduleCodeName().getValue().split("-")[0];
				}else {
					parent=vo.getSubmoduleCodeName().getValue();
				}
				T_Module_005 parentModule=moduledao.findBydat004ModuleCode(parent);
				insertModule(vo.getScreenCode(),vo.getScreenName(),itmsno,parentModule,vo.getScreenPurpose(),"SC");
			}
			
			
		

		}
		

	}

	public void insertModule(String Screencode,String screenName, Integer itmsnum, T_Module_005 parentmodule, String screenDesc,String scrnflag) {
		T_Module_005 module = new T_Module_005();
		module.setKda005ModuleCode(Screencode);
		module.setKda005MdulName(screenName);
		module.setKda005ScrnDesc(screenDesc);
		module.setKda005ParntMdulKey(parentmodule != null ? parentmodule : null);
		module.setKda005SeqNumber(1);
		module.setKda005StatusFlag('Y');
		module.setKda005CrtUsrC("DA_USER");
		module.setKda005CrtS(new Timestamp(System.currentTimeMillis()));
		module.setKda005CrtProcC("Save");
		module.setKda005LstUpdtUsrC("DA_USER");
		module.setKda005LstUpdtS(new Timestamp(System.currentTimeMillis()));
		module.setKda005LstUpdtProcC("Save");
		module.setKda005ModuleFlag(scrnflag);
		T_Appl_Dtl_004 appl = appdtldao.findBydat001ApplItmsNum(itmsnum);
		module.setKda004ApplItmsNum(appl);

		moduledao.save(module);
	}
	
	public ScreenVO validate(List<ScreenVO> moduleList, boolean saveorUpdate){

		HashSet<String> entrydupSet = new HashSet<String>();

		List<String> errorList = new ArrayList<String>();
		ScreenVO VO = new ScreenVO();
		for(ScreenVO vo : moduleList){
			String errorMsg = null ;
			if(entrydupSet.add(vo.getScreenCode())){
				if(saveorUpdate ==  true ? !submoduleCheck(vo.getScreenCode(),vo.getAppItmsNoName()) : true){

				}else{
					errorMsg = "{{\"common.LineNo.Lable\" | translate}}" + ".: ["+vo.getId()+"] "+ "{{\"conifgmodule.screencode.label\" | translate}} " + "'"+vo.getScreenCode() + "'"+ " {{\"common.select.exist.err\" | translate}}";
				}

			}else{
				errorMsg = "{{\"common.LineNo.Lable\" | translate}}" + ".: ["+vo.getId()+"] "+ "{{\"conifgmodule.screencode.label\" | translate}} " + "'"+vo.getScreenCode() + "'"+ " {{\"appliMain.select.dup.err\" | translate}}";
			}
		
			
			if(errorMsg != null)
			errorList.add(errorMsg);
		}

		VO.setErrors(errorList);

		return VO ;
	}
	public boolean submoduleCheck(String modulecode,SelectedItem appitmsnoname){

		boolean flag = false;
		T_Appl_Dtl_004 app=appdtldao.findBydat001ApplItmsNum(Integer.valueOf(appitmsnoname.getKey()));
		T_Module_005 moduleExists = moduledao.findBydat004ModuleCode(app,modulecode);

		//T_Module_005 moduleExists = moduledao.findBydat004ModuleCode(modulecode);
		if(moduleExists != null){
			flag = true;
		}

		return flag;
	}
	
	public List<SelectedItem> getModuleCodeName(SelectedItem itms) {
		List<SelectedItem> itmsList = new ArrayList<SelectedItem>();
		List<T_Module_005> moduleList=moduledao.findAllModules(appdtldao.findBydat001ApplItmsNum(Integer.valueOf(itms.getKey())));
		for(T_Module_005 module:moduleList) {
			SelectedItem item=new SelectedItem();
			item.setKey(module.getKda005MdulKey().toString());
			item.setValue(module.getKda005ModuleCode()+"-"+module.getKda005MdulName());
			itmsList.add(item);
		}
		

		return itmsList;
	}
	public List<SelectedItem> getSubModuleCodeName(SelectedItem modulecodename) {
		List<SelectedItem> itmsList = new ArrayList<SelectedItem>();
		T_Module_005 parentmodule=moduledao.findBydat004ModuleCode(modulecodename.getValue().split("-")[0]);
		List<T_Module_005> moduleList=moduledao.findAllSubModules(parentmodule, "SM");
		for(T_Module_005 module:moduleList) {
			SelectedItem item=new SelectedItem();
			item.setKey(module.getKda005MdulKey().toString());
			item.setValue(module.getKda005ModuleCode()+"-"+module.getKda005MdulName());
			itmsList.add(item);
		}
		

		return itmsList;
	}
	

	public File dowloadExcel(List<ScreenVO> voList) {
		// TODO Auto-generated method stub

		String[] headerNames = { "ITMS No", "Module", "SubModule","Screen Code", "Screen Name",
				"Screen Purpose","Created Date", "Last Updated Date"  };
		File downloadFile = com.thirdware.utils.ExcelUtils.downloadExportExcellforScreen("Screen Configuration", headerNames, 8,
				voList);
		return downloadFile;

	}

	public File dowloadTemplate() {
		String[] headerNames = { "ITMS No*", "Module Code*", "SubModule Code","Screen Code*", "Screen Name*",
				"Screen Purpose" };
		File downloadFile = com.thirdware.utils.ExcelUtils.downloadExcelTemplate("Screen Maintenance Template",
				headerNames, 6);
		return downloadFile;
	}
	
	public File fileUpload(MultipartFile file) throws Exception{
		
		

		List<ArrayList<Object>> sheetData = new ArrayList<ArrayList<Object>>();
		int totalRecords = 0;
		int successRecords = 0;
		int failureRecords = 0;
		//entrydupUpldSet = new HashSet<>();
		List<String> allModNamesList = moduledao.findAllSuBModuleCode("SC");
		List<ScreenVO> totalRecordList = new ArrayList<ScreenVO>();
		List<ScreenVO> successRecordList = new ArrayList<ScreenVO>();
		List<ScreenVO> failedRecordList = new ArrayList<ScreenVO>();
		List<ScreenVO> insertRecordList = new ArrayList<ScreenVO>();
		List<ScreenVO> updationRecordList = new ArrayList<ScreenVO>();

		InputStream fis = null;
		File finalFile = null;
		try {                
			fis = file.getInputStream();				
			boolean isXlsx = file.getOriginalFilename().contains(".xlsx")  ;				
			sheetData = uploadedFile(fis, 6, isXlsx, file.getOriginalFilename(), "Module Log");
			String [] headerNames = {"ITMS No", "Module Code", "SubModule Code","Screen Code", "Screen Name",
					"Screen Purpose"};


			for (int i = 1; i <sheetData.size(); i++) {	
				totalRecords++;				
				List<Object> list = sheetData.get(i);
				final String itms  = (list.get(0) != null &&  (list.get(0).toString() != "")) ? list.get(0).toString().trim() : "";
				final String modulecodename = (list.get(1) != null && (list.get(1).toString()  != "")) ? list.get(1).toString().trim() : "";
				final String submodulecodename = (list.get(2) != null && (list.get(2).toString()  != "")) ? list.get(2).toString().trim() : "";
				final String screencode = (list.get(3) != null && (list.get(3).toString()  != "")) ? list.get(3).toString().trim() : "";
				final String screename  = (list.get(4) != null &&  (list.get(4).toString() != "")) ? list.get(4).toString().trim() : "";
				final String screendesc = (list.get(5) != null && (list.get(5).toString()  != "")) ? list.get(5).toString().trim() : "";

				ScreenVO vo = new ScreenVO();		
				

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
				
				if(!modulecodename.equals("")) {
					
					SelectedItem item=new SelectedItem();
					item.setKey(vo.getAppItmsNoName().getKey());
					item.setValue(modulecodename);
					vo.setModuleCodeName(item);
				}
				else {
					SelectedItem item=new SelectedItem();
					item.setKey(null);
					item.setValue(modulecodename);
					vo.setModuleCodeName(item);
				}
				
				if(!submodulecodename.equals("")) {
					
					SelectedItem item=new SelectedItem();
					item.setKey(vo.getAppItmsNoName().getKey());
					item.setValue(submodulecodename);
					vo.setSubmoduleCodeName(item);
					vo.setSubModuleExists(true);
				}
				else {
					
					SelectedItem item=new SelectedItem();
					item.setKey(null);
					item.setValue(submodulecodename);
					vo.setSubmoduleCodeName(item);
					vo.setSubModuleExists(false);
				}
				
				vo.setScreenCode(screencode);
				vo.setScreenName(screename);
				vo.setScreenPurpose(screendesc);
			
				
				
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

			for( ScreenVO vo :successRecordList){
				
				if(allModNamesList.contains(vo.getScreenCode())){
					vo.setScreenKey(moduledao.findBydat004ModuleCode(vo.getScreenCode()).getKda005MdulKey());
					updationRecordList.add(vo);
				}else{
					insertRecordList.add(vo);
				}
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
	
	public String validateUpload(ScreenVO vo)
	{
		String errorMsg="";
		
		
		if(vo.getAppItmsNoName().getKey()==null||vo.getAppItmsNoName().getKey()==""){
			errorMsg+=("ITMS NO is mandatory");
			
		}else if(!CommonUtil.isNumeric(vo.getAppItmsNoName().getKey())) {
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += " ITMS No should be Numeric" ;
		}
		else if(vo.getAppItmsNoName().getKey().length()>5) {
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += " ITMS No should not be greater than 5" ;
			
		}else if(Integer.parseInt(vo.getAppItmsNoName().getKey())<0){
			errorMsg +=  "ITMS No should be positive";
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
						errorMsg+="ITMS No and Acronym does not match";
					}
				}
				catch(Exception e)
				{
					if(!errorMsg.equals("")){
						errorMsg  += ",";
					}
					errorMsg+="ITMS No and Acronym does not match";
				}
			}
			
		}
		 
		if(vo.getModuleCodeName().getKey()==null|| vo.getModuleCodeName().getKey()==""){
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += "Module Code is mandatory" ;
			
		}else if( vo.getModuleCodeName().getKey()!=null &&  vo.getModuleCodeName().getValue().matches("[^a-zA-Z]")) {
			 if(!errorMsg.equals("")){
					errorMsg  += ",";
				}
				errorMsg +="Module Code should contain only alphabets";
		 }else {
			 try {
				 T_Module_005 parentmodule=moduledao.findBydat004ModuleCode(vo.getModuleCodeName().getValue());
				 if(parentmodule==null) {
					 if(!errorMsg.equals("")){
							errorMsg  += ",";
						}
						errorMsg +="Module is not available";
					 
				 }
				 
			 }catch(Exception e) {
				 
				 if(!errorMsg.equals("")){
						errorMsg  += ",";
					}
					errorMsg +="Module is not available";
				 
			 }
			 
			 
		 }
		
		/*
		if(!vo.isSubModuleExists() && vo.getSubmoduleCodeName().getKey()==null && !vo.getSubmoduleCodeName().getValue().equals("")){
			
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += "SubModule Code is mandatory" ;
		}else*/ 
		if(vo.isSubModuleExists() && vo.getSubmoduleCodeName().getKey()!=null && vo.getSubmoduleCodeName().getValue().matches("[^a-zA-z]")) {
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg +="SubModule Code should contain only alphabets";
		}else if( vo.isSubModuleExists() && vo.getSubmoduleCodeName().getKey()!=null &&  vo.getSubmoduleCodeName().getValue().length()>5) {
			 if(!errorMsg.equals("")){
					errorMsg  += ",";
				}
				errorMsg +="SubModule Code can be maximum 5 characters";
		 }else {
			 if(vo.isSubModuleExists()) {
				 try {
					 T_Module_005 parentmodule=moduledao.findBydat004ModuleCode(vo.getSubmoduleCodeName().getValue());
					 if(parentmodule==null) {
						 if(!errorMsg.equals("")){
								errorMsg  += ",";
							}
							errorMsg +="SubModule is not available";
						 
					 }
					 
				 }catch(Exception e) {
					 
					 if(!errorMsg.equals("")){
							errorMsg  += ",";
						}
						errorMsg +="SubModule is not available";
					 
				 }
			 }
			
			 
			 
		 }
		
		if(vo.getScreenCode()==null|| vo.getScreenCode()==""){
			 if(!errorMsg.equals("")){
					errorMsg  += ",";
				}
				errorMsg += "Screen Code is mandatory" ;
				
			}else if(vo.getScreenCode().matches("[^a-zA-Z]")) {
				if(!errorMsg.equals("")){
					errorMsg  += ",";
				}
				errorMsg += "Screen Code should contain alphabets" ;
				
			}else if(vo.getScreenCode().length()>5) {
				if(!errorMsg.equals("")){
					errorMsg  += ",";
				}
				errorMsg += "Screen Code should not be greater than 5" ;
			}
		
		if(vo.getScreenName()==null|| vo.getScreenName()==""){
			
			 if(!errorMsg.equals("")){
					errorMsg  += ",";
				}
				errorMsg += "Screen Name  is mandatory" ;
		}else if(vo.getScreenName().matches("[^a-zA-Z]")) {
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += "Screen Name should contain alphabets" ;
			
		}else if(vo.getScreenName().length()>150) {
			if(!errorMsg.equals("")){
				errorMsg  += ",";
			}
			errorMsg += "Screen Name should not be greater than 150 characters" ;
		}
		
		
		
		return errorMsg;
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
	
	public File logExcelGeneration(List<ScreenVO> dsidVo , String user, String[] headerNames,int totalRecords,
			int failureRecords,int successRecords ,  List<ScreenVO> failedRecordList ,List<ScreenVO> insertRecordList ,List<ScreenVO> updationRecordList  ) throws Exception 
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

			XSSFCell cellRmks = row0.createCell(6);
			cellRmks.setCellStyle(editableCellStyle);		
			cellRmks.setCellValue("Remarks");	

			XSSFRow rows=null;
			int row=1;
			for(ScreenVO vo : failedRecordList  )
			{
				rows = sheet.createRow(row);
				row++;

				XSSFCell cell00 = rows.createCell(0);
				cell00.setCellValue(vo.getAppItmsNoName().getKey()!=null?vo.getAppItmsNoName().getKey():"");
				XSSFCell cell01 = rows.createCell(1);
				cell01.setCellValue(vo.getModuleCodeName()!=null?vo.getModuleCodeName().getValue():"");
				XSSFCell cell02 = rows.createCell(2);
				cell02.setCellValue(vo.getSubmoduleCodeName().getValue()!=null?vo.getSubmoduleCodeName().getValue():"");
				XSSFCell cell03 = rows.createCell(3);
				cell03.setCellValue(vo.getScreenCode() != null ? vo.getScreenCode() : "");
				XSSFCell cell04 = rows.createCell(4);
				cell04.setCellValue(vo.getScreenName()!= null ? vo.getScreenName() : "");
				XSSFCell cell05 = rows.createCell(5);
				cell05.setCellValue(vo.getScreenPurpose() != null ? vo.getScreenPurpose() : "");
				XSSFCell cell06 = rows.createCell(6);
				cell06.setCellValue(vo.getRemarks());

			}

			for (int columnPosition = 0; columnPosition < headerNames.length+1; columnPosition++) {
				sheet.autoSizeColumn(columnPosition);
			}
			/*File xlsxFile = null;
			File file = new File("c:/logeSIP");
			if(Files.exists(file.toPath(), LinkOption.NOFOLLOW_LINKS))
			{
				 xlsxFile = new File("c:/logeSIP/DSID Maintanance Log.xlsx");
				FileOutputStream out = new FileOutputStream(xlsxFile);
				workbook.write(out);
				out.flush();
				out.close();
			}
			else
			{
				file.mkdirs();
				 xlsxFile = new File("c:/logeSIP/DSID Maintanance Log.xlsx");
				FileOutputStream out = new FileOutputStream(xlsxFile);
				workbook.write(out);
				out.flush();
				out.close();
			}*/
			
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
}
