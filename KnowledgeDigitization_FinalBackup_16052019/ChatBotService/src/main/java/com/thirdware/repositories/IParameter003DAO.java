package com.thirdware.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import com.thirdware.entity.E_MDAT006_PARM;
import com.thirdware.entity.T_PARM_006;

@Transactional
@Repository
public interface IParameter003DAO extends CrudRepository<E_MDAT006_PARM, Long>{
	
	@Query("from E_MDAT006_PARM where paramType=:paramType and parameterName=:paramName")
	public List<E_MDAT006_PARM> findByesip003ParmTypC(@Param("paramType") String paramT,@Param("paramName") String paramName);
	
	@Query("from E_MDAT006_PARM where paramType=:paramType and parameterDescription=:paramDesc")
	public E_MDAT006_PARM findByesip003ParmRemarks(@Param("paramType") String paramT,@Param("paramDesc") String paramName);

	@Query("select distinct parameterCode from E_MDAT006_PARM where parameterName='W' ")
	public List<String> getWarningType();

	@Query("from T_PARM_006 where kda011ParmTypC=:paramType and kda011ParmN=:paramName")
	public List<T_PARM_006> findBytParm006TypC(@Param("paramType") String paramT,@Param("paramName") String paramName);
	
	@Query("from T_PARM_006 where kda011ParmTypC=:paramType and kda011ParmX=:paramDesc")
	public T_PARM_006 findByTParm006Remarks(@Param("paramType") String paramT,@Param("paramDesc") String paramName);
}
