package com.thirdware.repositories.application;

import java.sql.Connection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.thirdware.entity.T_APPLICATION_004;
import com.thirdware.vo.application.ApplicationVo;


@Repository
public class ApplicationCustomQry {

	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	DataSource dataSource;
	
	Connection conn=null;
	
	 public List<T_APPLICATION_004> getApplicationEntity(ApplicationVo appliVoObj) {
			
			boolean itmsIdFlag =(appliVoObj.getSearchItmsId() !=null && !appliVoObj.getSearchItmsId().isEmpty()) ?true:false;
			boolean appNameFlag =(appliVoObj.getSearchApplicationName()!=null && !appliVoObj.getSearchApplicationName().equalsIgnoreCase(""))?true:false;
			boolean appscrmFlag =(appliVoObj.getSearchApplicationAcronym()!=null && !appliVoObj.getSearchApplicationAcronym().equalsIgnoreCase(""))?true:false;
			boolean appDescFlag =(appliVoObj.getSearchApplicationDesc()!=null && !appliVoObj.getSearchApplicationDesc().equalsIgnoreCase(""))?true:false;
			
			
			
			StringBuffer query=new StringBuffer("SELECT e FROM T_APPLICATION_004 e WHERE ");
			
			if(itmsIdFlag)
			{
				query.append("KDA004_APPL_ITMS_R IN "+appliVoObj.getSearchStrItmsNo()+" ");	
				if(appNameFlag ||appscrmFlag ||appDescFlag)
					query.append(" AND ");
			}
			if(appNameFlag)
			{
				query.append("KDA004_APPL_N like '%"+appliVoObj.getSearchApplicationName()+"%' ");
				
				if(appscrmFlag ||appDescFlag)
					query.append(" AND ");
			}
			
			if(appscrmFlag)
			{
				query.append("KDA004_APPL_ACRNM_C like '%"+appliVoObj.getSearchApplicationAcronym()+"%' ");
				
				if(appDescFlag)
					query.append(" AND ");
			}
			
			if(appDescFlag)
			{
				query.append("KDA004_APPL_X like '%"+appliVoObj.getSearchApplicationDesc()+"%' ");
				
			}
			
			query.append("order by KDA004_LST_UPDT_S desc ");
			
			 Query qry = em.createQuery(query.toString());
			List<T_APPLICATION_004> result =qry.getResultList();
			
			return result;
			
		}
	

	 public List<String> searchApplicationName(String pn) {
			Query qurey = em.createQuery(
					"SELECT kda004AppliName FROM T_APPLICATION_004 e where kda004AppliName LIKE '%" + pn + "%'");
			List<String> result = qurey.getResultList();
			return result;
		}
	
}
