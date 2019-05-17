package com.thirdware.repositories.parameter;



import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
@Transactional
public interface I_MDAT006_PARM  extends JpaRepository<com.thirdware.entity.E_MDAT006_PARM, Long>{
	
	@Query("from  E_MDAT006_PARM where parameterName=:dbtype")
	public List<com.thirdware.entity.E_MDAT006_PARM> findByParamName(@Param("dbtype") String dbtype);

}