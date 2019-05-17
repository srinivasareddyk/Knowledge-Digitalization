package com.thirdware.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.util.IOUtils;
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

import com.thirdware.services.ConfigModuleService;
import com.thirdware.vo.ScreenVO;
import com.thirdware.vo.SelectedItem;
import com.thirdware.vo.module.ModuleVO;

@RestController
@RequestMapping("/configmodule")
public class configModuleController {
	
	@Autowired
	ConfigModuleService moduleservice;
	
	@RequestMapping("/getItmsNoAndAcrnym")
	public List<String> getItmsAcronym(){
		return moduleservice.getItmsNoWithAcrnym();

	}
	
	@RequestMapping("/getItmsNoAndAppName")
	public List<SelectedItem> getITMSNoAndApplName(){
		return moduleservice.getItmsNoWithAppName();

	}
	
	@RequestMapping(value="/getParentModule",method=RequestMethod.POST)
	public List<String> getModule(@RequestBody  List<String> itmsList){
		return moduleservice.getModule(itmsList);

	}
	
	@RequestMapping(value="/getModuleCode",method=RequestMethod.POST)
	public List<String> getModuleCode(@RequestBody  List<String> itmsList){
		return moduleservice.getModuleCode(itmsList);

	}
	@RequestMapping(value="/getModuleCodeName",method=RequestMethod.POST)
	public List<String> getModuleCodeName(@RequestBody  List<String> itmsList){
		return moduleservice.getModuleCodeName(itmsList);

	}
	@RequestMapping(value="/savemodules",method = RequestMethod.POST)
	public ModuleVO save(@RequestBody List<ModuleVO> dataList) {
		ModuleVO vo=new ModuleVO();
		vo=moduleservice.validate(dataList, true);
		if(vo.getErrors().isEmpty() && vo.getErrors().size()==0) {
			moduleservice.processSave(dataList);
		}
		return vo;
	}
	
	@RequestMapping(value="/updatemodules",method = RequestMethod.POST)
	public ModuleVO update(@RequestBody List<ModuleVO> dataList) {
		ModuleVO vo=new ModuleVO();
		vo=moduleservice.validate(dataList, false);
		if(vo.getErrors().isEmpty() && vo.getErrors().size()==0) {
			moduleservice.processUpdate(dataList);
		}
		return vo;
	}
	
	@RequestMapping(value="/fetchmodules",method = RequestMethod.POST)
	public List<ModuleVO> search(@RequestBody  ModuleVO vo)  {
		List<ModuleVO> searchList = new ArrayList<ModuleVO>();
		searchList=moduleservice.searchForModules(vo);
		return searchList;
	}
	
	 @RequestMapping(value="/downloadExcel", method = RequestMethod.POST,produces ="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	    public ResponseEntity<byte[]> downloadExportExcel(@RequestBody  List<ModuleVO>  list) throws Exception {
	                 
	           //File logFile=new File("C:\\PSAR\\Fault Maintenance.xlsx");
	           
	           File logFile=moduleservice.dowloadExcel(list);
	           
	           if (!logFile.exists()) { // handle FNF
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }

	        try {
	            FileSystemResource fileResource = new FileSystemResource(logFile);

	            byte[] base64Bytes = Base64Utils.encode(IOUtils.toByteArray(fileResource.getInputStream()));

	            HttpHeaders headers = new HttpHeaders();
	            headers.add("filename", "Module Maintenace");

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
	            headers.add("filename", "Module Maintenace");

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
	            headers.add("filename", "Module Maintenace");

	            return ResponseEntity.ok().headers(headers).body(base64Bytes);
	        } catch (IOException e) 
	        {
	          
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	        }
	    

	    
	 }
	    
}
