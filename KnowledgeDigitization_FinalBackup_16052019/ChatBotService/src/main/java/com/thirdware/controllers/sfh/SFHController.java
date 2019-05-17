package com.thirdware.controllers.sfh;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.thirdware.services.sfh.SFHService;
import com.thirdware.vo.SfhVO;
import com.thirdware.vo.UploadForm;
import com.thirdware.vo.sfh.KeyValueHolder;
import com.thirdware.vo.sfh.SfhDropdownVo;
@RestController
@RequestMapping("/sfh")
public class SFHController {
	private static final Logger logger = LoggerFactory.getLogger(SFHController.class);
	@Autowired
	SFHService sfhService;
	
	@RequestMapping("/commonSFH")
	public ArrayList<SfhVO> getApplicationManualDetails() {
		return sfhService.getApplicationManualDetails();
	}
	@RequestMapping("/fillDropDown1")
	public HashMap<String,List<KeyValueHolder>> getDropDownList1(){
		logger.info("getDropDownList1 start");
		HashMap<String,List<KeyValueHolder>> dropDownList=new HashMap<String,List<KeyValueHolder>>();
		dropDownList.put("MODULE", sfhService.findAllModules1());
		dropDownList.put("SUBMODULE", sfhService.findAllSubModules1());
		dropDownList.put("SCREENS", sfhService.findAllScreens1());
		logger.info("getDropDownList1 end");
		return dropDownList;
	}
	@RequestMapping("/getAllItmsNumbers")
	public List<String> getAllItmsNumbers(){
		return sfhService.getAllItmsNumbers();
	}
	@PostMapping("/delete")
	public void deleteScreens(@RequestBody String screenId) {
		sfhService.deleteScreens(screenId);
	 }
	
	@RequestMapping("/getAllItmsNumbersForGrid")
	public List<Integer> getAllItmsNumbersForGrid(){
		return sfhService.findAllItmsNumbersForGrid();
	}
	
	
	@RequestMapping("/findAllModules")
	public List<String> findAllModules(@RequestBody  SfhDropdownVo  SfhVo){
		logger.info("findAllModules start");
		return sfhService.findAllModules();
	}
	
	@RequestMapping("/findAllSubModules")
	public List<String> findAllSubModules(){
		logger.info("findAllSubModules start");
		return sfhService.findAllSubModules(null);

	}
	
	@RequestMapping("/findAllScreens")
	public List<String> findAllScreens(){
		logger.info("findAllScreens start");
		return sfhService.findAllScreens(null);

	}
	
	@RequestMapping("/fillDropDown")
	public HashMap<String,List<String>> getDropDownList(@RequestBody  SfhDropdownVo  SfhVo){
		logger.info("getDropDownList start");
		HashMap<String,List<String>> dropDownList=new HashMap<String,List<String>>();
		dropDownList.put("MODULE", sfhService.findAllModules());
		dropDownList.put("SUBMODULE", sfhService.findAllSubModules(SfhVo.getModule()));
		dropDownList.put("SCREENS", sfhService.findAllScreens(SfhVo.getSubModule()));
		return dropDownList;
	}
	
	@RequestMapping("/searchDetails")
	public List<SfhVO> getDetails( @RequestBody  SfhDropdownVo  SfhVo){
		logger.info("getDetails start");
		List<SfhVO> sfhList=sfhService.findAllDetails(SfhVo.getItms(),SfhVo.getModule(),SfhVo.getSubModule(),SfhVo.getScreen());
		
		return sfhList;

	}
	
