package com.thirdware.services.dbconfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.thirdware.entity.E_MDAT006_PARM;
import com.thirdware.entity.T_APPL_DB_DTL_010;
import com.thirdware.entity.T_Appl_Dtl_004;

import com.thirdware.repositories.IAppDtlDAO;
import com.thirdware.utils.dbconfig.CommonUtils;
import com.thirdware.utils.dbconfig.ExcelUtils;
import com.thirdware.vo.SelectedItem;
import com.thirdware.vo.dbconfig.DBconfigSearchVo;
import com.thirdware.vo.dbconfig.DBconfigdatatableVo;
import com.thirdware.vo.parameter.E_MDAT006_PARM_Vo;;
@Service
@Transactional
public class DBconfigService {
	
	private static final boolean TrueOnlyBooleanSerializer = false;

	@Autowired
com.thirdware.repositories.dbconfig.DBconfigRepository dao;
@Autowired
com.thirdware.repositories.dbconfig.DBconfigCustomQry DBconfigCustomQry;
@Autowired
com.thirdware.repositories.parameter.I_MDAT006_PARM I_MDAT006_PARM;

HashSet<String> entrydupUpldSet=new HashSet<String>();
	
	@Autowired
	IAppDtlDAO daoapp;
	
	//get data for datatable initially
	public List<DBconfigdatatableVo> getAllRecords() {
		List<String> itmsno=new ArrayList<>();
		List<SelectedItem> appName=new ArrayList<SelectedItem>();
		List<String> appAcrynm=new ArrayList<>();
		Set<String> databasetype=new HashSet<>();
		List<String> databasename=new ArrayList<>();
		List<DBconfigdatatableVo> dbvo=new ArrayList<DBconfigdatatableVo>();
		
		List<T_APPL_DB_DTL_010> obj = (List<T_APPL_DB_DTL_010>) dao.findAllOrderBy();
		List<T_Appl_Dtl_004> itmsobj=(List<T_Appl_Dtl_004>) daoapp.findAll();
		
		//DBconfigVo vo=new DBconfigVo();
		
		Integer id=1;
		for (T_APPL_DB_DTL_010 pojo: obj) {
			DBconfigdatatableVo vo=new DBconfigdatatableVo();
			vo.setId(id);
			vo.setItmsNo(String.valueOf(pojo.getApplication().getKda004ApplItmsNum())+"-"+pojo.getApplication().getKda004ApplAcrnymCode());
			vo.setApplicationName(pojo.getApplication().getKda004ApplName());
			vo.setApplicationAcronym(pojo.getApplication().getKda004ApplAcrnymCode());
			vo.setDatabaseName(pojo.getDatabasename());
			vo.setDatabaseType(pojo.getDatabasetype());
			databasetype.add(pojo.getDatabasetype());
			databasename.add(pojo.getDatabasename());
			vo.setDriverName(pojo.getDrivername());
			vo.setHostName(pojo.getHostname());
			vo.setPassword(pojo.getPassword());
			vo.setUserName(pojo.getUsername());
			vo.setCreatedDateAndTime(pojo.getCreateddate());
			vo.setLastUpdatedDateAndTime(pojo.getLastupdateddate());
			vo.setPortNumber(pojo.getPortno().toString());
			vo.setIpAddress(pojo.getIpaddress());
			if(id==1) {
				System.out.println("id"+id);
				for (T_Appl_Dtl_004 pojoList : itmsobj) {
					SelectedItem si=new SelectedItem();
					itmsno.add(pojoList.getKda004ApplItmsNum()+"-"+pojoList.getKda004ApplAcrnymCode());
					si.setKey(String.valueOf(pojoList.getKda004ApplItmsNum()));
					si.setValue(pojoList.getKda004ApplName().toUpperCase());
		
					appName.add(si);
					appAcrynm.add(pojoList.getKda004ApplAcrnymCode());
					
					
				}
				vo.setListapplicationAcronym(appAcrynm);
				vo.setListapplicationName(appName);
				vo.setListitmsNo(itmsno);
				vo.setListdatabaseName(databasename);
				vo.setListdatabaseType(databasetype);
			}
			id++;
			dbvo.add(vo);
		}
		return dbvo;
	}
	
	
	
	
	
	
	//updating row
	public String updateRowRecords(List<DBconfigdatatableVo> updateRow) {
		// TODO Auto-generated method stub
		List<T_APPL_DB_DTL_010> list =new ArrayList<T_APPL_DB_DTL_010>();
		String s="";
		Set<String> dupDbName=new HashSet<>();
		for (DBconfigdatatableVo vo1 : updateRow) {
			for (DBconfigdatatableVo vo2 : updateRow) {
				if(!vo1.getItmsNo().equals(vo2.getItmsNo()) && vo1.getDatabaseName().equals(vo2.getDatabaseName())) {
				dupDbName.add(vo1.getDatabaseName());
			}
			
		}
			}
		
	
		for(String str:dupDbName)
			s+=str+", ";
		if (s != "" && !s.equals("")){
			s=s.substring(0,s.length()-2);
			return s+" Database Names are reapted. It should be unique";
		}
		
		
		
		for (DBconfigdatatableVo dBconfigdatatableVo : updateRow) {
			String dbName;
			if(dBconfigdatatableVo.getItmsNo().contains("-")) {
				 dbName=dao.uniqueDBName(dBconfigdatatableVo.getDatabaseName().toUpperCase(),Integer.parseInt(dBconfigdatatableVo.getItmsNo().split("-")[0]));
			}else {
			 dbName=dao.uniqueDBName(dBconfigdatatableVo.getDatabaseName().toUpperCase(),Integer.parseInt(dBconfigdatatableVo.getItmsNo()));
			}
		
		if (dbName != null) {
			return "Line no [" + dBconfigdatatableVo.getId() + "] Database Name is already exist! try again";
		} else {
			
			T_APPL_DB_DTL_010 record =dao.findById(Integer.parseInt(dBconfigdatatableVo.getItmsNo().split("-")[0])).get();
			
			
			
			
			record.setDatabasename(dBconfigdatatableVo.getDatabaseName());
			record.setDatabasetype(dBconfigdatatableVo.getDatabaseType());
			record.setHostname(dBconfigdatatableVo.getHostName());
			record.setIpaddress(dBconfigdatatableVo.getIpAddress());
			record.setPortno(Integer.parseInt(dBconfigdatatableVo.getPortNumber()));
			record.setDrivername(dBconfigdatatableVo.getDriverName());
			record.setUsername(dBconfigdatatableVo.getUserName());
			record.setPassword(dBconfigdatatableVo.getPassword());
			record.setCreatedUN("SRINIVAS");
			
			record.setLastupdateddate(new Date());
			list.add(record);
			
		}
	
		}
		dao.saveAll(list);
		if(list.size() ==1)
		return "Record Updated Successfully";
		else
			return "Record(s) Updated Successfully";
	}
		
	
	//adding row
	public String addRowsRecords(List<DBconfigdatatableVo> addRow) {
		// TODO Auto-generated method stub
		
		List<T_APPL_DB_DTL_010> list =new ArrayList<T_APPL_DB_DTL_010>();
		
		String s="";
		Set<String> dupDbName=new HashSet<>();
		for (DBconfigdatatableVo vo1 : addRow) {
			for (DBconfigdatatableVo vo2 : addRow) {
				if(!vo1.getItmsNo().equals(vo2.getItmsNo()) && vo1.getDatabaseName().equals(vo2.getDatabaseName())) {
				dupDbName.add(vo1.getDatabaseName());
			}
			}
		}
		for(String str:dupDbName)
			s+=str+", ";
		if (s != "" && !s.equals("")){
			s=s.substring(0,s.length()-2);
			return s+" Database Names are reapted. It should be unique";
		}
		
		
		for (DBconfigdatatableVo dBconfigdatatableVo : addRow) {
			T_APPL_DB_DTL_010 itmsNo;
			int itms;
			if(dBconfigdatatableVo.getItmsNo().contains("-")) {
				itms=Integer.parseInt(dBconfigdatatableVo.getItmsNo().split("-")[0]);
			 itmsNo=dao.uniqueItmsNo(Integer.parseInt(dBconfigdatatableVo.getItmsNo().split("-")[0]));
			}
			else {
				itms=Integer.parseInt(dBconfigdatatableVo.getItmsNo().toString());
				 itmsNo=dao.uniqueItmsNo(Integer.parseInt(dBconfigdatatableVo.getItmsNo().toString()));
			}
			if(itmsNo !=null) {
				return "Line no [" + dBconfigdatatableVo.getId() + "] Database has been already mapped to this application";
			
			}
			String dbName=dao.uniqueDB(dBconfigdatatableVo.getDatabaseName().toUpperCase());
			
			if (dbName != null) {
				return "Line no [" + dBconfigdatatableVo.getId() + "] Database Name is already exist! try again for this application";
			} else {
				T_APPL_DB_DTL_010 pojo=new T_APPL_DB_DTL_010();
				Integer intObj = new Integer(itms);
				pojo.setItmsNo(intObj);
				pojo.setDatabasename(dBconfigdatatableVo.getDatabaseName());
				pojo.setDatabasetype(dBconfigdatatableVo.getDatabaseType());
				pojo.setHostname(dBconfigdatatableVo.getHostName());
				pojo.setIpaddress(dBconfigdatatableVo.getIpAddress());
				pojo.setPortno(Integer.parseInt(dBconfigdatatableVo.getPortNumber()));
				pojo.setDrivername(dBconfigdatatableVo.getDriverName());
				pojo.setUsername(dBconfigdatatableVo.getUserName());
				pojo.setPassword(dBconfigdatatableVo.getPassword());
				pojo.setCreatedUN("SRINIVAS");
				pojo.setCreateddate(new Date());
				pojo.setLastupdateddate(new Date());
				list.add(pojo);
				
			}
			
			
		}
		dao.saveAll(list);
		if(list.size() ==1 )
	 return "Record Saved Successfully";
		else
			return "Record(s) Saved Successfully";
	}

