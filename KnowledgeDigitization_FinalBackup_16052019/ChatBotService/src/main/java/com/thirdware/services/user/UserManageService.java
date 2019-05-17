package com.thirdware.services.user;

import java.sql.Date;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thirdware.entity.E_MDAT006_PARM;
import com.thirdware.entity.T_PARM_006;
import com.thirdware.entity.T_Role_001;
import com.thirdware.entity.T_User_003;
import com.thirdware.repositories.IParameter003DAO;
import com.thirdware.repositories.IRole002DAO;
import com.thirdware.repositories.IUser001DAO;
import com.thirdware.repositories.IUserCustomQry;
import com.thirdware.vo.SelectedItem;
import com.thirdware.vo.user.UserAreaVo;
import com.thirdware.vo.user.UserDataTableVo;
import com.thirdware.vo.user.UserManageVo;




@Service
public class UserManageService {
	
	@Autowired
	IUser001DAO userDaoService;
	
	@Autowired
	IRole002DAO roleDaoService;
	
	@Autowired
	IUserCustomQry userCustomService;
	
	/*@Autowired
	IArea009DAO areaService;
	
	@Autowired
	IUserArea022DAO userAreaService;
	*/
	@Autowired
	IParameter003DAO paramService;
	
	
	
	public List<UserDataTableVo> getManageUserList(UserManageVo usr)
	{
		
		List<UserDataTableVo> userList=new ArrayList<UserDataTableVo>();
		List<T_User_003> usrDaoList=null;
		if(usr.getCdsidSearch()==null && usr.getfNameSearch()==null && usr.getlNameSearch()==null && usr.getRoleSearch().getKey()==null && usr.getStatusSearch().getKey()==null)
		{
			usrDaoList=(List<T_User_003>) userDaoService.findByesip001StatC("A");
		}
		else
		{
		//List<T_User_003> usrDaoList=userDaoService.findByesip001Search(usr.getCdsidSearch(),usr.getfNameSearch(),usr.getlNameSearch(),usr.getRoleSearch().getValue(),usr.getStatusSearch().getValue());
		 usrDaoList=userCustomService.getUsersEntity(usr);
		 System.out.println("Inside custom query");
		}
		//uVo.setWslIdSrch(usr.getWslIdSrch());
		
		int row=0;
		if(usrDaoList!=null && !usrDaoList.isEmpty())
		{
			for(T_User_003 userDao:usrDaoList)
			{
				
				UserDataTableVo uVo=new UserDataTableVo();
				row=row+1;
				uVo.setId(row);	
				//uVo.setCdsid(userDao.getKda003WslD());
			uVo.setCdsid(userDao.getKda003WslD());
			uVo.setfName(userDao.getKda003FnN());
			uVo.setlName(userDao.getKda003LnN());
			uVo.setEmail(userDao.getKda003EmailidD());
			SelectedItem role=new SelectedItem();
			//role.setKey(userDao.getRole().getKda001rolec());
			role.setKey(userDao.getRole().getKda001rolec());
			role.setValue(userDao.getRole().getKda001rolen());
			uVo.setRole(role);	
			SelectedItem status=new SelectedItem();
			if(userDao.getKda003ActvF().equalsIgnoreCase("Y"))
			{
				status.setKey("Y");
				status.setValue("Active");
				
			}
			else
			{
				status.setKey("N");
				status.setValue("Inactive");
			}
			uVo.setStatus(status);
			
			userList.add(uVo);
		}
		}
		
		return userList;
	}
	
	
	public List<UserDataTableVo> getRequestUserList()
	{
		
		List<UserDataTableVo> userList=new ArrayList<UserDataTableVo>();		
		//List<T_User_003> usrDaoList=userDaoService.findByesip001Search(usr.getCdsidSearch(),usr.getfNameSearch(),usr.getlNameSearch(),usr.getRoleSearch().getValue(),usr.getStatusSearch().getValue());
		Iterable<T_User_003> usrDaoList=userDaoService.findByesip001StatC("P");
		//uVo.setWslIdSrch(usr.getWslIdSrch());
		int row=0;
		if(usrDaoList!=null && !usrDaoList.equals(""))
		{
			for(T_User_003 userDao:usrDaoList)
			{
				
				UserDataTableVo uVo=new UserDataTableVo();
				row=row+1;
				uVo.setId(row);	
	        uVo.setCdsid(userDao.getKda003WslD());
			uVo.setfName(userDao.getKda003FnN());
			uVo.setlName(userDao.getKda003LnN());
			uVo.setEmail(userDao.getKda003EmailidD());
			uVo.setSubmitDate(userDao.getKda003CrtS());	
			
			/*if(userDao.getKda003ActvF().equalsIgnoreCase("Y"))
			{
				uVo.setStatus("Active");
			}
			else
			{
				uVo.setStatus("Inactive");
			}*/
			
			userList.add(uVo);
		}
		}
		
		return userList;
	}
	public UserDataTableVo getManageUserInfo(UserDataTableVo usr)
	{
		UserDataTableVo uVo=new UserDataTableVo();
		T_User_003 usrDao=userDaoService.findByesip001WslD(usr.getCdsid());
		uVo.setCdsid(usr.getCdsid());
		String langCode="L";
		String countryCode="C";
		if(usrDao!=null)
		{
			uVo.setCdsid(usrDao.getKda003WslD());
			uVo.setfName(usrDao.getKda003FnN());
			uVo.setlName(usrDao.getKda003LnN());
			uVo.setEmail(usrDao.getKda003EmailidD());
			uVo.setwPhone(usrDao.getKda003WrkPhR());
			uVo.setAddr1(usrDao.getKda003AddrLn1X());
			uVo.setAdrr2(usrDao.getKda003AddrLn2X());
			uVo.setCity(usrDao.getKda003CtyN());
			uVo.setState(usrDao.getKda003StN());
			uVo.setpCode(usrDao.getKda003PstlC());
			T_PARM_006 langDetail=paramService.findByTParm006Remarks(langCode, usrDao.getKda003PreflangC());
			T_PARM_006 countryDetail=paramService.findByTParm006Remarks(countryCode, usrDao.getKda003CntryN());
			SelectedItem country=new SelectedItem();
			if(countryDetail!=null)
			{
			    country.setKey(countryDetail.getKda011ParmC());
				country.setValue(countryDetail.getKda011ParmX());
			}
			uVo.setCountry(country);
			SelectedItem lang=new SelectedItem();
			if(langDetail!=null)
			{
				lang.setKey(langDetail.getKda011ParmC());
				lang.setValue(langDetail.getKda011ParmX());
			}
			uVo.setLang(lang);
			SelectedItem role=new SelectedItem();
			role.setKey(usrDao.getRole().getKda001rolec());
			role.setValue(usrDao.getRole().getKda001rolen());
			uVo.setRole(role);
			
			/*List<T_UserAreaInfo_022> areaDBList=userAreaService.findByuser(uVo.getCdsid());
			if(areaDBList!=null && !areaDBList.isEmpty())
			{
			for(T_UserAreaInfo_022 userArea:areaDBList)
			{
				//areaList.add(userArea.getArea().getEsip009areaC()+"-"+userArea.getArea().getEsip009areaX());
				uVo.getAreaSelectedList().add(userArea.getArea().getEsip009areaC()+"-"+userArea.getArea().getEsip009areaX());
				System.out.println(userArea.getArea().getEsip009areaC()+"-"+userArea.getArea().getEsip009areaX());
			}
			}*/
		
			
			
			SelectedItem status=new SelectedItem();
			if(usrDao.getKda003ActvF().equalsIgnoreCase("Y"))
			{
				status.setKey("Y");
				status.setValue("Active");
				uVo.setDisableFlag(false);
			}
			else
			{
				status.setKey("N");
				status.setValue("Inactive");
				uVo.setDisableFlag(true);
			}
			uVo.setStatus(status);
			
		}
			
		return uVo;
	}
	
