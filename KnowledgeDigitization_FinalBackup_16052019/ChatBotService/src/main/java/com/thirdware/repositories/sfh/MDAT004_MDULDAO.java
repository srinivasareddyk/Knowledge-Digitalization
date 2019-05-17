package com.thirdware.repositories.sfh;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.thirdware.entity.MKDA005_MDUL;
import com.thirdware.entity.MKDA008_MDUL_HELP;

@Transactional
@Repository
public interface MDAT004_MDULDAO extends CrudRepository<MKDA005_MDUL, Long> {
		
	@Query("SELECT distinct t.kda004ApplItmsNum as KDA004_APPL_ITMS_R FROM T_Appl_Dtl_004 t")
	public   List<Integer> findAllItmsNumbersForGrid();
	
	@Query("SELECT cast(t.kda004ApplItmsNum AS text)+'-'+kda004ApplAcrnymCode as KDA005_MDUL_N FROM T_Appl_Dtl_004 t")
	public   List<String> findAllItmsNumbers();
	
	@Query("SELECT t.kda004ApplItmsNum as KDA004_APPL_ITMS_R,cast(t.kda004ApplItmsNum AS text)+'-'+kda004ApplAcrnymCode as KDA005_MDUL_N FROM T_Appl_Dtl_004 t")
	public   List<Object[]> findAllItmsNumbersByAcroniyum();
	
	
	  @Query("SELECT t.moduleId as KDA005_MDUL_K, t.moduleName as KDA005_MDUL_N FROM MKDA005_MDUL t where t.moduleFlag='MD'"
	  ) public List<Object[]> findAllModules();
	  
	  @Query("SELECT t.moduleId as KDA005_MDUL_K, t.moduleName as KDA005_MDUL_N FROM MKDA005_MDUL t where t.moduleFlag='SM' "
	  ) public List<Object[]> findAllSubModules();
	  
	  @Query("SELECT  t.moduleId as KDA005_MDUL_K,t.moduleName as KDA005_MDUL_N FROM MKDA005_MDUL t where t.moduleFlag='SC' "
	  ) public List<Object[]> findAllScreens();
	 
	
	  @Query("SELECT  t.itmsNumber as KDA005_PARNT_MDUL_K,t.moduleId as KDA005_MDUL_K, t.moduleName as KDA005_MDUL_N FROM MKDA005_MDUL t where t.moduleFlag='MD'"
	  ) public List<Object[]> findAllModules1();
	  
	  @Query("SELECT t.parentModuleKey as KDA005_PARNT_MDUL_K,t.moduleId as KDA005_MDUL_K, t.moduleName as KDA005_MDUL_N FROM MKDA005_MDUL t where t.moduleFlag='SM' "
	  ) public List<Object[]> findAllSubModules1();
	  
	  @Query("SELECT  t.parentModuleKey as KDA005_PARNT_MDUL_K,t.moduleId as KDA005_MDUL_K,t.moduleName as KDA005_MDUL_N FROM MKDA005_MDUL t where t.moduleFlag='SC' "
	  ) public List<Object[]> findAllScreens1();
	
	@Query("SELECT t.moduleId as KDA005_MDUL_K, t.moduleName as KDA005_MDUL_N FROM MKDA005_MDUL t where t.moduleFlag='M' and t.itmsNumber in (:itms)")
	public   List<Object[]> findAllModules(@Param("itms") List<String> itms);
	@Query("SELECT t.moduleId as KDA005_MDUL_K, t.moduleName as KDA005_MDUL_N FROM MKDA005_MDUL t where t.moduleFlag='SM' and t.parentModuleKey in (:moduleKey) ")
	public   List<Object[]> findAllSubModules(@Param("moduleKey") List<String> moduleKey);
	
	@Query("SELECT  t.moduleId as KDA005_MDUL_K,t.moduleName as KDA005_MDUL_N FROM MKDA005_MDUL t where t.moduleFlag='SC' and t.parentModuleKey in (:subModuleKey) ")
	public   List<Object[]> findAllScreens(@Param("subModuleKey") List<String> subModuleKey);
	
