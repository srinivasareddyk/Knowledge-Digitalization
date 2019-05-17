package com.thirdware.repositories;

import java.sql.Connection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.thirdware.entity.T_RoleMap_007;
import com.thirdware.entity.T_User_003;

@Repository
public class RoleCustomQry {
	@PersistenceContext
    private EntityManager em;
	@Autowired
	DataSource dataSource;
	
	Connection conn=null;
	
	public boolean getUserCountByRole(String roleCode)
	{
		boolean existFlag=false;
		StringBuffer query=new StringBuffer(" SELECT usr FROM T_User_003 usr inner join T_Role_001 r on usr.role=r.kda001rolesak where r.kda001rolec ='"+roleCode+"'");
		Query qry=em.createQuery(query.toString());
		List<T_User_003> result =qry.getResultList();
		if(result.size()>0)
		{
			existFlag=true;
		}
		return existFlag;
	}
	public boolean getRoleMapCountByRole(String roleCode)
	{
		boolean existFlag=false;
		StringBuffer query=new StringBuffer(" SELECT rolMap FROM T_RoleMap_007 rolMap inner join T_Role_001 r on rolMap.roleInRoleMap=r.kda001rolesak where r.kda001rolec ='"+roleCode+"'");
		Query qry=em.createQuery(query.toString());
		List<T_RoleMap_007> result =qry.getResultList();
		if(result.size()>0)
		{
			existFlag=true;
		}
		return existFlag;
	}
}