	public UserDataTableVo getRequestUserInfo(UserDataTableVo usr)
	{
		UserDataTableVo uVo=new UserDataTableVo();
		T_User_003 usrDao=userDaoService.findByesip001WslD(usr.getCdsid());
		uVo.setCdsid(usr.getCdsid());
		String langCode="L";
		String countryCode="C";
		if(usrDao!=null)
		{
			uVo.setCdsid(usrDao.getKda003WslD());
			uVo.setfName(usrDao.getKda003FnN());
			uVo.setlName(usrDao.getKda003LnN());
			uVo.setEmail(usrDao.getKda003EmailidD());
			uVo.setwPhone(usrDao.getKda003WrkPhR());
			uVo.setAddr1(usrDao.getKda003AddrLn1X());
			uVo.setAdrr2(usrDao.getKda003AddrLn2X());
			uVo.setCity(usrDao.getKda003CtyN());
			uVo.setState(usrDao.getKda003StN());
			uVo.setpCode(usrDao.getKda003PstlC());
			T_PARM_006 langDetail=paramService.findByTParm006Remarks(langCode, usrDao.getKda003PreflangC());
			T_PARM_006 countryDetail=paramService.findByTParm006Remarks(countryCode, usrDao.getKda003CntryN());
			SelectedItem country=new SelectedItem();
			if(countryDetail!=null)
			{
			    country.setKey(countryDetail.getKda011ParmC());
				country.setValue(countryDetail.getKda011ParmX());
			}
			uVo.setCountry(country);
			SelectedItem lang=new SelectedItem();
			if(langDetail!=null)
			{
				lang.setKey(langDetail.getKda011ParmC());
				lang.setValue(langDetail.getKda011ParmX());
			}
			uVo.setLang(lang);
			/*SelectedItem role=new SelectedItem();
			role.setKey(usrDao.getRole().getKda001rolec());
			role.setValue(usrDao.getRole().getKda001rolen());
			uVo.setRole(role);*/
			
			//Area Search
			
			/*List<T_UserAreaInfo_022> areaDBList=userAreaService.findByuser(uVo.getCdsid());
			if(areaDBList!=null && !areaDBList.isEmpty())
			{
			for(T_UserAreaInfo_022 userArea:areaDBList)
			{
				//areaList.add(userArea.getArea().getEsip009areaC()+"-"+userArea.getArea().getEsip009areaX());
				uVo.getAreaSelectedList().add(userArea.getArea().getEsip009areaC()+"-"+userArea.getArea().getEsip009areaX());
				System.out.println(userArea.getArea().getEsip009areaC()+"-"+userArea.getArea().getEsip009areaX());
			}
			}*/
		
			
			
			SelectedItem status=new SelectedItem();
			if(usrDao.getKda003ActvF().equalsIgnoreCase("Y"))
			{
				status.setKey("Y");
				status.setValue("Active");
				uVo.setDisableFlag(false);
			}
			else
			{
				status.setKey("N");
				status.setValue("Inactive");
				uVo.setDisableFlag(true);
			}
			uVo.setStatus(status);
			
		}
			
		return uVo;
	}
	