	@Query("select SC.moduleId as KDA005_MDUL_K,CAST (M.moduleId AS text)+'-'+M.moduleName +'#~#' + cast(SM.moduleId as text)+'-'+SM.moduleName +'#~#' + cast (SC.moduleId as text)+'-'+SC.moduleName+'#~#' +cast(t.kda004ApplItmsNum AS text)+'-'+t.kda004ApplAcrnymCode as KDA005_MDUL_N from MKDA005_MDUL SC  join MKDA005_MDUL  SM on  SM.moduleId=SC.parentModuleKey join MKDA005_MDUL  M on M.moduleId=SM.parentModuleKey join T_Appl_Dtl_004 t on t.kda004ApplItmsNum=SC.itmsNumber")
	public   List<Object[]> findAllApplicationDetails();
	
	
	@Query("select SC.moduleId as KDA005_MDUL_K,CAST (M.moduleId AS text)+'-'+M.moduleName +'#~#' + cast(SM.moduleId as text)+'-'+SM.moduleName +'#~#' + cast (SC.moduleId as text)+'-'+SC.moduleName+'#~#' +cast (SC.itmsNumber as text) as KDA005_MDUL_N from MKDA005_MDUL SC  join MKDA005_MDUL  SM on  SM.moduleId=SC.parentModuleKey join MKDA005_MDUL  M on M.moduleId=SM.parentModuleKey")
	public   List<Object[]> tfindAllApplicationDetails();
	
	@Query("select SC.moduleId as KDA005_MDUL_K from MKDA005_MDUL SC  join MKDA005_MDUL  SM on  SM.moduleId=SC.parentModuleKey join MKDA005_MDUL  M on M.moduleId=SM.parentModuleKey where SC.itmsNumber in(:itms)")
	public   List<Long> findAllApplicationDetailsByItms(@Param("itms") List<Integer> itms);
	
	@Query("select SC.moduleId as KDA005_MDUL_K from MKDA005_MDUL SC  join MKDA005_MDUL  SM on  SM.moduleId=SC.parentModuleKey join MKDA005_MDUL  M on M.moduleId=SM.parentModuleKey and M.moduleId in(:module) where SC.itmsNumber in(:itms)")
	  public List<Long> findAllApplicationDetailsByItmsModule(@Param("itms")List<Integer> itms,@Param("module") List<Long> module);
	  @Query("select SC.moduleId as KDA005_MDUL_K from MKDA005_MDUL SC  join MKDA005_MDUL  SM on  SM.moduleId=SC.parentModuleKey and SM.moduleId in(:subModule) join MKDA005_MDUL  M on M.moduleId=SM.parentModuleKey and M.moduleId in(:module) where SC.itmsNumber in(:itms)"
	  ) 
	  public List<Long> findAllApplicationDetailsByItmsModuleSubModule(@Param("itms") List<Integer> itms,@Param("module") List<Long> module,@Param("subModule") List<Long> subModule);
	  @Query("select SC.moduleId as KDA005_MDUL_K from MKDA005_MDUL SC  join MKDA005_MDUL  SM on  SM.moduleId=SC.parentModuleKey and SM.moduleId in(:subModule) join MKDA005_MDUL  M on M.moduleId=SM.parentModuleKey and M.moduleId in(:module) where SC.itmsNumber in(:itms) and SC.moduleId in(:screen)" ) 
	 public List<Long> findAllApplicationDetailsByItmsModuleSubModuleScreen(@Param("itms") List<Integer> itms,@Param("module") List<Long> module,@Param("subModule") List<Long> subModule,@Param("screen") List<Long> screen);
	@Query("select  SC.moduleId as KDA005_MDUL_K,CAST (SC.itmsNumber AS text)+'#~#'+M.moduleName +'#~#'+SM.moduleName +'#~#'+SC.moduleName as KDA005_MDUL_N from MKDA005_MDUL SC  join MKDA005_MDUL  SM on  SM.moduleId=SC.parentModuleKey join MKDA005_MDUL  M on M.moduleId=SM.parentModuleKey")
	public   List<Object[]> findAllApplicationDetailsForValidate();
	
	
	
}
