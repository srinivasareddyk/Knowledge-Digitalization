package com.thirdware.services.user;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thirdware.entity.T_RoleMap_007;
import com.thirdware.entity.T_Role_001;
import com.thirdware.entity.T_Screen_002;
import com.thirdware.repositories.IRole002DAO;
import com.thirdware.repositories.IRoleMap004DAO;
import com.thirdware.repositories.IScreen027DAO;
import com.thirdware.repositories.IScreenCustomQry;
import com.thirdware.util.ExcelUtils;
import com.thirdware.vo.SelectedItem;
import com.thirdware.vo.user.AddRoleDataTableVo;
import com.thirdware.vo.user.MapRoleDataTableVo;
import com.thirdware.vo.user.MapRoleVo;

@Service
public class MapRoleService {
	
	@Autowired
	IRole002DAO roleDaoService;
	@Autowired
	IScreen027DAO screenService;
	@Autowired
	IScreenCustomQry screenCustomService;
	@Autowired
	IRoleMap004DAO mapRoleService;
	
	public List<MapRoleDataTableVo> getMapRoleList(MapRoleVo mapVo)
	{
		
		List<MapRoleDataTableVo> mapRoleDTList = new ArrayList<MapRoleDataTableVo>();
		if(mapRoleDTList!=null && !mapRoleDTList.isEmpty())
		{
			mapRoleDTList.clear();
		}
	     List<T_RoleMap_007> maproleDaoList=null;
	     List<MapRoleDataTableVo> mapdataTableList=new ArrayList<MapRoleDataTableVo>();
		if( (mapVo.getScreenSearch().getKey() == null || mapVo.getScreenSearch().getKey().isEmpty()) && 
				( mapVo.getFunctionNames() == null || mapVo.getFunctionNames().isEmpty() )
				&& (mapVo.getRoles() == null || mapVo.getRoles().isEmpty()) ){
			
			maproleDaoList= screenCustomService.getMapRoleList(mapVo);
			
		}else{
			//converting multi select list to string with comma appended
			mapVo.setFunctionString(convertListToString(mapVo.getFunctionNames()));
			mapVo.setRoleNameString(convertListToString(mapVo.getRoles()));
			maproleDaoList = screenCustomService.getMapRoleList(mapVo);
		}
		int row=0;
		Set<String> keySet = new HashSet<>();
		Map<String, MapRoleDataTableVo> mapVoList = new HashMap<String, MapRoleDataTableVo>();
		for(T_RoleMap_007  mapDao : maproleDaoList){
			
			MapRoleDataTableVo mapRoleVo = new MapRoleDataTableVo();
			
			//mapRoleVo.setId(row);
			SelectedItem screen=new SelectedItem();
			screen.setKey(mapDao.getScreenInRoleMap().getKda002ScreenN());
			screen.setValue(mapDao.getScreenInRoleMap().getKda002ScreenN());
			mapRoleVo.setScreen(screen);
			SelectedItem roleName=new SelectedItem();
			//roleName.setKey(mapDao.getRoleInRoleMap().getKda001rolen());
			roleName.setKey(mapDao.getRoleInRoleMap().getKda001rolen());
			roleName.setValue(mapDao.getRoleInRoleMap().getKda001rolen());
			mapRoleVo.setRole(roleName);
			mapRoleVo.getRoleVoList().addAll(getDTRoles());
			mapRoleVo.getScreenVoList().addAll(getScreenNames());
			mapRoleVo.getSelectedFunctionVoList().add(mapDao.getScreenInRoleMap().getKda002FunctionN());
			//mapRoleVo.getFunctionVoList().addAll(getFunctionNames());
			//mapRoleVo.getRoleMapSakey().add(mapDao.getKda007RoleMapSak());
			mapRoleVo.getRoleMapSakey().add(mapDao.getKda007RoleMapSak());
			mapRoleVo.setRoleSakey(mapDao.getRoleInRoleMap().getKda001rolesak());
			mapRoleVo.setScreenSakey(mapDao.getScreenInRoleMap().getKda002ScreenMstrSak());
			String key = mapDao.getScreenInRoleMap().getKda002ScreenN()+mapDao.getRoleInRoleMap().getKda001rolesak();
			if(keySet.contains(key))
			{	
				MapRoleDataTableVo mapRoleVoTemp = mapVoList.get(key);
				mapRoleVoTemp.getSelectedFunctionVoList().addAll(mapRoleVo.getSelectedFunctionVoList());
				mapRoleVoTemp.getRoleMapSakey().addAll(mapRoleVo.getRoleMapSakey());
				mapVoList.put(key, mapRoleVoTemp);
				
			}
			else
			{
				keySet.add(key);
				mapVoList.put(key, mapRoleVo);
			}
			
		    
			/* mapRoleDTList.add(mapRoleVo);*/

		}
		for(MapRoleDataTableVo mapFinalVo:mapVoList.values())
	    {
			row=row+1;
			mapFinalVo.setId(row);
	    	List<String> funcList=getFunctionNamesOnScreen(mapFinalVo.getScreen().getKey());
			//List<String> funcList=getFunctionNames();
	    	boolean flag=funcList.removeAll(mapFinalVo.getSelectedFunctionVoList());
	    	//System.out.println(funcList);
	    	mapFinalVo.getFunctionVoList().addAll(funcList);
	    	
	    	
	    	mapdataTableList.add(mapFinalVo);
	    }
		return mapdataTableList;
		
	}
public void saveRoleMap(List<MapRoleDataTableVo> data){
		
		List<T_RoleMap_007> saveList = new ArrayList<T_RoleMap_007>();
		
		for(MapRoleDataTableVo dataObj : data){
			for(String func:dataObj.getSelectedFunctionVoList())
			{
			T_RoleMap_007  mapRoleBo = new T_RoleMap_007();
			/*mapRoleBo.setEsip002rolec(dataObj.getRoleCode());
			mapRoleBo.setEsip002rolen(dataObj.getRoleName());*/
			T_Role_001 role=roleDaoService.findByesip002rolen(dataObj.getRole().getValue());
			mapRoleBo.setRoleInRoleMap(role);
			T_Screen_002 screen=screenService.findByesip027ScreenN(dataObj.getScreen().getKey(), func);
			mapRoleBo.setScreenInRoleMap(screen);
			//mapRoleBo.setKda007ActvF("Y");
			mapRoleBo.setKda007ActvF("Y");
			mapRoleBo.setKda007CrtUsrC("esipUser");
			mapRoleBo.setKda007CrtS(new Timestamp(System.currentTimeMillis()));
			mapRoleBo.setKda007LstUpdtS(new Timestamp(System.currentTimeMillis()));
			mapRoleBo.setKda007LstUpdtUsrC("esipUser");
			saveList.add(mapRoleBo);
			}
		}
		
		mapRoleService.saveAll(saveList);
		
	}

public void updateRoleMap(List<MapRoleDataTableVo> data){
	List<T_RoleMap_007> saveList = new ArrayList<T_RoleMap_007>();
	List<Long> newSakeyList=new ArrayList<Long>();
	Map<Long, String> mapVoList = new HashMap<Long, String>();
	for(MapRoleDataTableVo dataObj : data){
		String fun=convertListToString(dataObj.getSelectedFunctionVoList());
	List<T_RoleMap_007> sakeyDao=screenCustomService.findSakeyList(dataObj.getScreen().getKey(),fun,dataObj.getRole().getValue());
		if(sakeyDao!=null && !sakeyDao.isEmpty())
		{
			for(T_RoleMap_007 r:sakeyDao)
			{
				newSakeyList.add(r.getKda007RoleMapSak());
				mapVoList.put(r.getKda007RoleMapSak(),r.getScreenInRoleMap().getKda002FunctionN());
			}
		}
	if(newSakeyList!=null && !newSakeyList.isEmpty())
	{
		for(Long saKey:newSakeyList)
		{
			if(dataObj.getRoleMapSakey().contains(saKey))
			{
				//update
				dataObj.getSelectedFunctionVoList().remove(mapVoList.get(saKey));
				T_RoleMap_007  mapDAO=mapRoleService.findByesip004RoleMapSak(saKey);
				T_Screen_002 screen=screenService.findByesip027ScreenN(dataObj.getScreen().getKey(), mapVoList.get(saKey));
				mapDAO.setScreenInRoleMap(screen);
				mapDAO.setKda007LstUpdtS(new Timestamp(System.currentTimeMillis()));
				mapDAO.setKda007LstUpdtUsrC("esipUser");
				mapRoleService.updateUsingVo(mapDAO);
			}
		}
			
				//insert
				for(String func:dataObj.getSelectedFunctionVoList())
				{
				T_RoleMap_007  mapRoleBo = new T_RoleMap_007();
				/*mapRoleBo.setEsip002rolec(dataObj.getRoleCode());
				mapRoleBo.setEsip002rolen(dataObj.getRoleName());*/
				T_Role_001 role=roleDaoService.findByesip002rolen(dataObj.getRole().getValue());
				mapRoleBo.setRoleInRoleMap(role);
				T_Screen_002 screen=screenService.findByesip027ScreenN(dataObj.getScreen().getKey(), func);
				mapRoleBo.setScreenInRoleMap(screen);
				mapRoleBo.setKda007ActvF("Y");
				mapRoleBo.setKda007CrtUsrC("esipUser");
				mapRoleBo.setKda007CrtS(new Timestamp(System.currentTimeMillis()));
				mapRoleBo.setKda007LstUpdtS(new Timestamp(System.currentTimeMillis()));
				mapRoleBo.setKda007LstUpdtUsrC("esipUser");
				saveList.add(mapRoleBo);
				mapRoleService.save(mapRoleBo);
				
			
			}
		}
		
		//Deleting existing sakey
		for(Long sKey:dataObj.getRoleMapSakey())
		{
			int deleteCount=0;
			if(!newSakeyList.contains(sKey))
			{
				//delete
				deleteCount=mapRoleService.deleteUserAreaUsingSakey(sKey);
			}
		}
	}
			
			
		
	}
	


public void deleteRoleMap(List<MapRoleDataTableVo> data){
	
	for(MapRoleDataTableVo dataObj : data){
		int deleteCount=0;
		for(Long saKey:dataObj.getRoleMapSakey())
		{
		deleteCount=mapRoleService.deleteUserAreaUsingSakey(saKey);
		}
	}
	
}


/*public void updateRoleMap(List<MapRoleDataTableVo> data){
	List<T_RoleMap_007> saveList = new ArrayList<T_RoleMap_007>();
	for(MapRoleDataTableVo dataObj : data){
		T_Role_001 rol=roleDaoService.findByesip002rolesak(dataObj.getRoleSakey());
		if(rol!=null && !rol.equals("") && rol.getKda001rolen().equalsIgnoreCase(dataObj.getRole().getValue()))
		{
			int deleteCount=0;
			for(Long saKey:dataObj.getRoleMapSakey())
			{
			deleteCount=mapRoleService.deleteUserAreaUsingSakey(saKey);
			}
		
			if(deleteCount>0)
			{
				for(String func:dataObj.getSelectedFunctionVoList())
				{
				T_RoleMap_007  mapRoleBo = new T_RoleMap_007();
				mapRoleBo.setEsip002rolec(dataObj.getRoleCode());
				mapRoleBo.setEsip002rolen(dataObj.getRoleName());
				T_Role_001 role=roleDaoService.findByesip002rolen(dataObj.getRole().getValue());
				mapRoleBo.setRoleInRoleMap(role);
				T_Screen_002 screen=screenService.findByesip027ScreenN(dataObj.getScreen().getValue(), func);
				mapRoleBo.setScreenInRoleMap(screen);
				mapRoleBo.setKda007ActvF("Y");
				mapRoleBo.setKda007CrtUsrC("esipUser");
				mapRoleBo.setKda007CrtS(new Timestamp(System.currentTimeMillis()));
				mapRoleBo.setKda007LstUpdtS(new Timestamp(System.currentTimeMillis()));
				mapRoleBo.setKda007LstUpdtUsrC("esipUser");
				saveList.add(mapRoleBo);
				}
			}
		
		}
		
	}
	
	if(saveList!=null && !saveList.isEmpty())
	{
	mapRoleService.saveAll(saveList);
	}
}*/
public MapRoleVo dupValidateMapRole(){
	
	HashSet<String> entrydupSet = new HashSet<String>();	
	String errorMsg = null;
	List<String> errorList = new ArrayList<String>();
	MapRoleVo mapVo = new MapRoleVo();
	
	return mapVo;
	
}

public MapRoleVo dupValidateMapRole(List<MapRoleDataTableVo> dataList, boolean saveorUpdate){

	HashSet<String> entrydupSet = new HashSet<String>();	
	String errorMsg = null;
	List<String> errorList = new ArrayList<String>();
	MapRoleVo mapVo = new MapRoleVo();
	for(MapRoleDataTableVo vo : dataList){
		String dupValue=vo.getScreen().getKey()+vo.getRole().getValue();
		if(entrydupSet.add(dupValue)){
			if(saveorUpdate && roleScreenDBCheck(vo.getScreen().getKey(),vo.getRole().getValue())){

				errorMsg = "{{\"DSIDMain.lineNo.label\" | translate}}" + ".: ["+vo.getId()+"] "+"{{\"RoleMaintenance.screenName.label\" | translate}}"+"-"+vo.getScreen().getKey() +" , "+"{{\"RoleMaintenance.role.label\" | translate}}"+"-"+vo.getRole().getValue()+ " {{\"DSIDMain.dsidExist.err\" | translate}}";
			}
			 
		}else{
			if(!errorMsg.equals("")){
				errorMsg  = ",";
			}
			errorMsg = "{{\"DSIDMain.lineNo.label\" | translate}}" + ".: ["+vo.getId()+"] "+"{{\"RoleMaintenance.screenName.label\" | translate}}"+"-"+vo.getScreen().getKey() +" , "+"{{\"RoleMaintenance.role.label\" | translate}}"+"-"+vo.getRole().getValue()+ " {{\"DSIDMain.select.dup.err\" | translate}}";
		}
		T_Role_001 rol=roleDaoService.findByesip002rolesak(vo.getRoleSakey());
		if(!saveorUpdate && !rol.getKda001rolen().equalsIgnoreCase(vo.getRole().getValue()) && roleScreenDBCheck(vo.getScreen().getKey(),vo.getRole().getValue()))
		{
			errorMsg = "{{\"DSIDMain.lineNo.label\" | translate}}" + ".: ["+vo.getId()+"] "+"{{\"RoleMaintenance.screenName.label\" | translate}}"+"-"+vo.getScreen().getKey() +" , "+"{{\"RoleMaintenance.role.label\" | translate}}"+"-"+vo.getRole().getValue()+ " {{\"Role.roleScreenExist.err\" | translate}}";
		}
			
        if(errorMsg!=null)
        {
		errorList.add(errorMsg);
        }
	}

	mapVo.setErrors(errorList);

	return mapVo ;
}

public boolean roleScreenDBCheck(String screen,String  role){
	   
	   boolean flag = false;
	   
	   List<T_RoleMap_007> roleScreenList = screenCustomService.findScreenRoleDB(screen,role);
	   if(roleScreenList.size()>0 && !roleScreenList.isEmpty()){
		   flag = true;
	   }
	   
	   return flag;
}



public List<String> getAllRoles()
{
	List<String> roleList=new ArrayList<String>();
	String activeFlag="Y";
	Iterable<T_Role_001> resultList=roleDaoService.findByesip002ActvF(activeFlag);
	if(resultList!=null && !resultList.equals(""))
	{
		for(T_Role_001 role:resultList)
		{
			roleList.add(role.getKda001rolen());
		}
		
	}
	return roleList;
	
}

public List<String> getFunctionNames()
{
	List<String> functionNameList=null;
	Set<String> functionSet = new TreeSet<String>();
	Iterable<T_Screen_002> resultList=screenService.findAll();
	if(resultList!=null && !resultList.equals(""))
	{
		for(T_Screen_002 screen:resultList)
		{
			functionSet.add(screen.getKda002FunctionN());
		}
		
	}
	
	if(functionSet!=null && !functionSet.isEmpty())
	{
		functionNameList=new ArrayList<String>(functionSet);
	}
	return functionNameList;
	
}

public List<String> getFunctionNamesOnScreen(String screen)
{
	System.out.println(screen);
	
	List<String> functionNameList=new ArrayList<String>();
	//Set<String> functionSet = new TreeSet<String>();
List<T_Screen_002> resultList=screenService.findByesip027ScreenName(screen);
	if(resultList!=null && !resultList.equals(""))
	{
		for(T_Screen_002 scr:resultList)
		{
			functionNameList.add(scr.getKda002FunctionN());
		}
		
	}
	return functionNameList;
	
}
public List<SelectedItem> getScreenNames()
{
	List<SelectedItem> screenList=new ArrayList<SelectedItem>();
	//HashSet<SelectedItem> screenSet = new HashSet<SelectedItem>();
	Iterable<String> resultList=screenCustomService.getScreenNames();
	if(resultList!=null && !resultList.equals(""))
	{
		SelectedItem svo1=new SelectedItem();
		svo1.setKey(null);
		svo1.setValue("Please Select");
		screenList.add(svo1);
		for(String screen:resultList)
		{
			SelectedItem svo=new SelectedItem();
			svo.setKey(screen);
			svo.setValue(trimto(screen,17));
			screenList.add(svo);
		}
		
	}
	
	
	return screenList;
}
public List<String> getScreenNamesDT()
{
	List<String> screenList=new ArrayList<String>();
	//HashSet<SelectedItem> screenSet = new HashSet<SelectedItem>();
	Iterable<String> resultList=screenCustomService.getScreenNames();
	if(resultList!=null && !resultList.equals(""))
	{
		
		for(String screen:resultList)
		{
			screenList.add(trimto(screen,17));
		}
		
	}
	
	
	return screenList;
}
public List<SelectedItem> getDTRoles()
{
	List<SelectedItem> roleList=new ArrayList<SelectedItem>();
	String activeFlag="Y";
	Iterable<T_Role_001> resultList=roleDaoService.findByesip002ActvF(activeFlag);
	if(resultList!=null && !resultList.equals(""))
	{
		
		SelectedItem svo1=new SelectedItem();
		svo1.setKey(null);
		svo1.setValue("Please Select");
		roleList.add(svo1);
		for(T_Role_001 screen:resultList)
		{
			SelectedItem svo=new SelectedItem();
			svo.setKey(screen.getKda001rolec());
			svo.setValue(screen.getKda001rolen());
			roleList.add(svo);
		}
		
		
	}
	return roleList;
	
}
public String convertListToString(List<String> valueList)
{
	StringBuilder query = new StringBuilder();
	System.out.println(valueList);
if (valueList.size() > 1) {
	query.append(" (");
	StringBuilder strB = new StringBuilder();
	for (String val : valueList) {
		strB.append("'" + val + "',");
	}
	strB.deleteCharAt(strB.lastIndexOf(","));
	query.append(strB.toString() + ")");
} else {
	if (valueList.size() == 0)
		query.append("");
	else{
		for (String value : valueList) {
			query.append(" ('" + value + "')");
		}
		
	}
}

System.out.println(query.toString());

	return query.toString();
} 
public File downloadExportExcelMapRole(List<MapRoleDataTableVo> mapRoleDataTableVo) {
    String [] headerNames = {"Screen Name", "Function","Role"};
    File downloadFile=ExcelUtils.downloadFaultExportExcelMapRole("Role Maintenance",headerNames,3,mapRoleDataTableVo);         
    return downloadFile;
    
}


public String trimto(String val, int digit)
	{
		String modified="";
		if(val.length()>=digit)
		{
			modified = val.substring(0, digit);
			return modified+"...";
		}
		else
			return val;
	} 

}
