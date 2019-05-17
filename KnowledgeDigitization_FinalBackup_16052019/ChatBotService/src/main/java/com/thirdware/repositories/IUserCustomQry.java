package com.thirdware.repositories;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.thirdware.entity.T_User_003;
import com.thirdware.entity.T_User_003;
import com.thirdware.vo.user.UserManageVo;


@Repository
public class IUserCustomQry {

	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	DataSource dataSource;
	
	Connection conn=null;
	
	public List<T_User_003> getUsersEntity(UserManageVo userVo) {
		
		boolean cdsidFlag =(userVo.getCdsidSearch()!=null && !userVo.getCdsidSearch().isEmpty())?true:false;
		boolean fNameFlag =(userVo.getfNameSearch()!=null && !userVo.getfNameSearch().isEmpty())?true:false;
		boolean lNameFlag =(userVo.getlNameSearch()!=null && !userVo.getlNameSearch().isEmpty())?true:false;
		boolean statusFlag =(userVo.getStatusSearch().getKey()!=null && !userVo.getStatusSearch().getKey().isEmpty())?true:false;
		boolean roleFlag =(userVo.getRoleSearch().getKey()!=null && !userVo.getRoleSearch().getKey().isEmpty())?true:false;
		
		StringBuffer query=new StringBuffer("SELECT usr FROM T_User_003 usr inner join T_Role_001 r on usr.role=r.kda001rolesak WHERE 1=1 and usr.kda003StatC='A' ");
		
		if(cdsidFlag|| fNameFlag ||lNameFlag||statusFlag||roleFlag )
		{
			query.append(" AND ");
		}
		if(cdsidFlag)
		{
			
			//query.append("usr.kda003_WSL_D like '%"+userVo.getCdsidSearch()+"%' ");
			query.append("usr.kda003WslD like '%"+userVo.getCdsidSearch()+"%' ");
			if(fNameFlag ||lNameFlag||statusFlag||roleFlag )
				query.append(" AND ");
		}
		if(fNameFlag)
		{
			//query.append("usr.kda003_FN_N like '%"+userVo.getfNameSearch()+"%' ");
			query.append("usr.kda003FnN like '%"+userVo.getfNameSearch()+"%' ");
			
			if(lNameFlag||statusFlag||roleFlag )
				query.append(" AND ");
		}
		
		if(lNameFlag)
		{
			//query.append("usr.kda003_LN_N like '%"+userVo.getlNameSearch()+"%' ");
			query.append("usr.kda003LnN like '%"+userVo.getlNameSearch()+"%' ");
			if(statusFlag||roleFlag )
				query.append(" AND ");
		}
		if(roleFlag)
		{
			//query.append("r.kda001_ROLE_N like '%"+userVo.getRoleSearch().getValue()+"%' ");
			query.append("r.kda001rolen like '%"+userVo.getRoleSearch().getValue()+"%' ");
			System.out.println(userVo.getRoleSearch().getValue());
			if(statusFlag )
				query.append(" AND ");
		}
		if(statusFlag)
		{
			//query.append("usr.kda003_ACTV_F = '"+userVo.getStatusSearch().getKey()+"' ");
			query.append("usr.kda003ActvF = '"+userVo.getStatusSearch().getKey()+"' ");
		}
		 Query qry=em.createQuery(query.toString());
		List<T_User_003> result =qry.getResultList();
		
		/*for(T_User_003 t : result)
		{
			System.out.println(t.getkda003LnN());
			System.out.println(t.getRole().getkda001rolen());
			System.out.println(t.getRole().getkda001rolen());
		}*/
		return result;
		
	}
	

	/*public void getUsersResultSet(String wsl, String name) throws SQLException {
		
		System.out.println("******************** Test ********************");
		
		try
		{
			
		conn = dataSource.getConnection();
		
		StringBuffer query=new StringBuffer("SELECT kda003_WSL_D,kda003_FN_N FROM dbo.Nkda003_USR WHERE 1=1 ");
		
		if(wsl!=null && !wsl.equals(""))
		{
			query.append("AND kda003_WSL_D='"+wsl+"' ");	
		}
		
		if(name!=null && !name.equals(""))
		{
			query.append("AND kda003_FN_N ='"+name+"' ");	
		} 
		
		ArrayList<HashMap<String, Object>> resultList = DBPropertyUtil.retrieveDataBySQL(query.toString(), conn);
		
		for(HashMap<String, Object> map :resultList)
		{
			System.out.println(map.get("kda003_WSL_D"));
			System.out.println(map.get("kda003_FN_N"));
		}
		
		}
		finally
		{
			conn.close();
		}
		
		System.out.println("******************** Test End ********************");
		
	}
	*/
	
}
