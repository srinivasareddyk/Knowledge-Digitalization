package com.thirdware.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.thirdware.entity.T_Screen_002;
import com.thirdware.entity.T_Screen_002;



@Transactional
@Repository
public interface IScreen027DAO extends CrudRepository<T_Screen_002, Long>{
	
	
	@Query("from T_Screen_002 where kda002ScreenN=:screen_n and kda002FunctionN=:func_n")
	public T_Screen_002 findByesip027ScreenN(@Param("screen_n") String screenN,@Param("func_n") String func);
	
	@Query("from T_Screen_002 where kda002ScreenN=:screen_n")
	public List<T_Screen_002> findByesip027ScreenName(@Param("screen_n") String screenN);

}
