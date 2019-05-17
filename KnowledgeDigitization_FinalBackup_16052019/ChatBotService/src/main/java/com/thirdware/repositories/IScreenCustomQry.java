package com.thirdware.repositories;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.thirdware.entity.T_RoleMap_007;
import com.thirdware.entity.T_Screen_002;
import com.thirdware.vo.user.MapRoleVo;


@Repository
public class IScreenCustomQry {
	
	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	DataSource dataSource;
	
	Connection conn=null;
public Iterable<String> getScreenNames()
{
	StringBuffer query=new StringBuffer("Select distinct screen.kda002ScreenN from T_Screen_002 screen");
	Query qry=em.createQuery(query.toString());
	List<String> resultList =qry.getResultList();
	return resultList;
}

public List<T_RoleMap_007> findScreenRoleDB(String screen,String role)
{
	StringBuffer query=new StringBuffer("select roleMap "+
			//" rol.kda001rolen ,roleMap.kda007RoleMapSak,rol.kda001rolesak, "+
			//" screen.kda002ScreenMstrSak "
			  "from "+
			" T_Role_001 rol inner join T_RoleMap_007 roleMap  on "+  
			" rol.kda001rolesak=roleMap.roleInRoleMap "+
			" inner join T_Screen_002 screen on "+
			" roleMap.screenInRoleMap=screen.kda002ScreenMstrSak "+  
			" where roleMap.kda007ActvF='Y' and screen.kda002ScreenN ='"+screen+"' and rol.kda001rolen ='"+role+"'  ");
	
	Query qry = em.createQuery(query.toString());
	 List<T_RoleMap_007> result = qry.getResultList();
	 return result;
	
	
}

public List<T_Screen_002>  findScreenFunctionList(String cdsid)
{
	StringBuffer query=new StringBuffer("select screen from T_Screen_002 screen join "+
 "T_RoleMap_007 roleMap  on screen.kda002ScreenMstrSak=roleMap.screenInRoleMap join"+ 
" T_User_003 usr on roleMap.roleInRoleMap=usr.role where "+
 "usr.kda003ActvF='Y'and usr.kda003WslD='"+cdsid+"'") ;
	
	Query qry = em.createQuery(query.toString());
	 List<T_Screen_002> result = qry.getResultList();
	 return result;
			
}

public List<T_RoleMap_007> findSakeyList(String screen,String function,String role)
{

	StringBuffer query=new StringBuffer("select roleMap "+
//" rol.kda001rolen ,roleMap.kda007RoleMapSak,rol.kda001rolesak, "+
//" screen.kda002ScreenMstrSak "
  "from "+
" T_Role_001 rol inner join T_RoleMap_007 roleMap  on "+  
" rol.kda001rolesak=roleMap.roleInRoleMap "+
" inner join T_Screen_002 screen on "+
" roleMap.screenInRoleMap=screen.kda002ScreenMstrSak "+  
" where roleMap.kda007ActvF='Y' and screen.kda002ScreenN ='"+screen+"' "+
		" and screen.kda002FunctionN  IN "+function+" and rol.kda001rolen = '"+role+"' ");
	 Query qry = em.createQuery(query.toString());
	 List<T_RoleMap_007> result = qry.getResultList();
	 System.out.println(result);
	 System.out.println(result.size());
	 return result;
}
public List<T_RoleMap_007> getMapRoleList(MapRoleVo maproleVo)
{
	
	boolean screenFlag =(maproleVo.getScreenSearch().getKey() !=null && !maproleVo.getScreenSearch().getKey().isEmpty()) ?true:false;
	boolean functionFlag =(maproleVo.getFunctionString()!=null && !maproleVo.getFunctionString().isEmpty())?true:false;
	boolean roleNameFlag =(maproleVo.getRoleNameString()!=null && !maproleVo.getRoleNameString().isEmpty())?true:false;
	
	
	StringBuffer query=new StringBuffer("select roleMap "+
//" rol.kda001rolen ,roleMap.kda007RoleMapSak,rol.kda001rolesak, "+
//" screen.kda002ScreenMstrSak "
  "from "+
" T_Role_001 rol inner join T_RoleMap_007 roleMap  on "+  
" rol.kda001rolesak=roleMap.roleInRoleMap "+
" inner join T_Screen_002 screen on "+
" roleMap.screenInRoleMap=screen.kda002ScreenMstrSak "+  
" where roleMap.kda007ActvF='Y' ");
	if(screenFlag ||functionFlag||roleNameFlag)
	{
		query.append(" AND ");
	}
	
	if(screenFlag)
	{
		query.append("screen.kda002ScreenN ='"+maproleVo.getScreenSearch().getKey()+"' ");	
		if(functionFlag ||roleNameFlag )
			query.append(" AND ");
	}
	if(functionFlag)
	{
		query.append("screen.kda002FunctionN  IN "+maproleVo.getFunctionString()+" ");
		
		if(roleNameFlag )
			query.append(" AND ");
	}
	
	if(roleNameFlag)
	{
		query.append("rol.kda001rolen IN  "+maproleVo.getRoleNameString()+" ");
		
	}
	query.append(" order by rol.kda001rolesak, roleMap.kda007LstUpdtS desc ");
	
	 Query qry = em.createQuery(query.toString());
	 List<T_RoleMap_007> result = qry.getResultList();
	 System.out.println(result.toString());
	 return result;

}
}
