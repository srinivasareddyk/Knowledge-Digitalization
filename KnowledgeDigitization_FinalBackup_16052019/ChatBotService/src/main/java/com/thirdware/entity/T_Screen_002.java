package com.thirdware.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="MKDA002_SCREEN_MSTR")
@NamedQuery(name="T_Screen_002.findAll", query="SELECT t FROM T_Screen_002 t")
public class T_Screen_002 implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	public T_Screen_002() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="KDA002_SCREEN_MSTR_SA_K")
	private Long kda002ScreenMstrSak;
	
	@Column(name="KDA002_FUNCTION_D")
	private String kda002FunctionD;
	
	@Column(name="KDA002_SCREEN_N")
	private String kda002ScreenN;
	
	@Column(name="KDA002_FUNCTION_N")
	private String kda002FunctionN;
	
	@OneToMany(mappedBy = "screenInRoleMap",cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    private List<T_RoleMap_007> roleMap;
	
	@Column(name="KDA002_CREATE_PROC_C")
	private String kda002CrtProcC;

	@Column(name="KDA002_CREATE_S")
	private Timestamp kda002CrtS;

	@Column(name="KDA002_CREATE_USER_C")
	private String kda002CrtUsrC;

	public Long getKda002ScreenMstrSak() {
		return kda002ScreenMstrSak;
	}

	public void setKda002ScreenMstrSak(Long kda002ScreenMstrSak) {
		this.kda002ScreenMstrSak = kda002ScreenMstrSak;
	}

	public String getKda002FunctionD() {
		return kda002FunctionD;
	}

	public void setKda002FunctionD(String kda002FunctionD) {
		this.kda002FunctionD = kda002FunctionD;
	}

	public String getKda002ScreenN() {
		return kda002ScreenN;
	}

	public void setKda002ScreenN(String kda002ScreenN) {
		this.kda002ScreenN = kda002ScreenN;
	}

	public String getKda002FunctionN() {
		return kda002FunctionN;
	}

	public void setKda002FunctionN(String kda002FunctionN) {
		this.kda002FunctionN = kda002FunctionN;
	}

	public List<T_RoleMap_007> getRoleMap() {
		return roleMap;
	}

	public void setRoleMap(List<T_RoleMap_007> roleMap) {
		this.roleMap = roleMap;
	}

	public String getKda002CrtProcC() {
		return kda002CrtProcC;
	}

	public void setKda002CrtProcC(String kda002CrtProcC) {
		this.kda002CrtProcC = kda002CrtProcC;
	}

	public Timestamp getKda002CrtS() {
		return kda002CrtS;
	}

	public void setKda002CrtS(Timestamp kda002CrtS) {
		this.kda002CrtS = kda002CrtS;
	}

	public String getKda002CrtUsrC() {
		return kda002CrtUsrC;
	}

	public void setKda002CrtUsrC(String kda002CrtUsrC) {
		this.kda002CrtUsrC = kda002CrtUsrC;
	}

		
}
