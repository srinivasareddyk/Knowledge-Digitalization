package com.thirdware.repositories.configUsrManual;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.thirdware.entity.T_APPLICATION_004;
import com.thirdware.entity.T_FIELD_HELP_006;
import com.thirdware.entity.T_Module_005;
import com.thirdware.entity.T_Module_MKDA003;


@Transactional
@Repository
public interface I_ConfigUsrManual_DTL_001DAO extends CrudRepository<T_FIELD_HELP_006, Long> {
	
	
	/*select u from User u order by u.address.town*/
	@Query("select e from T_Module_MKDA003 e  order by e.kda005LstUpdtS desc")
	public List<T_Module_MKDA003> findAllOrderBy();
	
	@Query("select e from T_APPLICATION_004 e  order by e.kda004LastUpdateDate desc")
	public List<T_APPLICATION_004> findAllApplItmsNoOrderBy();
	
	@Query("select e from T_FIELD_HELP_006 e  order by e.kda006LastUpdateDate desc")
	public List<T_FIELD_HELP_006> findAllFieldHelpOrderBy();
	
	/*@Query("from T_APPLICATION_004 where kda004AppliITMSNo=:itmsNo")
	public T_APPLICATION_004 findBy(@Param("itmsNo") String itmsNo);*/
	
	@Modifying
	@Query("update T_FIELD_HELP_006 e set  e.kda005MdulId = :#{#vo.getKda005MdulId()} ,  "
			+ "e.kda006FldCode = :#{#vo.getKda006FldCode()} , "
			+ "e.kda006FldName = :#{#vo.getKda006FldName()} , "
			+ "e.kda006AudoHelpFlag = :#{#vo.getKda006AudoHelpFlag()} , "
			+ "e.kda006ToolTipFlag = :#{#vo.getKda006ToolTipFlag()} , "
			+ "e.kda006HelpTextDesc = :#{#vo.getKda006HelpTextDesc()} , "
			+ "e.kda006LastUpdateProcess = :#{#vo.getKda006LastUpdateProcess()} , "
			+ "e.kda006LastUpdateUser = :#{#vo.getKda006LastUpdateUser()} , "
			+ "e.kda006LastUpdateDate = :#{#vo.getKda006LastUpdateDate()}   where e.kda006FieldId = :#{#vo.getKda006FieldId()}")
	int updateConfigUsrManualVo(@Param("vo") T_FIELD_HELP_006  vo);
	
	@Query("from T_Module_005 where kda005MdulKey=:modualId")
	public T_Module_005 findByModule(@Param("modualId") long itmsNo);
	
}

