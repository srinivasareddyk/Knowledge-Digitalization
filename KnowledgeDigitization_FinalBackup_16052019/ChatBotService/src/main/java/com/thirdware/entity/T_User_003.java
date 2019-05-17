package com.thirdware.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the NESIP001_USR database table.
 * 
 */
@Entity
@Table(name="MKDA003_USR")
@NamedQuery(name="T_User_003.findAll", query="SELECT e FROM T_User_003 e")
public class T_User_003 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="KDA003_WSL_D")
	private String kda003WslD;
	
	
	


	@Column(name="KDA003_ACTV_F")
	private String kda003ActvF;

	@Column(name="KDA003_ADDR_LN_1_X")
	private String kda003AddrLn1X;

	@Column(name="KDA003_ADDR_LN_2_X")
	private String kda003AddrLn2X;

	@Column(name="KDA003_CNTRY_N")
	private String kda003CntryN;

	@Column(name="KDA003_CRT_S")
	private Timestamp kda003CrtS;

	@Column(name="KDA003_CRT_USR_C")
	private String kda003CrtUsrC;

	@Column(name="KDA003_CTY_N")
	private String kda003CtyN;

	@Column(name="KDA003_EMAILID_D")
	private String kda003EmailidD;

	@Column(name="KDA003_FN_N")
	private String kda003FnN;

	@Column(name="KDA003_INACTV_Y")
	private Date kda003InactvY;

	@Column(name="KDA003_LN_N")
	private String kda003LnN;

	@Column(name="KDA003_LST_UPDT_S")
	private Timestamp kda003LstUpdtS;

	@Column(name="KDA003_LST_UPDT_USR_C")
	private String kda003LstUpdtUsrC;

	@Column(name="KDA003_PSTL_C")
	private String kda003PstlC;

	@Column(name="KDA003_ST_N")
	private String kda003StN;

	@Column(name="KDA003_WRK_PH_R")
	private String kda003WrkPhR;
	
	@Column(name="KDA003_STAT_C")
	private String kda003StatC;

	@Column(name="KDA003_PREF_LANG_C")
	private String kda003PreflangC;
	
	@Column(name="KDA003_CMT_X")
	private String kda003CmtX;
	
	@Column(name="KDA003_PSWD_P")
	private String kda003PswdP;
	
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "KDA001_ROLE_SA_K")
    private T_Role_001 role;
	
	/*@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true,fetch=FetchType.LAZY)
    private List<T_UserAreaInfo_022> userAreaInfo;
    
    public List<T_UserAreaInfo_022> getUserAreaInfo() {
		return userAreaInfo;
	}

	public void setUserAreaInfo(List<T_UserAreaInfo_022> userAreaInfo) {
		this.userAreaInfo = userAreaInfo;
	}*/
	
	public T_User_003() {
	}

	public String getKda003WslD() {
		return kda003WslD;
	}

	public void setKda003WslD(String kda003WslD) {
		this.kda003WslD = kda003WslD;
	}

	public String getKda003PswdP() {
		return kda003PswdP;
	}

	public void setKda003PswdP(String kda003PswdP) {
		this.kda003PswdP = kda003PswdP;
	}

	public String getKda003ActvF() {
		return kda003ActvF;
	}

	public void setKda003ActvF(String kda003ActvF) {
		this.kda003ActvF = kda003ActvF;
	}

	public String getKda003AddrLn1X() {
		return kda003AddrLn1X;
	}

	public void setKda003AddrLn1X(String kda003AddrLn1X) {
		this.kda003AddrLn1X = kda003AddrLn1X;
	}

	public String getKda003AddrLn2X() {
		return kda003AddrLn2X;
	}

	public void setKda003AddrLn2X(String kda003AddrLn2X) {
		this.kda003AddrLn2X = kda003AddrLn2X;
	}

	public String getKda003CntryN() {
		return kda003CntryN;
	}

	public void setKda003CntryN(String kda003CntryN) {
		this.kda003CntryN = kda003CntryN;
	}

	public Timestamp getKda003CrtS() {
		return kda003CrtS;
	}

	public void setKda003CrtS(Timestamp kda003CrtS) {
		this.kda003CrtS = kda003CrtS;
	}

	public String getKda003CrtUsrC() {
		return kda003CrtUsrC;
	}

	public void setKda003CrtUsrC(String kda003CrtUsrC) {
		this.kda003CrtUsrC = kda003CrtUsrC;
	}

	public String getKda003CtyN() {
		return kda003CtyN;
	}

	public void setKda003CtyN(String kda003CtyN) {
		this.kda003CtyN = kda003CtyN;
	}

	public String getKda003EmailidD() {
		return kda003EmailidD;
	}

	public void setKda003EmailidD(String kda003EmailidD) {
		this.kda003EmailidD = kda003EmailidD;
	}

	public String getKda003FnN() {
		return kda003FnN;
	}

	public void setKda003FnN(String kda003FnN) {
		this.kda003FnN = kda003FnN;
	}

	public Date getKda003InactvY() {
		return kda003InactvY;
	}

	public void setKda003InactvY(Date kda003InactvY) {
		this.kda003InactvY = kda003InactvY;
	}

	public String getKda003LnN() {
		return kda003LnN;
	}

	public void setKda003LnN(String kda003LnN) {
		this.kda003LnN = kda003LnN;
	}

	public Timestamp getKda003LstUpdtS() {
		return kda003LstUpdtS;
	}

	public void setKda003LstUpdtS(Timestamp kda003LstUpdtS) {
		this.kda003LstUpdtS = kda003LstUpdtS;
	}

	public String getKda003LstUpdtUsrC() {
		return kda003LstUpdtUsrC;
	}

	public void setKda003LstUpdtUsrC(String kda003LstUpdtUsrC) {
		this.kda003LstUpdtUsrC = kda003LstUpdtUsrC;
	}

	public String getKda003PstlC() {
		return kda003PstlC;
	}

	public void setKda003PstlC(String kda003PstlC) {
		this.kda003PstlC = kda003PstlC;
	}

	public String getKda003StN() {
		return kda003StN;
	}

	public void setKda003StN(String kda003StN) {
		this.kda003StN = kda003StN;
	}

	public String getKda003WrkPhR() {
		return kda003WrkPhR;
	}

	public void setKda003WrkPhR(String kda003WrkPhR) {
		this.kda003WrkPhR = kda003WrkPhR;
	}

	public String getKda003StatC() {
		return kda003StatC;
	}

	public void setKda003StatC(String kda003StatC) {
		this.kda003StatC = kda003StatC;
	}

	public String getKda003PreflangC() {
		return kda003PreflangC;
	}

	public void setKda003PreflangC(String kda003PreflangC) {
		this.kda003PreflangC = kda003PreflangC;
	}

	public String getKda003CmtX() {
		return kda003CmtX;
	}

	public void setKda003CmtX(String kda003CmtX) {
		this.kda003CmtX = kda003CmtX;
	}

	public T_Role_001 getRole() {
		return role;
	}

	public void setRole(T_Role_001 role) {
		this.role = role;
	}

		
	
}