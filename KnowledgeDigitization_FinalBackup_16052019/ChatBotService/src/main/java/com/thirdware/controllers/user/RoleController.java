package com.thirdware.controllers.user;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
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
import org.springframework.web.bind.annotation.RestController;
import com.thirdware.services.user.AddRoleService;
import com.thirdware.services.user.MapRoleService;
import com.thirdware.vo.SelectedItem;
import com.thirdware.vo.user.AddRoleDataTableVo;
import com.thirdware.vo.user.AddRoleVo;
import com.thirdware.vo.user.MapRoleDataTableVo;
import com.thirdware.vo.user.MapRoleVo;
import com.thirdware.vo.user.UserDataTableVo;

@RestController
@RequestMapping("/Role")
public class RoleController {
	
	@Autowired
	AddRoleService addRoleService;
	
	@Autowired
	MapRoleService mapRoleService;
	
	HashSet<String> entrydupUpldSet = new HashSet<String>();	
	//Add Role search
	
		@RequestMapping("/AddRoleSearch")
		public List<AddRoleDataTableVo> addRoleSearch()
		{
			/*System.out.println("*********** getUser **********"+user.getWslIdSrch());
			System.out.println("Status ******************"+user.getStatus().getKey());
			System.out.println("Lang ******************"+user.getLang());
			System.out.println("Country ******************"+user.getCountry());*/
					
			
			return addRoleService.getAddRoleList();
		}
		
		@RequestMapping("/AddRoleSave")
		public AddRoleVo save(@RequestBody List<AddRoleDataTableVo> dataList){
			AddRoleVo addRoleVo = new AddRoleVo();

			addRoleVo =addRoleService.dupValidate(dataList,true);
			if(addRoleVo.getErrors().isEmpty()){
				addRoleService.saveRole(dataList);

			}
			return addRoleVo;
		}
		
		@RequestMapping("/AddRoleUpdate")
		public AddRoleVo update(@RequestBody List<AddRoleDataTableVo> dataList){
			AddRoleVo addRoleVo = new AddRoleVo();

			addRoleVo = addRoleService.dupValidate(dataList,false);
			if(addRoleVo.getErrors().isEmpty()){
				addRoleService.updateRole(dataList);

			}
			return addRoleVo;
		}
		
		@RequestMapping("/MapRoleSearch")
		public List<MapRoleDataTableVo> getDsid( @RequestBody  MapRoleVo  mapRoleVo){
			return mapRoleService.getMapRoleList(mapRoleVo);

		}
		
		@RequestMapping("/MapRoleSave")
		public MapRoleVo saveMapRole(@RequestBody List<MapRoleDataTableVo> dataList){
			MapRoleVo mapRole = new MapRoleVo();

			mapRole = mapRoleService.dupValidateMapRole(dataList,true);
			if(mapRole.getErrors().isEmpty()){
				mapRoleService.saveRoleMap(dataList);

			}
			return mapRole;
		}
		@RequestMapping("/MapRoleUpdate")
		public MapRoleVo updateMapRole(@RequestBody List<MapRoleDataTableVo> dataList){
			MapRoleVo mapVo = new MapRoleVo();

			mapVo = mapRoleService.dupValidateMapRole(dataList,false);
			if(mapVo.getErrors().isEmpty()){
				mapRoleService.updateRoleMap(dataList);

			}
			return mapVo;
		}
		
		@RequestMapping("/MapRoleDelete")
		public MapRoleVo deleteRole(@RequestBody List<MapRoleDataTableVo> dataList){
			MapRoleVo mapVo = new MapRoleVo();

			
			
			mapRoleService.deleteRoleMap(dataList);

			
			return mapVo;
		}
		@RequestMapping("/getRoles")
		public List<String> getRole()
		{
			return mapRoleService.getAllRoles();
			
		}
		@RequestMapping("/getFunctionNames")
		public List<String> getFunctionNames()
		{
			return mapRoleService.getFunctionNames();
			
		}
		
		@RequestMapping("/getFunctionNamesBasedOnScreen")
		public List<String> getFunctionNamesOnScreen(@RequestBody String screen)
		{
			return mapRoleService.getFunctionNamesOnScreen(screen);
			
		}
		@RequestMapping("/getScreenNames")
		public List<SelectedItem> getScreenNames()
		{
			return mapRoleService.getScreenNames();
			
		}
		@RequestMapping("/getScreenNamesDT")
		public List<String> getScreenNamesDT()
		{
			return mapRoleService.getScreenNamesDT();
			
		}
		
		 @RequestMapping(value="/exportExcelAddRole", method = RequestMethod.POST,produces ="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	       public ResponseEntity<byte[]> downloadExportExcel(@RequestBody AddRoleVo addVo) throws Exception {
	                    
	              //File logFile=new File("C:\\PSAR\\Fault Maintenance.xlsx");
	              
	              File logFile=addRoleService.downloadExportExcelAddRole(addVo.getAddRoleList());
	              
	              if (!logFile.exists()) { // handle FNF
	               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	           }

	           try {
	               FileSystemResource fileResource = new FileSystemResource(logFile);

	               byte[] base64Bytes = Base64Utils.encode(IOUtils.toByteArray(fileResource.getInputStream()));

	               HttpHeaders headers = new HttpHeaders();
	               headers.add("filename", "Role Maintenance");

	               return ResponseEntity.ok().headers(headers).body(base64Bytes);
	           } catch (IOException e) 
	           {
	             
	               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	           }
	       

	       
	    }
	       
	       @RequestMapping(value="/exportExcelMapRole", method = RequestMethod.POST,produces ="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	       public ResponseEntity<byte[]> downloadExportExcelMap(@RequestBody MapRoleVo mapVo) throws Exception {
	                    
	              //File logFile=new File("C:\\PSAR\\Fault Maintenance.xlsx");
	              
	              File logFile=mapRoleService.downloadExportExcelMapRole(mapVo.getMaproleList());
	              
	              if (!logFile.exists()) { // handle FNF
	               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	           }

	           try {
	               FileSystemResource fileResource = new FileSystemResource(logFile);

	               byte[] base64Bytes = Base64Utils.encode(IOUtils.toByteArray(fileResource.getInputStream()));

	               HttpHeaders headers = new HttpHeaders();
	               headers.add("filename", "Role Maintenance");

	               return ResponseEntity.ok().headers(headers).body(base64Bytes);
	           } catch (IOException e) 
	           {
	             
	               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	           }
	       

	       
	    }

}
