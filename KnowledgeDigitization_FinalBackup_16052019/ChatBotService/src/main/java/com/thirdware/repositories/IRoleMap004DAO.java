package com.thirdware.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.thirdware.entity.T_RoleMap_007;

@Transactional
@Repository
public interface IRoleMap004DAO extends CrudRepository<T_RoleMap_007, Long> {
	
	@Modifying
	@Query("delete from T_RoleMap_007 e where e.kda007RoleMapSak= :saKey")
	int deleteUserAreaUsingSakey(@Param("saKey") Long vo);
	
	@Query("from T_RoleMap_007 where kda007RoleMapSak=:saKey")
	public T_RoleMap_007 findByesip004RoleMapSak(@Param("saKey") Long screenN);
	
	@Modifying
	@Query("update T_RoleMap_007 e set e.screenInRoleMap = :#{#vo.getScreenInRoleMap()},e.kda007LstUpdtUsrC = :#{#vo.getKda007LstUpdtUsrC()},e.kda007LstUpdtS = :#{#vo.getKda007LstUpdtS()} where e.kda007RoleMapSak= :#{#vo.getKda007RoleMapSak()}")
	int updateUsingVo(@Param("vo") T_RoleMap_007 vo);

}