	public UserDataTableVo getUpdateUserInfo(String usr)
	{
		UserDataTableVo uVo=new UserDataTableVo();
		T_User_003 usrDao=userDaoService.findByesip001WslD(usr);
		String langCode="L";
		String countryCode="C";
		if(usrDao!=null)
		{
			uVo.setCdsid(usrDao.getKda003WslD());
			uVo.setfName(usrDao.getKda003FnN());
			uVo.setlName(usrDao.getKda003LnN());
			uVo.setEmail(usrDao.getKda003EmailidD());
			uVo.setwPhone(usrDao.getKda003WrkPhR());
			uVo.setAddr1(usrDao.getKda003AddrLn1X());
			uVo.setAdrr2(usrDao.getKda003AddrLn2X());
			uVo.setCity(usrDao.getKda003CtyN());
			uVo.setState(usrDao.getKda003StN());
			uVo.setpCode(usrDao.getKda003PstlC());			
			T_PARM_006 langDetail=paramService.findByTParm006Remarks(langCode, usrDao.getKda003PreflangC());
			T_PARM_006 countryDetail=paramService.findByTParm006Remarks(countryCode, usrDao.getKda003CntryN());
			SelectedItem country=new SelectedItem();
			if(countryDetail!=null)
			{
			    country.setKey(countryDetail.getKda011ParmC());
				country.setValue(countryDetail.getKda011ParmX());
			}
			uVo.setCountry(country);
			SelectedItem lang=new SelectedItem();
			if(langDetail!=null)
			{
				lang.setKey(langDetail.getKda011ParmC());
				lang.setValue(langDetail.getKda011ParmX());
			}
			uVo.setLang(lang);
			SelectedItem role=new SelectedItem();
			role.setKey(usrDao.getRole().getKda001rolec());
			role.setValue(usrDao.getRole().getKda001rolen());
			uVo.setRole(role);
			
			//Area Search
			/*List<T_UserAreaInfo_022> areaDBList=userAreaService.findByuser(uVo.getCdsid());
			if(areaDBList!=null && !areaDBList.isEmpty())
			{
			for(T_UserAreaInfo_022 userArea:areaDBList)
			{
				//areaList.add(userArea.getArea().getEsip009areaC()+"-"+userArea.getArea().getEsip009areaX());
				uVo.getAreaSelectedList().add(userArea.getArea().getEsip009areaC()+"-"+userArea.getArea().getEsip009areaX());
				System.out.println(userArea.getArea().getEsip009areaC()+"-"+userArea.getArea().getEsip009areaX());
			}
			}*/
			/*
			SelectedItem status=new SelectedItem();
			if(usrDao.getKda003ActvF().equalsIgnoreCase("Y"))
			{
				status.setKey("Y");
				status.setValue("Active");
				uVo.setDisableFlag(false);
			}
			else
			{
				status.setKey("N");
				status.setValue("Inactive");
				uVo.setDisableFlag(true);
			}
			uVo.setStatus(status);*/
			
		}
			
		return uVo;
	}
	/*public UserDataTableVo getRequestUserInfo(UserDataTableVo usr)
	{
		UserDataTableVo uVo=new UserDataTableVo();
		T_User_003 usrDao=userDaoService.findByesip001WslD(usr.getCdsid());
		uVo.setCdsid(usr.getCdsid());
		if(usrDao!=null)
		{
			uVo.setCdsid(usrDao.getKda003WslD());
			uVo.setfName(usrDao.getKda003FnN());
			uVo.setlName(usrDao.getKda003LnN());
			uVo.setEmail(usrDao.getKda003EmailidD());
			uVo.setwPhone(usrDao.getKda003WrkPhR());
			uVo.setAddr1(usrDao.getKda003AddrLn1X());
			uVo.setAdrr2(usrDao.getKda003AddrLn2X());
			uVo.setCity(usrDao.getKda003CtyN());
			uVo.setState(usrDao.getKda003StN());
			uVo.setpCode(usrDao.getKda003PstlC());
			uVo.setpLang(usrDao.getKda003PreflangC());
			SelectedItem country=new SelectedItem();
			//country.setKey("Y");
			country.setValue(usrDao.getKda003CntryN());
			uVo.setCountry(country);
			SelectedItem role=new SelectedItem();
			//country.setKey("Y");
			//role.setValue(usrDao);
			//uVo.setRole(country);
			SelectedItem lang=new SelectedItem();
			lang.setKey("Y");
			lang.setValue(usrDao.);
			uVo.setRole(lang);
			
			if(usrDao.getKda003ActvF().equalsIgnoreCase("Y"))
			{
				uVo.setStatus("Active");
			}
			else
			{
				uVo.setStatus("Inactive");
			}
			
		}
			
		return uVo;
	}*/
	public void saveUser(UserAreaVo usrVo)
	{
		
		T_User_003 usr=new T_User_003();
		//usr.setKda003WslD(usrVo.getUserObj().getCdsid());
		usr.setKda003WslD(usrVo.getUserObj().getCdsid());
		usr.setKda003FnN(usrVo.getUserObj().getfName());
		usr.setKda003LnN(usrVo.getUserObj().getlName());
		usr.setKda003EmailidD(usrVo.getUserObj().getEmail());
		usr.setKda003WrkPhR(usrVo.getUserObj().getwPhone());
		usr.setKda003AddrLn1X(usrVo.getUserObj().getAddr1());
		usr.setKda003AddrLn2X(usrVo.getUserObj().getAdrr2());
		usr.setKda003CtyN(usrVo.getUserObj().getCity());
		usr.setKda003StN(usrVo.getUserObj().getState());
		usr.setKda003PstlC(usrVo.getUserObj().getpCode());
		usr.setKda003CntryN(usrVo.getUserObj().getCountry().getValue());
		usr.setKda003PreflangC(usrVo.getUserObj().getLang().getValue());
		if(usrVo.getUserObj().isDisableFlag())
		{
			usr.setKda003ActvF("N");	
			usr.setKda003InactvY(new Date(System.currentTimeMillis()));
		}
		else
			usr.setKda003ActvF("Y");	
		T_Role_001 role=roleDaoService.findByesip002rolen(usrVo.getUserObj().getRole().getValue());
		usr.setRole(role);
		usr.setKda003LstUpdtUsrC("esipUser");
		usr.setKda003LstUpdtS(new Timestamp(System.currentTimeMillis()));
		userDaoService.updateUserUsingVo(usr);
		
		
		/*System.out.println("Selected List"+usrVo.getSelectedList().size());
        List<T_UserAreaInfo_022> userArea=userAreaService.findByuser(usrVo.getUserObj().getCdsid());
        int deleteCount=0;
        if(userArea!=null && !userArea.isEmpty())
        {
        
        	for(T_UserAreaInfo_022 usrAreaObj:userArea)
        	{
        	int count=userAreaService.deleteUserAreaUsingSakey(usrAreaObj.getEsip022UsrAreaInfoSak());
        	deleteCount=deleteCount+count;
        	}
        }
        
        System.out.println("Delete Count-------------------------"+deleteCount);
		System.out.println("Selected List"+usrVo.getSelectedList().size());
		if(usrVo.getSelectedList()!=null && !usrVo.getSelectedList().isEmpty())
		{
		for(String areaVar:usrVo.getSelectedList())
		{
			T_UserAreaInfo_022 userAreaDetail=new  T_UserAreaInfo_022();
			usr=userDaoService.findByesip001WslD(usrVo.getUserObj().getCdsid());
			userAreaDetail.setUser(usr);
			String[] areaCode=areaVar.split("-");
			T_Area_009 finalArea=areaService.findByesip009areaC(areaCode[0]);
			userAreaDetail.setArea(finalArea);
			userAreaDetail.setEsip022CrtprocC("save");
			userAreaDetail.setEsip022CrtUsrC("esipUser");
			userAreaDetail.setEsip022CrtS(new Timestamp(System.currentTimeMillis()));
			userAreaDetail.setEsip022LstprocC("save");
			userAreaDetail.setEsip022LstUpdtUsrC("esipUser");
			userAreaDetail.setEsip022LstUpdtS(new Timestamp(System.currentTimeMillis()));
			userAreaService.save(userAreaDetail);
		}
		}*/
	}
	
