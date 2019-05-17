package com.thirdware.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.thirdware.entity.T_Appl_Dtl_004;

@Repository
public interface IAppDtlDAO extends CrudRepository<T_Appl_Dtl_004, Long> {
	
	@Query("select kda004ApplName from T_Appl_Dtl_004 ")
	public List<String> findAllAppNames();
	
	@Query("from T_Appl_Dtl_004 where kda004ApplItmsNum=:itms_no")
	public T_Appl_Dtl_004 findBydat001ApplItmsNum(@Param("itms_no") Integer itms_no);
	
	@Query(" from T_Appl_Dtl_004 where kda004ApplName=:applicationName")
	public  T_Appl_Dtl_004 findBydat001ApplItmsNum(@Param("applicationName") String applicationName);
}