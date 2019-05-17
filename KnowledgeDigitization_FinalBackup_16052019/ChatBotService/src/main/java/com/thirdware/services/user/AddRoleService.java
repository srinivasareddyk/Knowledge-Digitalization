package com.thirdware.services.user;

import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.thirdware.entity.T_Role_001;
import com.thirdware.repositories.IRole002DAO;
import com.thirdware.repositories.RoleCustomQry;
import com.thirdware.util.ExcelUtils;
import com.thirdware.vo.SelectedItem;
import com.thirdware.vo.user.AddRoleDataTableVo;
import com.thirdware.vo.user.AddRoleVo;


@Service
public class AddRoleService {
	
	@Autowired
	IRole002DAO roleService;
	
	@Autowired
	RoleCustomQry roleCustomService;
	
	public List<AddRoleDataTableVo> getAddRoleList()
	{
		List<AddRoleDataTableVo> roleList=new ArrayList<AddRoleDataTableVo>();
		List<T_Role_001> daoRoleList=roleService.findByesip002LstUpdtS();
		//uVo.setWslIdSrch(usr.getWslIdSrch());
		int row=0;
		if(daoRoleList!=null && !daoRoleList.equals(""))
		{
			for(T_Role_001 roleDao:daoRoleList)
			{
				
				AddRoleDataTableVo rVo=new AddRoleDataTableVo();
				row=row+1;
				rVo.setId(row);	
				rVo.setRoleCode(roleDao.getKda001rolec());
				rVo.setRoleName(roleDao.getKda001rolen());
				SelectedItem status=new SelectedItem();
				status.setKey(roleDao.getKda001ActvF());
				status.setValue(roleDao.getKda001ActvF().equals("Y") ? "Active": "Inactive" );
				rVo.setStatus(status);
				rVo.setCreatedDate(roleDao.getKda001CrtS());
				rVo.setLastUpdatedDate(roleDao.getKda001LstUpdtS());
			    roleList.add(rVo);
		}
		}
		
		return roleList;
		
	}
public void saveRole(List<AddRoleDataTableVo> data){
		
		List<T_Role_001> saveList = new ArrayList<T_Role_001>();
		
		for(AddRoleDataTableVo dataObj : data){
			T_Role_001  addRoleBo = new T_Role_001();
			addRoleBo.setKda001rolec(dataObj.getRoleCode());
			addRoleBo.setKda001rolen(dataObj.getRoleName());
			addRoleBo.setKda001ActvF("Y");
			addRoleBo.setKda001CrtUsrC("esipUser");
			addRoleBo.setKda001CrtS(new Timestamp(System.currentTimeMillis()));
			addRoleBo.setKda001LstUpdtS(new Timestamp(System.currentTimeMillis()));
			addRoleBo.setKda001LstUpdtUsrC("esipUser");
			saveList.add(addRoleBo);
		}
		
		roleService.saveAll(saveList);
		
	}

public void updateRole(List<AddRoleDataTableVo> data){
	
	for(AddRoleDataTableVo dataObj : data  ){
		T_Role_001  addRoleBo = new T_Role_001();
		addRoleBo.setKda001rolec(dataObj.getRoleCode());
		addRoleBo.setKda001rolen(dataObj.getRoleName());
		addRoleBo.setKda001ActvF(dataObj.getStatus().getKey());
		if(dataObj.getStatus() != null &  dataObj.getStatus().getKey().equals("N") ){
			addRoleBo.setKda001Inactvy(new Date(System.currentTimeMillis()));
		}
		addRoleBo.setKda001LstUpdtS(new Timestamp(System.currentTimeMillis()));
		addRoleBo.setKda001LstUpdtUsrC("esipUser");
		roleService.updateAddRoleVo(addRoleBo);
	}
	
}

public boolean roleCodeDBCheck(String roleCode ){
	   
	   boolean flag = false;
	   
	   T_Role_001 roleCodeExists = roleService.findByesip002rolec(roleCode);
	   if(roleCodeExists != null){
		   flag = true;
	   }
	   
	   return flag;
}

public AddRoleVo dupValidate(List<AddRoleDataTableVo> dataList, boolean saveorUpdate){

	HashSet<String> entrydupSet = new HashSet<String>();	
	String errorMsg = null;
	List<String> errorList = new ArrayList<String>();
	AddRoleVo addVo = new AddRoleVo();
	for(AddRoleDataTableVo vo : dataList){
		if(entrydupSet.add(vo.getRoleCode())){
			if(saveorUpdate && roleCodeDBCheck(vo.getRoleCode())){

				errorMsg = "{{\"DSIDMain.lineNo.label\" | translate}}" + ".: ["+vo.getId()+"] "+"{{\"RoleMaintenance.roleCode.label\" | translate}}"+" "+vo.getRoleCode() + " {{\"DSIDMain.dsidExist.err\" | translate}}";
			}
			 
		}else{
			if(!errorMsg.equals("")){
				errorMsg  = ",";
			}
			errorMsg = "{{\"DSIDMain.lineNo.label\" | translate}}" + ".: ["+vo.getId()+"] "+"{{\"RoleMaintenance.roleCode.label\" | translate}}"+" "+vo.getRoleCode() + " {{\"DSIDMain.select.dup.err\" | translate}}";
		}
		
		if(!saveorUpdate && vo.getStatus().getKey().equalsIgnoreCase("N"))
		{
			boolean existFlag=roleCustomService.getUserCountByRole(vo.getRoleCode());
			if(existFlag)
			{
				errorMsg = "{{\"DSIDMain.lineNo.label\" | translate}}" + ".: ["+vo.getId()+"] "+"{{\"RoleMaintenance.roleCode.label\" | translate}}"+" "+vo.getRoleCode() + " {{\"Role.inactiveExist.err\" | translate}}";
			}
			else
			{
				boolean roleexistFlag=roleCustomService.getRoleMapCountByRole(vo.getRoleCode());
				if(roleexistFlag)
				{
					errorMsg = "{{\"DSIDMain.lineNo.label\" | translate}}" + ".: ["+vo.getId()+"] "+"{{\"RoleMaintenance.roleCode.label\" | translate}}"+" "+vo.getRoleCode() + " {{\"Role.inactiveroleMapExist.err\" | translate}}";
				}
			}
		}
        if(errorMsg!=null)
        {
		errorList.add(errorMsg);
        }
	}

	addVo.setErrors(errorList);

	return addVo ;
}

public File downloadExportExcelAddRole(List<AddRoleDataTableVo> adddataTableVo) {
    String [] headerNames = {"Role Code", "Role Name","Status","Created Date","Last Updated Date"};
    File downloadFile=ExcelUtils.downloadExportExcelAddRole("Role Maintenance",headerNames,5,adddataTableVo);         
    return downloadFile;
    
}

}