	public void approveUser(UserAreaVo usrVo)
	{
		
		T_User_003 usr=new T_User_003();
		usr.setKda003WslD(usrVo.getUserObj().getCdsid());
		/*usr.setKda003FnN(usrVo.getfName());
		usr.setKda003LnN(usrVo.getlName());
		usr.setKda003EmailidD(usrVo.getEmail());
		usr.setKda003WrkPhR(usrVo.getwPhone());
		usr.setKda003AddrLn1X(usrVo.getAddr1());*/
		usr.setKda003AddrLn2X(usrVo.getUserObj().getAdrr2());
		usr.setKda003CtyN(usrVo.getUserObj().getCity());
		usr.setKda003StN(usrVo.getUserObj().getState());
		usr.setKda003PstlC(usrVo.getUserObj().getpCode());
		usr.setKda003CntryN(usrVo.getUserObj().getCountry().getValue());
		usr.setKda003PreflangC(usrVo.getUserObj().getLang().getValue());
		usr.setKda003StatC("A");		
		T_Role_001 role=roleDaoService.findByesip002rolen(usrVo.getUserObj().getRole().getValue());
		usr.setRole(role);
		usr.setKda003LstUpdtUsrC("esipUser");
		usr.setKda003LstUpdtS(new Timestamp(System.currentTimeMillis()));
		int noOfRowsUpdated=userDaoService.approveUserUsingVo(usr);
		System.out.println(noOfRowsUpdated);
		
		
		
		/*System.out.println("Selected List"+usrVo.getSelectedList().size());
        List<T_UserAreaInfo_022> userArea=userAreaService.findByuser(usrVo.getUserObj().getCdsid());
        int deleteCount=0;
        if(userArea!=null && !userArea.isEmpty())
        {
        
        	for(T_UserAreaInfo_022 usrAreaObj:userArea)
        	{
        	int count=userAreaService.deleteUserAreaUsingSakey(usrAreaObj.getEsip022UsrAreaInfoSak());
        	deleteCount=deleteCount+count;
        	}
        }
        
        System.out.println("Delete Count-------------------------"+deleteCount);
		System.out.println("Selected List"+usrVo.getSelectedList().size());
		if(usrVo.getSelectedList()!=null && !usrVo.getSelectedList().isEmpty())
		{
		for(String areaVar:usrVo.getSelectedList())
		{
			T_UserAreaInfo_022 userAreaDetail=new  T_UserAreaInfo_022();
			usr=userDaoService.findByesip001WslD(usrVo.getUserObj().getCdsid());
			userAreaDetail.setUser(usr);
			String[] areaCode=areaVar.split("-");
			T_Area_009 finalArea=areaService.findByesip009areaC(areaCode[0]);
			userAreaDetail.setArea(finalArea);
			userAreaDetail.setEsip022CrtprocC("save");
			userAreaDetail.setEsip022CrtUsrC("esipUser");
			userAreaDetail.setEsip022CrtS(new Timestamp(System.currentTimeMillis()));
			userAreaDetail.setEsip022LstprocC("save");
			userAreaDetail.setEsip022LstUpdtUsrC("esipUser");
			userAreaDetail.setEsip022LstUpdtS(new Timestamp(System.currentTimeMillis()));
			userAreaService.save(userAreaDetail);
		}
		}*/
	}
	