	@PostMapping("/save")
	public SfhVO save(@ModelAttribute UploadForm dataList) throws IOException{
		logger.info("save start");
		SfhVO  voObject=new SfhVO();
		voObject.setSelectedScreen(dataList.getSelectedScreen());
		if(null!=dataList.getHelpDocRequired()&&dataList.getHelpDocRequired().equals("Y")) {
			voObject.setHelpDocFileName(dataList.getHelpDocFileName());
			voObject.setHelpDocLoc(dataList.getHelpDocLoc());
			voObject.setHelpDocRequired(dataList.getHelpDocRequired());
		}else {
			voObject.setHelpDocFileName("");
			voObject.setHelpDocLoc("");
			voObject.setHelpDocRequired("N");
		}
		if(null!=dataList.getHelpVideoRequired()&&dataList.getHelpVideoRequired().equals("Y")) {
			voObject.setHelpVideoFileName(dataList.getHelpVideoFileName());
			voObject.setHelpVideoLoc(dataList.getHelpVideoLoc());
			voObject.setHelpVideoRequired(dataList.getHelpVideoRequired());
		}else {
			voObject.setHelpVideoFileName("");
			voObject.setHelpVideoLoc("");
			voObject.setHelpVideoRequired("N");
		}
		
		if(null!=dataList.getAltDocRequired()&&dataList.getAltDocRequired().equals("Y")) {
			voObject.setAltDocRequired(dataList.getAltDocRequired());
			voObject.setAltDocument(dataList.getFiles().getBytes());
			
		}else {
			voObject.setAltDocRequired("N");
		}
		/* voObject.setStatusSearch(dataList.getStatusSearch()); */
		voObject.setCreatedBy(dataList.getCreatedBy());
		voObject.setUpdatedBy(dataList.getUpdatedBy());
		sfhService.saveSfh(voObject);
		return voObject;
	}
	
	@RequestMapping(value="/ExcelUpload", method = RequestMethod.POST,produces ="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<byte[]> downloadLogExcel(@RequestParam("file") MultipartFile file,@RequestParam("user") String user) throws Exception {
    	logger.info("downloadLogExcel start");
           File logFile=sfhService.fileUpload(file,user);
           if (logFile==null||!logFile.exists()) { 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        try {
            FileSystemResource fileResource = new FileSystemResource(logFile);

            byte[] base64Bytes = Base64Utils.encode(IOUtils.toByteArray(fileResource.getInputStream()));

            HttpHeaders headers = new HttpHeaders();
            headers.add("filename", "SFH Maintenace");

            return ResponseEntity.ok().headers(headers).body(base64Bytes);
        } catch (IOException e) 
        {
          
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
 }
    
    @RequestMapping(value="/downloadTemplate", method = RequestMethod.GET,produces ="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<byte[]> downloadTemplate() throws Exception {
    	logger.info("downloadTemplate start");
           File logFile=sfhService.dowloadTemplate();
           if (!logFile.exists()) { 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        try {
            FileSystemResource fileResource = new FileSystemResource(logFile);

            byte[] base64Bytes = Base64Utils.encode(IOUtils.toByteArray(fileResource.getInputStream()));

            HttpHeaders headers = new HttpHeaders();
            headers.add("filename", "Screen Functionality Help");

            return ResponseEntity.ok().headers(headers).body(base64Bytes);
        } catch (IOException e) 
        {
          
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
 }
    @RequestMapping(value="/downloadExcel", method = RequestMethod.POST,produces ="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<byte[]> downloadExportExcel(@RequestBody  List<SfhVO> sfhList) throws Exception {
    	logger.info("downloadExportExcel start");
           File logFile=sfhService.dowloadExcel(sfhList);
           if (!logFile.exists()) { 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        try {
            FileSystemResource fileResource = new FileSystemResource(logFile);

            byte[] base64Bytes = Base64Utils.encode(IOUtils.toByteArray(fileResource.getInputStream()));

            HttpHeaders headers = new HttpHeaders();
            headers.add("filename", "SFH Maintenace");

            return ResponseEntity.ok().headers(headers).body(base64Bytes);
        } catch (IOException e) 
        {
          
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    
 }
    
    /*
	 * @RequestMapping("/test") public HashMap<String,String> getTest(
	 * HttpServletRequest request){ HashMap<String,String> test=new
	 * HashMap<String,String>(); test.put("1", "Test1"); test.put("1", "Test2");
	 * test.put("3", "Test3"); test.put("4", "Test4");
	 * System.setProperty("webdriver.chrome.driver",
	 * "C:\\SB3\\test\\chromedriver_win32\\chromedriver.exe"); RemoteWebDriver
	 * driver=new ChromeDriver(); ChromeOptions o=new ChromeOptions();
	 * System.out.println(o.getBrowserName()); driver.get("http://www.google.com");
	 * driver.manage().window().maximize(); return test; }
	 */
    
    
	
	  @GetMapping("/downloadManFile") public ResponseEntity<Resource>
	  downloadFile(@RequestParam String screenId) { // Load file from database
	  return ResponseEntity.ok()
	  .contentType(MediaType.parseMediaType("application/pdf"))
	  .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" +
	  "welcome.pdf" + "\"") .body(new
	  ByteArrayResource(sfhService.getFileData(screenId))); }
	 
}