	public List<DBconfigdatatableVo> getitms() {
		List<T_Appl_Dtl_004> itmsobj=(List<T_Appl_Dtl_004>) daoapp.findAll();
		List<DBconfigdatatableVo> list=new ArrayList<>();
		
		for (T_Appl_Dtl_004 T_Appl_Dtl_004 : itmsobj) {
			DBconfigdatatableVo vo=new DBconfigdatatableVo();
			vo.setItmsNo(String.valueOf(T_Appl_Dtl_004.getKda004ApplItmsNum()));
			vo.setApplicationName(T_Appl_Dtl_004.getKda004ApplName());
			vo.setApplicationAcronym(T_Appl_Dtl_004.getKda004ApplAcrnymCode());
			list.add(vo);
		}
		
		return list;
	}




	
	public List<DBconfigdatatableVo> search(DBconfigSearchVo searchdata) {
		
		if((searchdata.getInputItmsNo()==null ||searchdata.getInputItmsNo().isEmpty()) &&searchdata.getInputApplicationName().equals("")&&
				(searchdata.getInputDatabaseType()==null ||searchdata.getInputDatabaseType().isEmpty()) && (searchdata.getInputDatabaseName()==null |searchdata.getInputDatabaseName().isEmpty()))
		{
			return getAllRecords();
		}
		else {
		
		
		List<DBconfigdatatableVo> list=new ArrayList<DBconfigdatatableVo>();
		//
		List<String> selectedItmsList=searchdata.getInputItmsNo();
		String selectedAppName=searchdata.getInputApplicationName();
		List<String> selectedDataBaseType=searchdata.getInputDatabaseType();
		List<String> selectedDataBaseName=searchdata.getInputDatabaseName();
		
		List<String> seleteditmsno=null;
		try {
			seleteditmsno=new ArrayList<>();
		for (String string : selectedItmsList) {
			
			seleteditmsno.add(string.split("-")[0]);
		}

		Integer id=1;
		if(selectedItmsList!=null && selectedItmsList.size()>0) {
			for(String application:selectedItmsList) {
				
				int itmsno=Integer.valueOf(application.split("-")[0]);
				
				 T_APPL_DB_DTL_010 db=dao.findByapplication(itmsno);
				 if(db!=null) {
				 
				 DBconfigdatatableVo vo=new DBconfigdatatableVo();
					vo.setId(id);
					vo.setItmsNo(String.valueOf(db.getApplication().getKda004ApplItmsNum()));
					vo.setApplicationName(db.getApplication().getKda004ApplName());
					vo.setApplicationAcronym(db.getApplication().getKda004ApplAcrnymCode());
					vo.setDatabaseName(db.getDatabasename());
					vo.setDatabaseType(db.getDatabasetype());
					
					vo.setDriverName(db.getDrivername());
					vo.setHostName(db.getHostname());
					vo.setPassword(db.getPassword());
					vo.setUserName(db.getUsername());
					vo.setCreatedDateAndTime(db.getCreateddate());
					vo.setLastUpdatedDateAndTime(db.getLastupdateddate());
					vo.setPortNumber(db.getPortno().toString());
					vo.setIpAddress(db.getIpaddress());
				
					id++;
					list.add(vo);
			}
			
			}
		} if( selectedAppName!=null &&selectedAppName!="") {
			
			T_Appl_Dtl_004 appdb=daoapp.findBydat001ApplItmsNum(selectedAppName);
			if(!seleteditmsno.contains(String.valueOf(appdb.getKda004ApplItmsNum()))) {
				
				seleteditmsno.add(String.valueOf(appdb.getKda004ApplItmsNum()));
			T_APPL_DB_DTL_010 db=dao.findByapplication(appdb.getKda004ApplItmsNum());
			if(db!=null) {
			 DBconfigdatatableVo vo=new DBconfigdatatableVo();
				vo.setId(id);
				vo.setItmsNo(String.valueOf(db.getApplication().getKda004ApplItmsNum()));
				vo.setApplicationName(db.getApplication().getKda004ApplName());
				vo.setApplicationAcronym(db.getApplication().getKda004ApplAcrnymCode());
				vo.setDatabaseName(db.getDatabasename());
				vo.setDatabaseType(db.getDatabasetype());
				
				vo.setDriverName(db.getDrivername());
				vo.setHostName(db.getHostname());
				vo.setPassword(db.getPassword());
				vo.setUserName(db.getUsername());
				vo.setCreatedDateAndTime(db.getCreateddate());
				vo.setLastUpdatedDateAndTime(db.getLastupdateddate());
				vo.setPortNumber(db.getPortno().toString());
				vo.setIpAddress(db.getIpaddress());
			
				id++;
				list.add(vo);
			}
			}
		}if((selectedDataBaseType!=null && selectedDataBaseType.size()>0)) {
			for (String string : selectedDataBaseType) {
				
				
				List<T_APPL_DB_DTL_010> dblist=(List<T_APPL_DB_DTL_010>) dao.findByDatabasetype(string);
				for (T_APPL_DB_DTL_010 db : dblist) {
					
				
				if(db!=null) {
					
					if(!seleteditmsno.contains(db.getItmsNo().toString())) {
						
						seleteditmsno.add(db.getItmsNo().toString());
					 DBconfigdatatableVo vo=new DBconfigdatatableVo();
						vo.setId(id);
						vo.setItmsNo(String.valueOf(db.getApplication().getKda004ApplItmsNum()));
						vo.setApplicationName(db.getApplication().getKda004ApplName());
						vo.setApplicationAcronym(db.getApplication().getKda004ApplAcrnymCode());
						vo.setDatabaseName(db.getDatabasename());
						vo.setDatabaseType(db.getDatabasetype());
						
						vo.setDriverName(db.getDrivername());
						vo.setHostName(db.getHostname());
						vo.setPassword(db.getPassword());
						vo.setUserName(db.getUsername());
						vo.setCreatedDateAndTime(db.getCreateddate());
						vo.setLastUpdatedDateAndTime(db.getLastupdateddate());
						vo.setPortNumber(db.getPortno().toString());
						vo.setIpAddress(db.getIpaddress());
					
						id++;
						list.add(vo);
					}
				}
			}
			}
		}
		if(selectedDataBaseType!=null) {
			
			for (String string : selectedDataBaseName) {
				T_APPL_DB_DTL_010 db=dao.findByDatabaseName(string);
				if(db!=null) {
					if(!seleteditmsno.contains(db.getItmsNo().toString())) {
						seleteditmsno.add(db.getItmsNo().toString());
					 DBconfigdatatableVo vo=new DBconfigdatatableVo();
						vo.setId(id);
						vo.setItmsNo(String.valueOf(db.getApplication().getKda004ApplItmsNum()));
						vo.setApplicationName(db.getApplication().getKda004ApplName());
						vo.setApplicationAcronym(db.getApplication().getKda004ApplAcrnymCode());
						vo.setDatabaseName(db.getDatabasename());
						vo.setDatabaseType(db.getDatabasetype());
						
						vo.setDriverName(db.getDrivername());
						vo.setHostName(db.getHostname());
						vo.setPassword(db.getPassword());
						vo.setUserName(db.getUsername());
						vo.setCreatedDateAndTime(db.getCreateddate());
						vo.setLastUpdatedDateAndTime(db.getLastupdateddate());
						vo.setPortNumber(db.getPortno().toString());
						vo.setIpAddress(db.getIpaddress());
					
						id++;
						list.add(vo);
					}
				}
			}
		}
		
	
		//
		
		// TODO Auto-generated method stub
		
	
		
	}
		catch(Exception e) {
		e.printStackTrace();
	
	}
		return list;
		}

	}

