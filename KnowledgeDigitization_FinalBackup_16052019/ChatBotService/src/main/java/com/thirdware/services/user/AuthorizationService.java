package com.thirdware.services.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thirdware.entity.T_Screen_002;
import com.thirdware.entity.T_User_003;
import com.thirdware.repositories.IScreenCustomQry;
import com.thirdware.repositories.IUser001DAO;
import com.thirdware.vo.AuthStatusVo;
import com.thirdware.vo.AuthorizationVo;

@Service
public class AuthorizationService {
	
	@Autowired
	IScreenCustomQry screenCustomService;
	
	@Autowired
	IUser001DAO userService;
	
	
	
	public List<AuthorizationVo> getScreenAuthList(String cdsid)
	{
		List<AuthorizationVo> authList=new ArrayList<AuthorizationVo>();
		if(cdsid!=null && !cdsid.equalsIgnoreCase(""))
		{
			List<T_Screen_002> screenDaoList=screenCustomService.findScreenFunctionList(cdsid);
			if(screenDaoList!=null && !screenDaoList.isEmpty())
			{
				for(T_Screen_002 screenDAo:screenDaoList)
				{
					AuthorizationVo authVo=new AuthorizationVo();
					authVo.setScreenName(screenDAo.getKda002ScreenN());
					authVo.setFunctionName(screenDAo.getKda002FunctionN());
					authList.add(authVo);
				}
				
				
			}
		}
	
		return authList;
		
	}
	
	public AuthStatusVo getUserStatusById(String cdsid)
	{
		AuthStatusVo authStatusVo=new AuthStatusVo(); 
		if(cdsid!=null && !cdsid.equalsIgnoreCase(""))
		{
			T_User_003 userDao=userService.findByesip001WslD(cdsid);
			if(userDao!=null && !userDao.equals(""))
			{
				authStatusVo.setActiveStatus(userDao.getKda003ActvF());
				authStatusVo.setUserRegStatus(userDao.getKda003StatC());
			}
		}
		
		return authStatusVo;
		
	}
 
}
