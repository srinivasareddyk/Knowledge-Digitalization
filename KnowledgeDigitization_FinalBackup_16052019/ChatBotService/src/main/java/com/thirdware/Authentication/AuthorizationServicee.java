package com.thirdware.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thirdware.entity.T_User_003;
import com.thirdware.repositories.IUser001DAO;



@Service
public class AuthorizationServicee {

	
	@Autowired
	IUser001DAO dao;
	
	

	public AuthStatusvo getUserStatusById(String cdsid, String password)
	{
		AuthStatusvo authStatusVo=new AuthStatusvo(); 
		if(cdsid!=null && !cdsid.equalsIgnoreCase(""))
		{
			T_User_003 userDao=dao.findByesip001WslD(cdsid);
			if(userDao!=null) {
				if(password.equals(userDao.getKda003PswdP())){
				
				authStatusVo.setActiveStatus(userDao.getKda003ActvF());
				authStatusVo.setUserRegStatus(userDao.getKda003StatC());
				authStatusVo.setFirstName(userDao.getKda003FnN());
				authStatusVo.setCdsid(userDao.getKda003WslD());
				
			}
				else {
					authStatusVo.setCredentials("Invalid credentials");	
				}
		
		}
			else {
				authStatusVo.setCredentials("Invalid credentials");
			}
	}	
		return authStatusVo;
		
	}



	public AuthStatusvo checkUser(String email) {
	
		T_User_003 T_User_018 =dao.findByEmail(email);
		AuthStatusvo authStatusVo=new AuthStatusvo();
		if(T_User_018!=null) {
	
		authStatusVo.setFirstName(T_User_018.getKda003FnN());
		authStatusVo.setUserRegStatus(T_User_018.getKda003StatC());
		authStatusVo.setActiveStatus(T_User_018.getKda003ActvF());
		authStatusVo.setCdsid(T_User_018.getKda003WslD());
		}
		return authStatusVo;
	}
 
}