	public File dowloadTemplate() {
		// TODO Auto-generated method stub
		
		
		String[] headerNames = {"ITMS No*","Database Type*","HostName*", "Ip Address*", "Port Number*", "Driver Name*",
				"Database Name*", "UserName*","Password*" };
		File downloadFile = ExcelUtils.downloadExcelTemplate("Database Configuartion Template", headerNames, 9);
		return downloadFile;
	}




		public File dowloadExcel(List<DBconfigdatatableVo> dbvo) {
		// TODO Auto-generated method stub

		String[] headerNames = { "Line No.", "ITMS No","Application Name","Application Acronym", "Database Name", "Database Type", "HostName", "Port Number",
				"Driver Name", "Ip Address", "UserName","Password","Created Date & Time", "Last Updated Date & Time" };
		File downloadFile = ExcelUtils.downloadExcel("Database Configuration", headerNames, 14, dbvo);
		return downloadFile;

	}

	

		


		public File fileUpload(MultipartFile file) {
			String[] headerNames = {"ITMS No*","Database Type*","HostName*", "Ip Address*", "Port Number*", "Driver Name*",
				"Database Name*", "UserName*","Password*" };

			
				
	
	List<ArrayList<Object>> sheetData = new ArrayList<ArrayList<Object>>();
	int totalRecords = 0;
	int successRecords = 0;
	int failureRecords = 0;
	entrydupUpldSet = new HashSet<>();
	List<Integer> allitmsList = new ArrayList<Integer>();
	List<String> alldbList = new ArrayList<String>();
	
	
	List<DBconfigdatatableVo> totalRecordList = new ArrayList<DBconfigdatatableVo>();
	List<DBconfigdatatableVo> successRecordList = new ArrayList<DBconfigdatatableVo>();
	List<DBconfigdatatableVo> failedRecordList = new ArrayList<DBconfigdatatableVo>();
	List<DBconfigdatatableVo> insertRecordList = new ArrayList<DBconfigdatatableVo>();
	List<DBconfigdatatableVo> updationRecordList = new ArrayList<DBconfigdatatableVo>();
	Map<Integer, String> map=getItmsDbN();
	
	Set<Integer> set = map.keySet();
	Collection<String> dbset=map.values();
	alldbList=new ArrayList<>(dbset);
	allitmsList = new ArrayList<>(set);
	String errorMsg="";
	InputStream fis = null;
	File finalFile = null;

	try {
		fis = file.getInputStream();
		boolean isXlsx = file.getOriginalFilename().contains(".xlsx");
		sheetData = uploadedFile(fis, 9, isXlsx, file.getOriginalFilename(), "DataBase Configuration Log");
		
		for(int i=1;i<sheetData.size();i++) {
			totalRecords++;
			List<Object> list = sheetData.get(i);
			

			/* "ITMS No", "Database Name", "Database Type", "HostName",
				"Port Number","Driver Name","Ip Address","UserName","Password" };
				
				*{"ITMS No*","Database Type*","HostName*", "Ip Address*", "Port Number*", "Driver Name*",
				"Database Name*", "UserName*","Password*" };

				*
				*/
			//final Integer LineNo=(list.get(0) != null ) ? Integer.parseInt(list.get(0).toString()) : null;
			final String ItmsNo=(list.get(0) != null ) ? list.get(0).toString() : null;
			
			final String DatabaseType=(list.get(1) !=null && list.get(1)!="")?(list.get(1).toString()):null;
			final String HostName=(list.get(2) !=null && list.get(2)!="")?(list.get(2).toString()):null;
			
			final String IpAddress=(list.get(3) !=null && list.get(3)!="")?(list.get(3).toString()):null;
			final String PortNo=(list.get(4) != null &&list.get(4) != ""  ) ? list.get(4).toString() : null;
			final String DriverName=(list.get(5) !=null && list.get(5)!="")?(list.get(5).toString()):null;
			final String DatabaseName=(list.get(6) !=null && list.get(6)!="")?(list.get(6).toString()):null;
			final String UserName=(list.get(7) !=null && list.get(7)!="")?(list.get(7).toString()):null;
			final String Password=(list.get(8) !=null && list.get(8)!="")?(list.get(8).toString()):null;
			
			DBconfigdatatableVo vo=new DBconfigdatatableVo();
			//vo.setId(LineNo);
			vo.setItmsNo(ItmsNo);
			vo.setDatabaseName(DatabaseName);
			vo.setDatabaseType(DatabaseType);
			vo.setHostName(HostName);
			vo.setPortNumber(PortNo);
			vo.setDriverName(DriverName);
			vo.setIpAddress(IpAddress);
			vo.setUserName(UserName);
			vo.setPassword(Password);
			 errorMsg = validateUpload(vo,allitmsList,alldbList);
			
			if (errorMsg.length() == 0) {
				successRecords++;
				
				successRecordList.add(vo);
			} else {

				vo.setErrorMsg(errorMsg);
				failureRecords++;
				failedRecordList.add(vo);
			}
			totalRecordList.add(vo);
		}
		
		for (DBconfigdatatableVo vo : successRecordList) {
			System.out.println("allitmslist"+allitmsList);
			if(!allitmsList.contains(Integer.parseInt(vo.getItmsNo()))) {
				System.out.println("alldbList"+alldbList);
				if(!alldbList.contains(vo.getDatabaseName())) {
					
					
					insertRecordList.add(vo);
				}
			}
			else {
				updationRecordList.add(vo);
			}
			
		}
		
		if (insertRecordList.size() > 0)
			addRowsRecords(insertRecordList); // save and update records
		if (updationRecordList.size() > 0) {
			updateRowRecords(updationRecordList);
		}
	}
	catch (final IOException e) {
		e.printStackTrace();
	} catch(Exception e) {
		e.printStackTrace();
	}
	
	finally {
		if (fis != null)
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	finalFile = ExcelUtils.logExcelGeneration(totalRecordList, "Srinivas", headerNames, sheetData.size(), failureRecords,
			successRecords, failedRecordList, insertRecordList, updationRecordList);
	
			return finalFile;
		}
	
	


private String validateUpload(DBconfigdatatableVo vo, List<Integer> allitmsList, List<String> alldbList) {
			// TODO Auto-generated method stub
	Set<String> uniqueDBName=new HashSet<>();
	String errorMsg="";
	if (vo.getItmsNo() == null ) {
		errorMsg += "ITMS NO. is mandatory";
	} else if (!CommonUtils.isNumeric(vo.getItmsNo().toString())) {
		if (!errorMsg.equals("")) {
			errorMsg += ", ";
		}
		errorMsg += " ITMS NO. should be Numeric";
	} 
	

	else if (vo.getItmsNo().toString().length() >5 ||vo.getItmsNo().toString().length() <5) {
		if (!errorMsg.equals("")) {
			errorMsg += ", ";
		}
		errorMsg += "ITMS NO length must be 5 digits";
	}
	if(vo.getItmsNo()!=null && CommonUtils.isNumeric(vo.getItmsNo().toString())) {
	T_Appl_Dtl_004 itms=  daoapp.findBydat001ApplItmsNum(Integer.parseInt(vo.getItmsNo())); 
	if(itms==null) {
			if (!errorMsg.equals("")) {
				errorMsg += ", ";
			}
			errorMsg += "Please provide valid  ITMS NO";
		}
	}
	
	
	if (vo.getDatabaseName() == null || vo.getDatabaseName() == "") {
		if (!errorMsg.equals("")) {
			errorMsg += ", ";
		}
		errorMsg += "Database Name is mandatory";
	}else if (vo.getDatabaseName().length() > 50) {
		if (!errorMsg.equals("")) {
			errorMsg += ", ";
		}
		errorMsg += "Database Name should not be greater than 50";
	}else if(!CommonUtils.isAlphaNumeric(vo.getDatabaseName())) {
		
		if (!errorMsg.equals("")) {
			errorMsg += ", ";
		}
		errorMsg += "Database Name should  be characters only";
	}else if(!uniqueDBName.add(vo.getDatabaseName().toString())) {
		if (!errorMsg.equals("")) {
			errorMsg += ", ";
		}
		errorMsg += "Database Name  is repeated";
	}
	
	
	if (vo.getDatabaseType() == null || vo.getDatabaseType() == "") {
		if (!errorMsg.equals("")) {
			errorMsg += ", ";
		}
		errorMsg += "Database Type is mandatory";
	}
	else if (vo.getDatabaseType().length() > 15) {
		if (!errorMsg.equals("")) {
			errorMsg += ", ";
		}
		errorMsg += "Database Type should not be greater than 15";
	}
	
	
	if (vo.getHostName() == null || vo.getHostName() == "") {
		if (!errorMsg.equals("")) {
			errorMsg += ", ";
		}
		errorMsg += "HostName is mandatory";
	}else if (vo.getHostName().length() > 100) {
		if (!errorMsg.equals("")) {
			errorMsg += ", ";
		}
		errorMsg += "Host Name should not be greater than 15";
	}

	
	if (vo.getPortNumber() == null ) {
		errorMsg += "Port Number  is mandatory";
	} else if (!CommonUtils.isNumeric(vo.getPortNumber().toString())) {
		if (!errorMsg.equals("")) {
			errorMsg += ", ";
		}
		errorMsg += "Port Number should be Numeric";
	} else if (vo.getPortNumber().toString().length() >5) {
		if (!errorMsg.equals("")) {
			errorMsg += ", ";
		}
		errorMsg += "Port Number should not be greater than 5";
	}
	
	
	if (vo.getDriverName() == null || vo.getDriverName() == "") {
		if (!errorMsg.equals("")) {
			errorMsg += ", ";
		}
		errorMsg += "Driver Name is mandatory";
	}
	else if (vo.getDriverName().length() > 250) {
		if (!errorMsg.equals("")) {
			errorMsg += ", ";
		}
		errorMsg += "Driver Name should not be greater than 250";
	}

	
	if (vo.getIpAddress()== null || vo.getIpAddress() == "") {
		if (!errorMsg.equals("")) {
			errorMsg += ", ";
		}
		errorMsg += "IP Address is mandatory";
	}else if (vo.getIpAddress().length() > 15) {
		if (!errorMsg.equals("")) {
			errorMsg += ", ";
		}
		errorMsg += "Ip Address should not be greater than 15";
	}else if (!CommonUtils.isNumericip(vo.getIpAddress().toString())) {
		if (!errorMsg.equals("")) {
			errorMsg += ", ";
		}
		errorMsg += "IP Address should be Numeric";
	}

	if (vo.getPassword()== null || vo.getPassword() == "") {
		if (!errorMsg.equals("")) {
			errorMsg += ", ";
		}
		errorMsg += "Password is mandatory";
	}else if (vo.getPassword().length() > 25) {
		if (!errorMsg.equals("")) {
			errorMsg += ", ";
		}
		errorMsg += "Password should not be greater than 25";
	}

	if (vo.getUserName()== null || vo.getUserName() == "") {
		if (!errorMsg.equals("")) {
			errorMsg += ", ";
		}
		errorMsg += "User Name is mandatory";
	}
	else if (vo.getUserName().length() > 8) {
		if (!errorMsg.equals("")) {
			errorMsg += ", ";
		}
		errorMsg += "Password should not be greater than 8";
	}

	if(vo.getItmsNo()!=null && CommonUtils.isNumeric(vo.getItmsNo().toString())) {
if(!allitmsList.contains(Integer.parseInt(vo.getItmsNo()))) {
	if(alldbList.contains(vo.getDatabaseName())) {
		if (!errorMsg.equals("")) {
			errorMsg += ", ";
		}
		errorMsg+="Database Name is already existed please try again";
	}
}
	}

if(vo.getDatabaseName()!=null && vo.getItmsNo()!=null && CommonUtils.isNumeric(vo.getItmsNo().toString())){
if(allitmsList.contains(Integer.parseInt(vo.getItmsNo()))) {
	String dbName=dao.uniqueDBName(vo.getDatabaseName().toUpperCase(),Integer.parseInt(vo.getItmsNo().split("-")[0]));
	if(dbName!=null) {
		if (!errorMsg.equals("")) {
			errorMsg += ", ";
		}
		errorMsg+="Database Name is already existed please try agains";
	}
}
}

return errorMsg;
		}




public List<ArrayList<Object>> uploadedFile(InputStream fis, int lastColumn, boolean isXlsx, String fileN,
		String param) throws IOException {
	List<ArrayList<Object>> rowData = new ArrayList<ArrayList<Object>>();
	ArrayList<Object> dataCell = null;

	XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
	XSSFSheet mySheet = myWorkBook.getSheetAt(0);
	Iterator<Row> rowIterator = mySheet.iterator();

	while (rowIterator.hasNext()) {
		Row row = rowIterator.next();
		dataCell = new ArrayList<Object>();

		for (int cellCounter = 0; cellCounter < lastColumn; cellCounter++) {
			Cell cell = row.getCell(cellCounter);

			if (cell != null) {
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_BLANK:
					dataCell.add(null);
					break;
				case Cell.CELL_TYPE_STRING:
					dataCell.add(
							!cell.getStringCellValue().trim().equals("") ? cell.getStringCellValue().trim() : null);
					break;
				case Cell.CELL_TYPE_NUMERIC:
					dataCell.add(cell.getNumericCellValue());
					break;

				}
			} else {
				dataCell.add(null);
			}

		} // End Cell Loop

		rowData.add(dataCell);
		// System.out.println();
	} // End Row Loop

	return rowData;
}
public Map<Integer, String> getItmsDbN() {
	Iterable<T_APPL_DB_DTL_010> List = dao.findAll();
	Map<Integer, String> Map = new HashMap<Integer, String>();
	if (List != null) {
		for (T_APPL_DB_DTL_010 Bo : List) {
			Map.put(Bo.getItmsNo(), Bo.getDatabasename());
		}
	}
	return Map;
}



public List<String> searchProjectName(String pn) {
	System.out.println("service");
	List<String> obj = DBconfigCustomQry.searchProjectName(pn);
	return obj;
}






public Set getDatabaseTye() {
	// TODO Auto-generated method stub
	
	Set<String> set=new HashSet<String>();
	@SuppressWarnings("unchecked")
	List<E_MDAT006_PARM>	E= I_MDAT006_PARM.findByParamName("DBtype");
	
	
	for (E_MDAT006_PARM pojo : E) {
		set.add(pojo.getParameterCode());
		
	}
	return set;
}


}
	

	

	