	public void denyUser(UserAreaVo usrVo)
	{
		      ///int deleteUser=0;
			// int deleteCount=0;
			/* List<T_UserAreaInfo_022> userArea=userAreaService.findByuser(usrVo.getUserObj().getCdsid());
		        if(userArea!=null && !userArea.isEmpty())
		        {
		        
		        	for(T_UserAreaInfo_022 usrAreaObj:userArea)
		        	{
		        	int count=userAreaService.deleteUserAreaUsingSakey(usrAreaObj.getEsip022UsrAreaInfoSak());
		        	deleteCount=deleteCount+count;
		        	}
		        }*/
		        /*if(deleteCount>0)
		        {*/
			 int deleteUser=userDaoService.deleteUserUsingCdsId(usrVo.getUserObj().getCdsid());	
		        //}
		        System.out.println("Delete Count-------------------------"+deleteUser);
		
	}
	/*public void denyUser(UserAreaVo usrVo)
	{
		
		T_User_003 usr=new T_User_003();
		usr.setKda003WslD(usrVo.getUserObj().getCdsid());
		usr.setKda003FnN(usrVo.getfName());
		usr.setKda003LnN(usrVo.getlName());
		usr.setKda003EmailidD(usrVo.getEmail());
		usr.setKda003WrkPhR(usrVo.getwPhone());
		usr.setKda003AddrLn1X(usrVo.getAddr1());
		usr.setKda003AddrLn2X(usrVo.getUserObj().getAdrr2());
		usr.setKda003CtyN(usrVo.getUserObj().getCity());
		usr.setKda003StN(usrVo.getUserObj().getState());
		usr.setKda003PstlC(usrVo.getUserObj().getpCode());
		usr.setKda003CntryN(usrVo.getUserObj().getCountry().getValue());
		usr.setKda003PreflangC(usrVo.getUserObj().getLang().getValue());
		usr.setKda003CmtX(usrVo.getUserObj().getComments());
		usr.setKda003StatC("D");		
		T_Role_002 role=roleDaoService.findByesip002rolen(usrVo.getUserObj().getRole().getValue());
		usr.setRole(role);
		usr.setKda003LstUpdtUsrC("esipUser");
		usr.setKda003LstUpdtS(new Timestamp(System.currentTimeMillis()));
		userDaoService.denyUserUsingVo(usr);
		System.out.println("Selected List"+usrVo.getSelectedList().size());
        List<T_UserAreaInfo_022> userArea=userAreaService.findByuser(usrVo.getUserObj().getCdsid());
        int deleteCount=0;
        if(userArea!=null && !userArea.isEmpty())
        {
        
        	for(T_UserAreaInfo_022 usrAreaObj:userArea)
        	{
        	int count=userAreaService.deleteUserAreaUsingSakey(usrAreaObj.getEsip022UsrAreaInfoSak());
        	deleteCount=deleteCount+count;
        	}
        }
        
        System.out.println("Delete Count-------------------------"+deleteCount);
		System.out.println("Selected List"+usrVo.getSelectedList().size());
		if(usrVo.getSelectedList()!=null && !usrVo.getSelectedList().isEmpty())
		{
		for(String areaVar:usrVo.getSelectedList())
		{
			T_UserAreaInfo_022 userAreaDetail=new  T_UserAreaInfo_022();
			usr=userDaoService.findByesip001WslD(usrVo.getUserObj().getCdsid());
			userAreaDetail.setUser(usr);
			String[] areaCode=areaVar.split("-");
			T_Area_009 finalArea=areaService.findByesip009areaC(areaCode[0]);
			userAreaDetail.setArea(finalArea);
			userAreaDetail.setEsip022CrtprocC("save");
			userAreaDetail.setEsip022CrtUsrC("esipUser");
			userAreaDetail.setEsip022CrtS(new Timestamp(System.currentTimeMillis()));
			userAreaDetail.setEsip022LstprocC("save");
			userAreaDetail.setEsip022LstUpdtUsrC("esipUser");
			userAreaDetail.setEsip022LstUpdtS(new Timestamp(System.currentTimeMillis()));
			userAreaService.save(userAreaDetail);
		}
		}
	}*/
	public List<SelectedItem> getAllRoles()
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
			for(T_Role_001 role:resultList)
			{
				SelectedItem svo=new SelectedItem();
				svo.setKey(role.getKda001rolec());
				svo.setValue(role.getKda001rolen());
				roleList.add(svo);
			}
			
		}
		
		return roleList;
	}

