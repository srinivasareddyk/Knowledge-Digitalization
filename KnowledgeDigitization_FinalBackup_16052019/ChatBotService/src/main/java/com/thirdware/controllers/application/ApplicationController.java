package com.thirdware.controllers.application;


import java.io.File;
import java.io.IOException;
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

import com.thirdware.services.application.ApplicationService;
import com.thirdware.vo.application.ApplicationVo;

@RestController
@RequestMapping("/Application")
public class ApplicationController {

	@Autowired
	ApplicationService applicationService;

	

	@RequestMapping("/ItmsNoOnload")
	public List<String> getItmsNo() {

		return applicationService.getAllItmsNo();
	}
	
	
	 
	/*
	 * , @RequestParam(value="ApplicationMaintenanceSearchList")
	 * List<String> ApplicationMaintenanceSearchList
	 */
	@RequestMapping(value="/applicationSearch", method=RequestMethod.POST)
	public List<ApplicationVo> getApplicationSearchResult(@RequestBody ApplicationVo appliVo) {
		
		return applicationService.getApplicationMaintenSrchInfo(appliVo);
	}
	
	
	@RequestMapping("/saveAppliMaintenance")
	public ApplicationVo save(@RequestBody List<ApplicationVo> appliMaintenanceList) {
		ApplicationVo appliVo = new ApplicationVo();

		appliVo = applicationService.validate(appliMaintenanceList, true);
		if (appliVo.getErrors().isEmpty()) {
			applicationService.saveAppliMainten(appliMaintenanceList);

		}
		
		return appliVo;
	}
	
	
	@RequestMapping("/updateAppliMaintenance")
	public ApplicationVo update(@RequestBody List<ApplicationVo> appliMaintenanceList) {

		ApplicationVo appliVo = new ApplicationVo();

		appliVo = applicationService.validate(appliMaintenanceList, false);
		if (appliVo.getErrors().isEmpty()) {
			applicationService.updateAppliMainten(appliMaintenanceList);
		}
		
		return appliVo;
	}
	
	
	@RequestMapping(value = "/downloadTemplate", method = RequestMethod.GET, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public ResponseEntity<byte[]> downloadTemplate() throws Exception {

		File logFile = applicationService.dowloadTemplate();

		if (!logFile.exists()) { // handle FNF
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

		try {
			FileSystemResource fileResource = new FileSystemResource(logFile);

			byte[] base64Bytes = Base64Utils.encode(IOUtils.toByteArray(fileResource.getInputStream()));

			HttpHeaders headers = new HttpHeaders();
			headers.add("filename", "Application Maintenace");

			return ResponseEntity.ok().headers(headers).body(base64Bytes);
		} catch (IOException e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}
	
	@RequestMapping(value = "/downloadExcel", method = RequestMethod.POST, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public ResponseEntity<byte[]> downloadExportExcel(@RequestBody List<ApplicationVo> dsid) throws Exception {

		;

		File logFile = applicationService.dowloadExcel(dsid);

		if (!logFile.exists()) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

		try {
			FileSystemResource fileResource = new FileSystemResource(logFile);

			byte[] base64Bytes = Base64Utils.encode(IOUtils.toByteArray(fileResource.getInputStream()));

			HttpHeaders headers = new HttpHeaders();
			headers.add("filename", "Application Maintenace");

			return ResponseEntity.ok().headers(headers).body(base64Bytes);
		} catch (IOException e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}
	
	@RequestMapping(value = "/ExcelUpload", method = RequestMethod.POST, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public ResponseEntity<byte[]> downloadLogExcel(@RequestParam("file") MultipartFile file) throws Exception {

		File logFile = applicationService.fileUpload(file);

		if (!logFile.exists()) { // handle FNF
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

		try {
			FileSystemResource fileResource = new FileSystemResource(logFile);

			byte[] base64Bytes = Base64Utils.encode(IOUtils.toByteArray(fileResource.getInputStream()));

			HttpHeaders headers = new HttpHeaders();
			headers.add("filename", "Application Maintenace");

			return ResponseEntity.ok().headers(headers).body(base64Bytes);
		} catch (IOException e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}
	
	@RequestMapping(path="/searchApplicationName",method=RequestMethod.POST)
	public List<String> searchApplicationName(@RequestBody String pn){
		return applicationService.searchApplicationName(pn);
	}
}
