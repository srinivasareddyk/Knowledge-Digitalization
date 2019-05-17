package com.thirdware.controllers.dbconfig;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.thirdware.services.dbconfig.DBconfigService;
import com.thirdware.vo.dbconfig.DBconfigSearchVo;
import com.thirdware.vo.dbconfig.DBconfigdatatableVo;





@RestController
@RequestMapping("/DbConfig")
public class DBconfigController {
	@Autowired
	DBconfigService DBconfigService;
	
	@RequestMapping(path = "/getAllRecords", method = RequestMethod.GET)
	public List<DBconfigdatatableVo> getAllRecords() {
		
		return DBconfigService.getAllRecords();
	}
	
	
	
	@RequestMapping(path="/getitms",method=RequestMethod.GET)
	public List<DBconfigdatatableVo> getitms(){
		return DBconfigService.getitms();
	}
	
	@RequestMapping(path = "/searchProjectName/{string}", method = RequestMethod.POST)
	public List<String> searchProjectName(@PathVariable("string") String pn) {
		System.out.println("Entering into Maintain Project Controller-->searchProjectName()"+pn);
		return DBconfigService.searchProjectName(pn);
	}
	
	
	@RequestMapping(path = "/addRowRecords", method = RequestMethod.POST, produces = "text/plain")
	public String addRowsRecords(@RequestBody List<DBconfigdatatableVo> addRow) {
		return DBconfigService.addRowsRecords(addRow);
	}

	@RequestMapping(path = "/updateRowRecords", method = RequestMethod.PUT, produces = "text/plain")
	public String updateRowRecords(@RequestBody List<DBconfigdatatableVo> updateRow) {
		return DBconfigService.updateRowRecords(updateRow);
	}
	
	@RequestMapping(value="/getsearchrecords",method = RequestMethod.POST)
	public List<DBconfigdatatableVo> getsearchrecords(@RequestBody DBconfigSearchVo searchdata ){
		
		return DBconfigService.search(searchdata);
	}
	
	/*@RequestMapping(value="/getsearchrecords",method = RequestMethod.POST)
	public String getsearchrecords(@RequestBody DBconfigSearchVo searchdata ){
		System.out.println("binfding");
		return "hello";
	}*/
	
	
	@RequestMapping(value="/databaseTpe",method = RequestMethod.GET)
	public Set getDbType(){
	System.out.println("binfding");
	return DBconfigService.getDatabaseTye();
	}

	@RequestMapping(value = "/downloadTemplate", method = RequestMethod.GET, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public ResponseEntity<byte[]> downloadTemplate() throws Exception {

		

		File logFile = DBconfigService.dowloadTemplate();

		if (!logFile.exists()) { // handle FNF
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

		try {
			FileSystemResource fileResource = new FileSystemResource(logFile);

			byte[] base64Bytes = Base64Utils.encode(IOUtils.toByteArray(fileResource.getInputStream()));

			HttpHeaders headers = new HttpHeaders();
			headers.add("filename", "Database Config");

			return ResponseEntity.ok().headers(headers).body(base64Bytes);
		} catch (IOException e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}
	
	@RequestMapping(value="/downloadExcel", method = RequestMethod.POST,produces ="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<byte[]> downloadExportExcel(@RequestBody List<DBconfigdatatableVo>  Vo) throws Exception {
                 
           File logFile=DBconfigService.dowloadExcel(Vo);
           
           if (!logFile.exists()) { 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        try {
            FileSystemResource fileResource = new FileSystemResource(logFile);

            byte[] base64Bytes = Base64Utils.encode(IOUtils.toByteArray(fileResource.getInputStream()));

            HttpHeaders headers = new HttpHeaders();
            headers.add("filename", "Database Configuration");

            return ResponseEntity.ok().headers(headers).body(base64Bytes);
        } catch (IOException e) 
        {
          
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    

    
 }
    
    
    
	@RequestMapping(value = "/ExcelUpload", method = RequestMethod.POST, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public ResponseEntity<byte[]> downloadLogExcel(@RequestParam("file") MultipartFile file) throws Exception {

		
		File logFile = DBconfigService.fileUpload(file);

		if (!logFile.exists()) { // handle FNF
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

		try {
			FileSystemResource fileResource = new FileSystemResource(logFile);
			byte[] base64Bytes = Base64Utils.encode(IOUtils.toByteArray(fileResource.getInputStream()));
			HttpHeaders headers = new HttpHeaders();
			headers.add("filename", "Database Configuration");
			return ResponseEntity.ok().headers(headers).body(base64Bytes);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
    

}
