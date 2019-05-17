package com.thirdware.controllers.configUsrManual;


import java.io.File;


import java.io.IOException;
import java.sql.SQLException;
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

import com.thirdware.services.configUsrManual.ConfigUsrManualService;
import com.thirdware.vo.SelectedItem;
import com.thirdware.vo.configUsrManual.ConfigUsrManualVo;

@RestController
@RequestMapping("/configUsrManual")
public class ConfigUsrManualController {

	@Autowired
	ConfigUsrManualService configUsrManualService;
	
	@RequestMapping("/searchModuleOnload")
	public List<String> getSearchModuleList() {

		return configUsrManualService.getSearchInputModuleSubMaduleValues("MD");
	}
	
	@RequestMapping("/searchSubModuleOnload")
	public List<String> getSearchSubModuleList() {

		return configUsrManualService.getSearchInputModuleSubMaduleValues("SM");
	}
	
	@RequestMapping("/searchScreenNameOnload")
	public List<String> getSearchScreenNameList() {

		return configUsrManualService.getSearchInputModuleSubMaduleValues("SC");
	}
	
	@RequestMapping("/applItmsNoOnload")
	public List<SelectedItem> getApplItmsNoList() {

		return configUsrManualService.getAllApplicationItmsNo();
	}
	
	@RequestMapping("/applItmsNoAndDescOnload")
	public List<String> applItmsNoAndDescList() {

		return configUsrManualService.getAllApplItmsNoAndDesc();
	}
	
	@RequestMapping("/onChangeModuleOnload")
	public List<SelectedItem> getModuleListForOnChange(@RequestBody ConfigUsrManualVo configUsrManualVo){	
		return configUsrManualService.getModuleListForOnChange(configUsrManualVo);
	}
	
	@RequestMapping("/onChangeSubModuleOnload")
	public List<SelectedItem> getSubModuleListForOnChange(@RequestBody ConfigUsrManualVo configUsrManualVo){	
		return configUsrManualService.getSubModuleScreenNameListForOnChange(configUsrManualVo.getModule());
	}
	
	@RequestMapping("/onChangeScreenNameOnload")
	public List<SelectedItem> getScreenNameListForOnChange(@RequestBody ConfigUsrManualVo configUsrManualVo){	
		return configUsrManualService.getSubModuleScreenNameListForOnChange(configUsrManualVo.getSubModule());
	}
	
	@RequestMapping("/toValidateCombination")
	public ConfigUsrManualVo toValidateModuleSubModuleCombination(@RequestBody ConfigUsrManualVo configUsrManualVo){	
		return configUsrManualService.toValidateModuleSubModuleCombination(configUsrManualVo.getScreenName());
	}
	
	@RequestMapping("/toValidateCombinationForEdit")
	public ConfigUsrManualVo toValidateModuleSubModuleCombinationForEdit(@RequestBody ConfigUsrManualVo configUsrManualVo){	
		return configUsrManualService.toValidateModuleSubModuleCombinationForEdit(configUsrManualVo.getScreenName(),configUsrManualVo.getConfigSakeyId());
	}
	
	/*
	 * , @RequestParam(value="dsidSearchList")
	 * List<String> dsidSearchList
	 */
	@RequestMapping(value="/configUsrManualSearch", method=RequestMethod.POST)
	public List<ConfigUsrManualVo> getConfigUsrSearchResult(@RequestBody ConfigUsrManualVo configUsrManualVo) {
		
		//System.out.println("Application List : "+configUsrManualVo.getSearchApplItmsList());
		System.out.println("Manual List : "+configUsrManualVo.getSearchModuleList());
		System.out.println("Sub Manual List : "+configUsrManualVo.getSearchSubModuleList());
		System.out.println("Screen List : "+configUsrManualVo.getSearchScreenNameList());
		
		return configUsrManualService.getConfigUsrManualSrchInfo(configUsrManualVo);
	}
	
	
	@RequestMapping("/saveConfigUsrManual")
	public ConfigUsrManualVo save(@RequestBody List<ConfigUsrManualVo> configUsrManualList) {
		ConfigUsrManualVo cmuVo = new ConfigUsrManualVo();

		try {
			cmuVo = configUsrManualService.validate(configUsrManualList, true);
			if (cmuVo.getErrors().isEmpty()) {
				configUsrManualService.saveConfigUsrManual(configUsrManualList);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cmuVo;
	}
	
	
	@RequestMapping("/updateConfigUsrManual")
	public ConfigUsrManualVo update(@RequestBody List<ConfigUsrManualVo> configUsrManualList) {
		ConfigUsrManualVo cmuVo = new ConfigUsrManualVo();

		try {
			cmuVo = configUsrManualService.validate(configUsrManualList, false);
			if (cmuVo.getErrors().isEmpty()) {
				configUsrManualService.updateConfigUsrManual(configUsrManualList);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cmuVo;
	}
	
	
	@RequestMapping(value = "/downloadExcel", method = RequestMethod.POST, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public ResponseEntity<byte[]> downloadExportExcel(@RequestBody List<ConfigUsrManualVo> configUsrManualVoList) throws Exception {

		;

		File logFile = configUsrManualService.dowloadExcel(configUsrManualVoList);

		if (!logFile.exists()) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

		try {
			FileSystemResource fileResource = new FileSystemResource(logFile);

			byte[] base64Bytes = Base64Utils.encode(IOUtils.toByteArray(fileResource.getInputStream()));

			HttpHeaders headers = new HttpHeaders();
			headers.add("filename", "Configure User Manual And Videos");

			return ResponseEntity.ok().headers(headers).body(base64Bytes);
		} catch (IOException e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}
	
	
	@RequestMapping(value = "/downloadTemplate", method = RequestMethod.GET, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public ResponseEntity<byte[]> downloadTemplate() throws Exception {

		File logFile = configUsrManualService.dowloadTemplate();

		if (!logFile.exists()) { // handle FNF
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

		try {
			FileSystemResource fileResource = new FileSystemResource(logFile);

			byte[] base64Bytes = Base64Utils.encode(IOUtils.toByteArray(fileResource.getInputStream()));

			HttpHeaders headers = new HttpHeaders();
			headers.add("filename", "Configure User Manual And Videos");

			return ResponseEntity.ok().headers(headers).body(base64Bytes);
		} catch (IOException e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}
	
	
	@RequestMapping(value = "/ExcelUpload", method = RequestMethod.POST, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public ResponseEntity<byte[]> downloadLogExcel(@RequestParam("file") MultipartFile file) throws Exception {

		File logFile = configUsrManualService.fileUpload(file);

		if (!logFile.exists()) { // handle FNF
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

		try {
			FileSystemResource fileResource = new FileSystemResource(logFile);

			byte[] base64Bytes = Base64Utils.encode(IOUtils.toByteArray(fileResource.getInputStream()));

			HttpHeaders headers = new HttpHeaders();
			headers.add("filename", "Configure User Manual And Videos");

			return ResponseEntity.ok().headers(headers).body(base64Bytes);
		} catch (IOException e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}
	
}
