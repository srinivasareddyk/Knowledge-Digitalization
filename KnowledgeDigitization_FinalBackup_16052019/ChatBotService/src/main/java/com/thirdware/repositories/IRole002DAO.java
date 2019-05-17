package com.thirdware.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.thirdware.entity.T_Role_001;


@Transactional
@Repository
public interface IRole002DAO extends CrudRepository<T_Role_001, Long>{
	
	@Query("from T_Role_001 where kda001rolesak=:role_sakey")
	public T_Role_001 findByesip002rolesak(@Param("role_sakey") String sakey);
	
	@Query("from T_Role_001 where kda001rolec=:ROLE_C")
	public T_Role_001 findByesip002rolec(@Param("ROLE_C") String roleC);
	
	@Query("from T_Role_001 where kda001rolen=:ROLE_N")
	public T_Role_001 findByesip002rolen(@Param("ROLE_N") String roleN);
	
	
	@Query("from T_Role_001 where kda001ActvF=:flag")
	public List<T_Role_001> findByesip002ActvF(@Param("flag") String activeFlag);
	
	@Query("from T_Role_001 order by kda001LstUpdtS desc")
	public List<T_Role_001> findByesip002LstUpdtS();
	
	/*@Query("from T_Role_001 where esip002ActvF=:Y")
	public List<T_Role_001> findByesip002ActvF();*/

	@Modifying
	@Query("update T_Role_001 e set  e.kda001rolen = :#{#vo.getKda001rolen()} ,  e.kda001ActvF = :#{#vo.getKda001ActvF()} ,e.kda001Inactvy = :#{#vo.getKda001Inactvy()} ,e.kda001LstUpdtUsrC = :#{#vo.getKda001LstUpdtUsrC()} ,e.kda001LstUpdtS = :#{#vo.getKda001LstUpdtS()}   where e.kda001rolec = :#{#vo.getKda001rolec()}")
	int updateAddRoleVo(@Param("vo") T_Role_001  vo); 
	
}
