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

import com.thirdware.services.configSubModuleService;
import com.thirdware.vo.SelectedItem;
import com.thirdware.vo.module.ModuleVO;
import com.thirdware.vo.submodule.SubModuleVO;

@RestController
@RequestMapping("/configsubmodule")
public class configSubModuleController {
	@Autowired
	configSubModuleService submoduleservice;
	
	
	@RequestMapping("/getItmsNoAndAcrnym")
	public List<String> getItmsAcronym(){
		return submoduleservice.getItmsNoWithAcrnym();

	}
	
	@RequestMapping("/getItmsNoAndAppName")
	public List<SelectedItem> getITMSNoAndApplName(){
		return submoduleservice.getItmsNoWithAppName();

	}
	@RequestMapping(value="/getModuleCodeName",method=RequestMethod.POST)
	public List<SelectedItem> getModuleCodeName(@RequestBody SelectedItem itms){
		return submoduleservice.getModuleCodeNameByITMS(itms);

	}
	
	@RequestMapping(value="/getModuleCodeNameStrList",method=RequestMethod.POST)
	public List<String> getModuleCodeName(@RequestBody  List<String> itmsList){
		return submoduleservice.getModuleCodeName(itmsList);

	}
	@RequestMapping(value="/getSubModuleList",method=RequestMethod.POST)
	public List<String> getSubModuleList(@RequestBody  List<String> itmsList){
		return submoduleservice.getSubModuleList(itmsList);

	}
	
	@RequestMapping(value="/savesubmodules",method = RequestMethod.POST)
	public SubModuleVO save(@RequestBody List<SubModuleVO> dataList) {
		SubModuleVO vo=new SubModuleVO();
		vo=submoduleservice.validate(dataList, true);
		if(vo.getErrors().isEmpty() && vo.getErrors().size()==0) {
			submoduleservice.processSave(dataList);
		}
		return vo;
	}
	
	@RequestMapping(value="/updatesubmodules",method = RequestMethod.POST)
	public SubModuleVO update(@RequestBody List<SubModuleVO> dataList) {
		SubModuleVO vo=new SubModuleVO();
		vo=submoduleservice.validate(dataList, false);
		if(vo.getErrors().isEmpty() && vo.getErrors().size()==0) {
			submoduleservice.processUpdate(dataList);
		}
		return vo;
	}
	
	@RequestMapping(value="/fetchsubmodules",method = RequestMethod.POST)
	public List<SubModuleVO> search(@RequestBody  SubModuleVO vo)  {
		List<SubModuleVO> searchList = new ArrayList<SubModuleVO>();
		searchList=submoduleservice.searchForSubModules(vo);
		return searchList;
	}
	
	@RequestMapping(value="/downloadExcel", method = RequestMethod.POST,produces ="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<byte[]> downloadExportExcel(@RequestBody  List<SubModuleVO>  list) throws Exception {
                 
           //File logFile=new File("C:\\PSAR\\Fault Maintenance.xlsx");
           
           File logFile=submoduleservice.dowloadExcel(list);
           
           if (!logFile.exists()) { // handle FNF
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        try {
            FileSystemResource fileResource = new FileSystemResource(logFile);

            byte[] base64Bytes = Base64Utils.encode(IOUtils.toByteArray(fileResource.getInputStream()));

            HttpHeaders headers = new HttpHeaders();
            headers.add("filename", "SubModule Maintenace");

            return ResponseEntity.ok().headers(headers).body(base64Bytes);
        } catch (IOException e) 
        {
          
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    

    
 }
    
    @RequestMapping(value="/downloadTemplate", produces ="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<byte[]> downloadTemplate() throws Exception {
                 
           //File logFile=new File("C:\\PSAR\\Fault Maintenance.xlsx");
           
           File logFile=submoduleservice.dowloadTemplate();
           
           if (!logFile.exists()) { // handle FNF
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        try {
            FileSystemResource fileResource = new FileSystemResource(logFile);

            byte[] base64Bytes = Base64Utils.encode(IOUtils.toByteArray(fileResource.getInputStream()));

            HttpHeaders headers = new HttpHeaders();
            headers.add("filename", "SubModule Maintenace");

            return ResponseEntity.ok().headers(headers).body(base64Bytes);
        } catch (IOException e) 
        {
          
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    

    
 }
    
    
    @RequestMapping(value="/ExcelUpload", method = RequestMethod.POST,produces ="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<byte[]> downloadLogExcel(@RequestParam("file") MultipartFile file) throws Exception {
                 
           //File logFile=new File("C:\\PSAR\\Fault Maintenance.xlsx");
           
           File logFile=submoduleservice.fileUpload(file);
           
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