public List<String> getRoleList()
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
	public List<String> getAllLanguagesPopulate()
	{
		String langCode="L";
		String langName="Language";
		List<String> langList=new ArrayList<String>();
		List<T_PARM_006> resultList=paramService.findBytParm006TypC(langCode, langName);
		if(resultList!=null && !resultList.equals(""))
		{
			
			for(T_PARM_006 paramVal:resultList)
			{
				/*SelectedItem svo=new SelectedItem();
				svo.setKey(paramVal.getEsip003ParmCode());
				svo.setValue(paramVal.getEsip003ParmRemarks());*/
				langList.add(paramVal.getKda011ParmX());
			}
			
		}
		
		return langList;
	}
	
	public List<SelectedItem> getAllLanguages()
	{
		String langCode="L";
		String langName="Language";
		List<SelectedItem> langList=new ArrayList<SelectedItem>();
		List<T_PARM_006> resultList=paramService.findBytParm006TypC(langCode, langName);
		if(resultList!=null && !resultList.equals(""))
		{
			SelectedItem svo1=new SelectedItem();
			svo1.setKey(null);
			svo1.setValue("Please Select");
			langList.add(svo1);	
			for(T_PARM_006 paramVal:resultList)
			{
				SelectedItem svo=new SelectedItem();
				svo.setKey(paramVal.getKda011ParmC());
				svo.setValue(paramVal.getKda011ParmX());
				langList.add(svo);
			}
			
		}
		
		return langList;
	}
	
	public List<SelectedItem> getAllCountries()
	{
		String langCode="C";
		String langName="Country";
		List<SelectedItem> countryList=new ArrayList<SelectedItem>();
		List<T_PARM_006> resultList=paramService.findBytParm006TypC(langCode, langName);
		if(resultList!=null && !resultList.equals(""))
		{
			SelectedItem svo1=new SelectedItem();
			svo1.setKey(null);
			svo1.setValue("Please Select");
			countryList.add(svo1);
			for(T_PARM_006 paramVal:resultList)
			{
				SelectedItem svo=new SelectedItem();
				svo.setKey(paramVal.getKda011ParmC());
				svo.setValue(paramVal.getKda011ParmX());
				countryList.add(svo);
			}
			
		}
		
		return countryList;
	}
	public List<String> getAllCountriesPopulate()
	{
		String langCode="C";
		String langName="Country";
		List<String> countryList=new ArrayList<String>();
		List<T_PARM_006> resultList=paramService.findBytParm006TypC(langCode, langName);
		if(resultList!=null && !resultList.equals(""))
		{
			
			for(T_PARM_006 paramVal:resultList)
			{
			/*	SelectedItem svo=new SelectedItem();
				svo.setKey(paramVal.getEsip003ParmCode());
				svo.setValue(paramVal.getEsip003ParmRemarks());*/
				countryList.add(paramVal.getKda011ParmX());
			}
			
		}
		
		return countryList;
	}
	public List<String> getAreaCodes()
	{
		List<String> areaList=new ArrayList<String>();
		String activeFlag="Y";
		/*Iterable<T_Area_009> resultList=areaService.findByesip009ActvF(activeFlag);
		if(resultList!=null && !resultList.equals(""))
		{
			for(T_Area_009 area:resultList)
			{
				areaList.add(area.getEsip009areaC()+"-"+area.getEsip009areaX());
			}
			
		}*/
		
		return areaList;
	}
	public void saveUserInfo(UserAreaVo usrVo)
	{
		
		T_User_003 usr=new T_User_003();
		usr.setKda003WslD(usrVo.getUserObj().getCdsid());
		usr.setKda003PswdP(usrVo.getUserObj().getPassword());
		usr.setKda003FnN(usrVo.getUserObj().getfName());
		usr.setKda003LnN(usrVo.getUserObj().getlName());
		usr.setKda003EmailidD(usrVo.getUserObj().getEmail());
		usr.setKda003WrkPhR(usrVo.getUserObj().getwPhone());
		usr.setKda003AddrLn1X(usrVo.getUserObj().getAddr1());
		usr.setKda003AddrLn2X(usrVo.getUserObj().getAdrr2());
		usr.setKda003CtyN(usrVo.getUserObj().getCity());
		usr.setKda003StN(usrVo.getUserObj().getState());
		usr.setKda003PstlC(usrVo.getUserObj().getpCode());
		usr.setKda003CntryN(usrVo.getUserObj().getCountry().getValue());
		usr.setKda003PreflangC(usrVo.getUserObj().getLang().getValue());
		usr.setKda003ActvF("Y");
		usr.setKda003StatC("P");
		usr.setKda003CrtUsrC("esipUser");
		usr.setKda003CrtS(new Timestamp(System.currentTimeMillis()));
		T_Role_001 role=roleDaoService.findByesip002rolen(usrVo.getUserObj().getRole().getValue());
		usr.setRole(role);
		usr.setKda003LstUpdtUsrC("esipUser");
		usr.setKda003LstUpdtS(new Timestamp(System.currentTimeMillis()));
	    userDaoService.save(usr);
	    
		/*if(usrVo.getSelectedList()!=null && !usrVo.getSelectedList().isEmpty())
		{
		for(String areaVar:usrVo.getSelectedList())
		{
			T_UserAreaInfo_022 userAreaDetail=new  T_UserAreaInfo_022();
			usr=userDaoService.findByesip001WslD(usrVo.getUserObj().getCdsid());
			userAreaDetail.setUser(usr);
			String[] areaCode=areaVar.split("-");
			T_Area_009 finalArea=areaService.findByesip009areaC(areaCode[0]);
			userAreaDetail.setArea(finalArea);
			userAreaDetail.setEsip022CrtprocC("save");
			userAreaDetail.setEsip022CrtUsrC("esipUser");
			userAreaDetail.setEsip022CrtS(new Timestamp(System.currentTimeMillis()));
			userAreaDetail.setEsip022LstprocC("save");
			userAreaDetail.setEsip022LstUpdtUsrC("esipUser");
			userAreaDetail.setEsip022LstUpdtS(new Timestamp(System.currentTimeMillis()));
			userAreaService.save(userAreaDetail);
		}
		}*/
	}
	public void updateUser(UserAreaVo usrVo)
	{
		
		T_User_003 usr=new T_User_003();
		usr.setKda003WslD(usrVo.getUserObj().getCdsid());
		usr.setKda003AddrLn2X(usrVo.getUserObj().getAdrr2());
		usr.setKda003CtyN(usrVo.getUserObj().getCity());
		usr.setKda003StN(usrVo.getUserObj().getState());
		usr.setKda003PstlC(usrVo.getUserObj().getpCode());
		usr.setKda003CntryN(usrVo.getUserObj().getCountry().getValue());
		usr.setKda003PreflangC(usrVo.getUserObj().getLang().getValue());
		T_Role_001 role=roleDaoService.findByesip002rolen(usrVo.getUserObj().getRole().getValue());
		usr.setRole(role);
		usr.setKda003LstUpdtUsrC("esipUser");
		usr.setKda003LstUpdtS(new Timestamp(System.currentTimeMillis()));
		int noOfRowsUpdated=userDaoService.updateUser(usr);
		System.out.println(noOfRowsUpdated);
		
		
		/*System.out.println("Selected List"+usrVo.getSelectedList().size());
        List<T_UserAreaInfo_022> userArea=userAreaService.findByuser(usrVo.getUserObj().getCdsid());
        int deleteCount=0;
        if(userArea!=null && !userArea.isEmpty())
        {
        
        	for(T_UserAreaInfo_022 usrAreaObj:userArea)
        	{
        	int count=userAreaService.deleteUserAreaUsingSakey(usrAreaObj.getEsip022UsrAreaInfoSak());
        	deleteCount=deleteCount+count;
        	}
        }
        
        System.out.println("Delete Count-------------------------"+deleteCount);
		System.out.println("Selected List"+usrVo.getSelectedList().size());*/
		/*if(usrVo.getSelectedList()!=null && !usrVo.getSelectedList().isEmpty())
		{
		for(String areaVar:usrVo.getSelectedList())
		{
			T_UserAreaInfo_022 userAreaDetail=new  T_UserAreaInfo_022();
			usr=userDaoService.findByesip001WslD(usrVo.getUserObj().getCdsid());
			userAreaDetail.setUser(usr);
			String[] areaCode=areaVar.split("-");
			T_Area_009 finalArea=areaService.findByesip009areaC(areaCode[0]);
			userAreaDetail.setArea(finalArea);
			userAreaDetail.setEsip022CrtprocC("save");
			userAreaDetail.setEsip022CrtUsrC("esipUser");
			userAreaDetail.setEsip022CrtS(new Timestamp(System.currentTimeMillis()));
			userAreaDetail.setEsip022LstprocC("save");
			userAreaDetail.setEsip022LstUpdtUsrC("esipUser");
			userAreaDetail.setEsip022LstUpdtS(new Timestamp(System.currentTimeMillis()));
			userAreaService.save(userAreaDetail);
		}
		}*/
	}
}
