package com.thirdware.repositories.dbconfig;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.thirdware.entity.T_APPLICATION_004;
import com.thirdware.entity.T_APPL_DB_DTL_010;
import com.thirdware.entity.T_Appl_Dtl_004;

@Repository
public interface DBconfigRepository extends CrudRepository<T_APPL_DB_DTL_010, Integer>{
	
	@Query("select databasename from T_APPL_DB_DTL_010 where databasename=:dbName and itmsNo!=:Itmsno")
	public String uniqueDBName(@Param("dbName") String dbName,@Param("Itmsno") Integer Itmsno);
	
	@Query("select e from T_APPL_DB_DTL_010 e where e.itmsNo=:itmsNo")
	public T_APPL_DB_DTL_010 uniqueItmsNo(@Param("itmsNo") Integer itmsNo);
	
	@Query("select databasename from T_APPL_DB_DTL_010 where databasename=:dbName ")
	public String uniqueDB(@Param("dbName") String dbName);
	
	@Query("from T_APPL_DB_DTL_010 where application=:app_dtl")
	public T_APPL_DB_DTL_010 findByapplication(@Param("app_dtl") T_Appl_Dtl_004 app_dtl);
	
	@Query("from T_APPL_DB_DTL_010 where itmsNo=:itmsno")
	public T_APPL_DB_DTL_010 findByapplication(@Param("itmsno") int itmsno);
	
	@Query("from T_APPL_DB_DTL_010 where databasename=:dbname")	
	public T_APPL_DB_DTL_010 findByDatabaseName(@Param("dbname") String string);

	@Query("from T_APPL_DB_DTL_010 where databasetype=:dbtype")	
	public List<T_APPL_DB_DTL_010> findByDatabasetype(@Param("dbtype") String string);

	@Query("select e from T_APPL_DB_DTL_010 e  order by e.lastupdateddate desc")
	public List<T_APPL_DB_DTL_010> findAllOrderBy();

	
	

}