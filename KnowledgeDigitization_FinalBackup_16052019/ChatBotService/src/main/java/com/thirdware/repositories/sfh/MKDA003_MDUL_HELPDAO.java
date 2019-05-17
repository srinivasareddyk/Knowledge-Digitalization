package com.thirdware.repositories.sfh;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.thirdware.entity.MKDA008_MDUL_HELP;

@Transactional
@Repository
public interface MKDA003_MDUL_HELPDAO extends CrudRepository<MKDA008_MDUL_HELP, Long> {
	
	 @Query(" FROM MKDA008_MDUL_HELP t where t.module in (:ids) " ) 
	  public List<MKDA008_MDUL_HELP> findDetailsByID(@Param("ids") List<Long> ids);	
	  
	  @Query(" select t.module as KDA005_MDUL_K FROM MKDA008_MDUL_HELP t " ) 
	  public List<Long> getAllScreenIds();	
	 @Modifying
	 @Query("update MKDA008_MDUL_HELP e set  e.helpDocFileName = :#{#vo.getHelpDocFileName()} ,  e.helpDocLoc = :#{#vo.getHelpDocLoc()} , e.helpVideoFileName = :#{#vo.getHelpVideoFileName()} , e.helpVideoLoc = :#{#vo.getHelpVideoLoc()}, e.updatedUser = :#{#vo.getUpdatedUser()},e.updatedTime = :#{#vo.getUpdatedTime()} , e.updatedProcess = :#{#vo.getUpdatedProcess()} where e.module = :#{#vo.getModule()}")
	 int update(@Param("vo") MKDA008_MDUL_HELP  vo);                                                                                                                                                                             




}
