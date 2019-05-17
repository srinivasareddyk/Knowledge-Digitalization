package com.thirdware.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.thirdware.entity.T_Appl_Dtl_004;
import com.thirdware.entity.T_Module_005;

@Transactional
@Repository
public interface IModuleDAO extends CrudRepository<T_Module_005, Long>{
	
	
	
	@Query("from T_Module_005 where kda005ParntMdulKey is null order by kda004ApplItmsNum")
	public List<T_Module_005> findAllOrderBydat001ApplItmsNum();
	
	@Query("from T_Module_005  order by kda004ApplItmsNum")
	public List<T_Module_005> findAllOrderByApplItmsNum();
	
	@Query("from T_Module_005 where kda005ParntMdulKey is null order by kda005LstUpdtS desc")
	public List<T_Module_005> findAllOrderBydat004LstUpdtS();
	
	@Query("from T_Module_005 where kda005ModuleFlag=:flag order by kda005LstUpdtS desc")
	public List<T_Module_005> findAllSubModulesOrderBydat004LstUpdtS(@Param("flag") String flag);
	
	@Query("from T_Module_005  where kda005MdulName=:prntmod_name")
	public T_Module_005 findBydat004MdulName(@Param("prntmod_name") String prntmod_name);
	
	@Query("from T_Module_005 where kda004ApplItmsNum=:app_itms and kda005ParntMdulKey is null ")
	public List<T_Module_005> findAllModules(@Param("app_itms") T_Appl_Dtl_004 app_itms);
	 
	
	@Query("from T_Module_005 where kda005ParntMdulKey=:prntmod_key and kda005ModuleFlag=:flag ")
	public List<T_Module_005> findAllSubModules(@Param("prntmod_key") T_Module_005 prntmod_key,@Param("flag") String flag);
	
	@Query("from T_Module_005 where kda004ApplItmsNum=:app_itms and kda005ModuleFlag=:flag ")
	public List<T_Module_005> findSubModulesByApplication(@Param("app_itms") T_Appl_Dtl_004 app_itms,@Param("flag") String flag);
	
	@Query("from T_Module_005 where kda005MdulKey=:mdulekey")
	public T_Module_005 findBydat004MdulKey(@Param("mdulekey") Long mdulekey);
	
	@Query("from T_Module_005 where kda005ModuleCode=:modulecode")
	public T_Module_005 findBydat004ModuleCode(@Param("modulecode") String modulecode);
	
	@Query("select kda005ModuleCode from T_Module_005 where kda004ApplItmsNum=:app_itms")
	public List<String> findAllModuleCodeByApplication(@Param("app_itms")T_Appl_Dtl_004 app_itms);
	
	
	
	@Query("from T_Module_005 where kda004ApplItmsNum=:app_itms and kda005ModuleCode=:modulecode")
	public T_Module_005 findBydat004ModuleCode(@Param("app_itms" )T_Appl_Dtl_004 app_itms,@Param("modulecode") String modulecode);
	
	@Query("select kda005ModuleCode from T_Module_005 where kda005ParntMdulKey is null")
	public List<String> findBydat004ModuleCode();
	
	@Query("select kda005ModuleCode from T_Module_005 where kda005ModuleFlag=:flag")
	public List<String> findAllSuBModuleCode(@Param("flag") String flag);
	
	@Query("select kda005ModuleCode from T_Module_005 where kda005ParntMdulKey is null and kda004ApplItmsNum=:app_itms")
	public List<String> findBydat004ModuleCode(@Param("app_itms" )T_Appl_Dtl_004 app_itms);
	
	
	@Modifying
	@Query("update T_Module_005 e set e.kda005MdulName=:#{#vo.getKda005MdulName()} , e.kda005ParntMdulKey=:#{#vo.getKda005ParntMdulKey()}, e.kda004ApplItmsNum=:#{#vo.getDat001ApplItmsNum()},e.kda005ScrnDesc=:#{#vo.getKda005ScrnDesc()},e.kda005LstUpdtUsrC = :#{#vo.getKda005LstUpdtUsrC()} ,  e.kda005LstUpdtProcC = :#{#vo.getKda005LstUpdtProcC()} , e.kda005LstUpdtS = :#{#vo.getKda005LstUpdtS()}   where e.kda005MdulKey = :#{#vo.getKda005MdulKey()}")
	int updateModule(@Param("vo") T_Module_005  vo);
	
	

}
