package com.thirdware.repositories.application;

import java.util.List;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.thirdware.entity.T_APPLICATION_004;


@Transactional
@Repository
public interface I_APPLI_DTL_001DAO extends CrudRepository<T_APPLICATION_004, Long> {
	
	
	/*select u from User u order by u.address.town*/
	@Query("select e from T_APPLICATION_004 e  order by e.kda004LastUpdateDate desc")
	public List<T_APPLICATION_004> findAllOrderBy();
	
	@Query("from T_APPLICATION_004 where kda004AppliITMSNo=:itmsNo")
	public T_APPLICATION_004 findBy(@Param("itmsNo") String itmsNo);
	
	@Modifying
	@Query("update T_APPLICATION_004 e set  e.kda004AppliName = :#{#vo.getKda004AppliName()} ,  "
			+ "e.kda004AppliAcrnmNo = :#{#vo.getKda004AppliAcrnmNo()} , "
			+ "e.kda004AppliDesc = :#{#vo.getKda004AppliDesc()} , "
			+ "e.kda004LastUpdateProcess = :#{#vo.getKda004LastUpdateProcess()} , "
			+ "e.kda004LastUpdateUser = :#{#vo.getKda004LastUpdateUser()} , "
			+ "e.kda004LastUpdateDate = :#{#vo.getKda004LastUpdateDate()}   where e.kda004AppliITMSNo = :#{#vo.getKda004AppliITMSNo()}")
	int updateAppliMaintenVo(@Param("vo") T_APPLICATION_004  vo);
	
}

