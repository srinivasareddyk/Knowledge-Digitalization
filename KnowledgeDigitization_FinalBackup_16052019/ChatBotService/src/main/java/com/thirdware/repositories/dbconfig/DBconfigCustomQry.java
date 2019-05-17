package com.thirdware.repositories.dbconfig;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.thirdware.entity.T_APPL_DB_DTL_010;
import com.thirdware.utils.dbconfig.CommonUtils;
import com.thirdware.vo.dbconfig.DBconfigSearchVo;
import com.thirdware.vo.dbconfig.DBconfigdatatableVo;

@Repository
public class DBconfigCustomQry {

	@Autowired
	DataSource dataSource;
	@PersistenceContext
	private EntityManager em;
	


	public List<String> searchProjectName(String pn) {
		System.out.println("query"+pn);
		Query query = em.createQuery(
				"SELECT kda004ApplName FROM T_Appl_Dtl_004  where kda004ApplName LIKE '%" + pn + "%'");
		List<String> result = query.getResultList();
		return result ;
	}
	
	
}