package com.thirdware.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.thirdware.entity.T_User_003;
import com.thirdware.vo.UserVo;

@Transactional
@Repository
public interface IUser001DAO extends CrudRepository<T_User_003, Long>{
	
	@Query("from T_User_003 where kda003WslD=:WSL_ID")
	public T_User_003 findByesip001WslD(@Param("WSL_ID") String wslId);

	@Query("from T_User_003 where kda003StatC=:status")
	public List<T_User_003> findByesip001StatC(@Param("status") String statusCode);
	
	@Modifying
	@Query("delete from T_User_003 e where e.kda003WslD=:WSL_ID")
	int deleteUserUsingCdsId(@Param("WSL_ID") String wslId);
	
	@Modifying
	@Query("update T_User_003 e set e.kda003FnN = :name where e.kda003WslD = :wsl_id")
	int updateUser(@Param("name") String name, @Param("wsl_id") String wsl_id);
	
	@Modifying
	@Query("update T_User_003 e set e.kda003AddrLn2X = :#{#vo.getKda003AddrLn2X()}, e.kda003CtyN = :#{#vo.getKda003CtyN()},e.kda003StN = :#{#vo.getKda003StN()},e.kda003PstlC = :#{#vo.getKda003PstlC()},e.kda003CntryN = :#{#vo.getKda003CntryN()},e.kda003PreflangC = :#{#vo.getKda003PreflangC()},e.role = :#{#vo.getRole()},e.kda003ActvF = :#{#vo.getKda003ActvF()},e.kda003InactvY = :#{#vo.getKda003InactvY()},e.kda003LstUpdtUsrC = :#{#vo.getKda003LstUpdtUsrC()},e.kda003LstUpdtS = :#{#vo.getKda003LstUpdtS()} where e.kda003WslD = :#{#vo.getKda003WslD()}")
	int updateUserUsingVo(@Param("vo") T_User_003 vo);
	
	@Modifying
	@Query("update T_User_003 e set e.kda003AddrLn2X = :#{#vo.getKda003AddrLn2X()}, e.kda003CtyN = :#{#vo.getKda003CtyN()},e.kda003StN = :#{#vo.getKda003StN()},e.kda003PstlC = :#{#vo.getKda003PstlC()},e.kda003CntryN = :#{#vo.getKda003CntryN()},e.kda003PreflangC = :#{#vo.getKda003PreflangC()},e.role = :#{#vo.getRole()},e.kda003StatC = :#{#vo.getKda003StatC()},e.kda003LstUpdtUsrC = :#{#vo.getKda003LstUpdtUsrC()},e.kda003LstUpdtS = :#{#vo.getKda003LstUpdtS()} where e.kda003WslD = :#{#vo.getKda003WslD()}")
	int approveUserUsingVo(@Param("vo") T_User_003 vo);
	
	@Modifying
	@Query("update T_User_003 e set e.kda003AddrLn2X = :#{#vo.getKda003AddrLn2X()}, e.kda003CtyN = :#{#vo.getKda003CtyN()},e.kda003StN = :#{#vo.getKda003StN()},e.kda003PstlC = :#{#vo.getKda003PstlC()},e.kda003CntryN = :#{#vo.getKda003CntryN()},e.kda003PreflangC = :#{#vo.getKda003PreflangC()},e.role = :#{#vo.getRole()},e.kda003CmtX = :#{#vo.getKda003CmtX()},e.kda003StatC = :#{#vo.getKda003StatC()},e.kda003LstUpdtUsrC = :#{#vo.getKda003LstUpdtUsrC()},e.kda003LstUpdtS = :#{#vo.getKda003LstUpdtS()} where e.kda003WslD = :#{#vo.getKda003WslD()}")
	int denyUserUsingVo(@Param("vo") T_User_003 vo);
	
	@Modifying
	@Query("update T_User_003 e set e.kda003AddrLn2X = :#{#vo.getKda003AddrLn2X()}, e.kda003CtyN = :#{#vo.getKda003CtyN()},e.kda003StN = :#{#vo.getKda003StN()},e.kda003PstlC = :#{#vo.getKda003PstlC()},e.kda003CntryN = :#{#vo.getKda003CntryN()},e.kda003PreflangC = :#{#vo.getKda003PreflangC()},e.role = :#{#vo.getRole()},e.kda003LstUpdtUsrC = :#{#vo.getKda003LstUpdtUsrC()},e.kda003LstUpdtS = :#{#vo.getKda003LstUpdtS()} where e.kda003WslD = :#{#vo.getKda003WslD()}")
	int updateUser(@Param("vo") T_User_003 vo);
	
	
	@Query("select distinct kda003WslD from T_User_003")
    public List<String> findCDSID();
	@Query("from T_User_003 where kda003EmailidD=:email")
	public T_User_003 findByEmail(@Param("email") String email);
	
	
}
