package com.thirdware.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.thirdware.entity.T_Appl_Dtl_004;
import com.thirdware.services.ConfigScreenService;
import com.thirdware.vo.SelectedItem;
import com.thirdware.vo.ScreenVO;
import com.thirdware.vo.SearchVO;

@RestController
@RequestMapping("/configscreen")
public class ConfigScreenController {
	
	@Autowired
	ConfigScreenService moduleservice;
	
	HashSet<String> entrydupUpldSet = new HashSet<String>();	
	
	@RequestMapping("/getItmsNo")
	public List<Integer> getITMSNo(){
		return moduleservice.getAllITMSNo();

	}
	
	@RequestMapping("/getItmsNoAndAppName")
	public List<SelectedItem> getITMSNoAndApplName(){
		return moduleservice.getItmsNoWithAppName();

	}

	
	@RequestMapping("/getItmsNoAndAcrnym")
	public List<String> getItmsAcronym(){
		return moduleservice.getItmsNoWithAcrnym();

	}
	
	@RequestMapping(value="/getModuleCodeName",method=RequestMethod.POST)
	public List<SelectedItem> getModuleCodeName(@RequestBody SelectedItem itms){
		return moduleservice.getModuleCodeName(itms);

	}
	
	@RequestMapping(value="/getSubModuleCodeName",method=RequestMethod.POST)
	public List<SelectedItem> getSubModuleCodeName(@RequestBody SelectedItem module){
		return moduleservice.getSubModuleCodeName(module);

	}
	
	@RequestMapping(value="/getParentModule",method=RequestMethod.POST)
	public List<String> getModule(@RequestBody  List<String> itmsList){
		return moduleservice.getModule(itmsList);

	}
	
	@RequestMapping(value="/getSubModule",method=RequestMethod.POST)
	public List<String> getSubModule(@RequestBody List<String> itmsList){
		return moduleservice.getSubModule(itmsList);

	}
	
	@RequestMapping(value="/getScreen",method=RequestMethod.POST)
	public List<String> getScreen(@RequestBody List<String> itmsList){
		return moduleservice.getScreen(itmsList);

	}
	
	@RequestMapping(value="/savemodules",method = RequestMethod.POST)
	public ScreenVO save(@RequestBody List<ScreenVO> dataList) {
		ScreenVO vo=new ScreenVO();
		vo=moduleservice.validate(dataList, true);
		if(vo.getErrors().isEmpty()) {
			 moduleservice.processSave(dataList);
		}
		return vo;
	}
	
	@RequestMapping(value="/updatemodules",method = RequestMethod.POST)
	public ScreenVO update(@RequestBody List<ScreenVO> dataList) {
		ScreenVO vo=new ScreenVO();
		vo=moduleservice.validate(dataList, false);
		if(vo.getErrors().isEmpty()) {
		 moduleservice.processUpdate(dataList);
		}
		return vo;
	}
	
	@RequestMapping(value="/fetchmodules",method = RequestMethod.POST)
	public List<ScreenVO> search(@RequestBody  SearchVO vo)  {
		List<ScreenVO> searchList = new ArrayList<ScreenVO>();
		searchList=moduleservice.searchForModules(vo);
		return searchList;
	}
	
	  @RequestMapping(value="/downloadExcel", method = RequestMethod.POST,produces ="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	    public ResponseEntity<byte[]> downloadExportExcel(@RequestBody  List<ScreenVO>  list) throws Exception {
	                 
	           //File logFile=new File("C:\\PSAR\\Fault Maintenance.xlsx");
	           
	           File logFile=moduleservice.dowloadExcel(list);
	           
	           if (!logFile.exists()) { // handle FNF
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }

	        try {
	            FileSystemResource fileResource = new FileSystemResource(logFile);

	            byte[] base64Bytes = Base64Utils.encode(IOUtils.toByteArray(fileResource.getInputStream()));

	            HttpHeaders headers = new HttpHeaders();
	            headers.add("filename", "Screen Maintenace");

	            return ResponseEntity.ok().headers(headers).body(base64Bytes);
	        } catch (IOException e) 
	        {
	          
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    

	    
	 }
	    
	    
	    @RequestMapping(value="/downloadTemplate", produces ="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	    public ResponseEntity<byte[]> downloadTemplate() throws Exception {
	                 
	           //File logFile=new File("C:\\PSAR\\Fault Maintenance.xlsx");
	           
	           File logFile=moduleservice.dowloadTemplate();
	           
	           if (!logFile.exists()) { // handle FNF
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }

	        try {
	            FileSystemResource fileResource = new FileSystemResource(logFile);

	            byte[] base64Bytes = Base64Utils.encode(IOUtils.toByteArray(fileResource.getInputStream()));

	            HttpHeaders headers = new HttpHeaders();
	            headers.add("filename", "DSID Maintenace");

	            return ResponseEntity.ok().headers(headers).body(base64Bytes);
	        } catch (IOException e) 
	        {
	          
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    

	    
	 }
	    
	    
	    @RequestMapping(value="/ExcelUpload", method = RequestMethod.POST,produces ="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	    public ResponseEntity<byte[]> downloadLogExcel(@RequestParam("file") MultipartFile file) throws Exception {
	                 
	           //File logFile=new File("C:\\PSAR\\Fault Maintenance.xlsx");
	           
	           File logFile=moduleservice.fileUpload(file);
	           
	           if (!logFile.exists()) { // handle FNF
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }

	        try {
	            FileSystemResource fileResource = new FileSystemResource(logFile);

	            byte[] base64Bytes = Base64Utils.encode(IOUtils.toByteArray(fileResource.getInputStream()));

	            HttpHeaders headers = new HttpHeaders();
	            headers.add("filename", "DSID Maintenace");

	            return ResponseEntity.ok().headers(headers).body(base64Bytes);
	        } catch (IOException e) 
	        {
	          
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    

	    
	 }
	    
	    

		
}
