package com.thirdware.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="MKDA007_ROLE_MAP")
@NamedQuery(name="T_RoleMap_007.findAll", query="SELECT t FROM T_RoleMap_007 t")
public class T_RoleMap_007  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="KDA007_ROLE_MAP_SA_K")
	private Long kda007RoleMapSak;
	
	@ManyToOne(cascade= CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name = "KDA002_SCREEN_MSTR_SA_K")
	private T_Screen_002 screenInRoleMap;
	
	@ManyToOne(cascade= CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name = "KDA001_ROLE_SA_K")
	private T_Role_001 roleInRoleMap;
	
	@Column(name="KDA007_ACTV_F")
	private String kda007ActvF;
	
	@Column(name="KDA007_INACTV_Y")
	private Date kda007Inactvy;
	
	@Column(name="KDA007_CRT_S")
	private Timestamp kda007CrtS;

	@Column(name="KDA007_CRT_USR_C")
	private String kda007CrtUsrC;
	
	@Column(name="KDA007_LST_UPDT_S")
	private Timestamp kda007LstUpdtS;

	@Column(name="KDA007_LST_UPDT_USR_C")
	private String kda007LstUpdtUsrC;

	public Long getKda007RoleMapSak() {
		return kda007RoleMapSak;
	}

	public void setKda007RoleMapSak(Long kda007RoleMapSak) {
		this.kda007RoleMapSak = kda007RoleMapSak;
	}

	public T_Screen_002 getScreenInRoleMap() {
		return screenInRoleMap;
	}

	public void setScreenInRoleMap(T_Screen_002 screenInRoleMap) {
		this.screenInRoleMap = screenInRoleMap;
	}

	public T_Role_001 getRoleInRoleMap() {
		return roleInRoleMap;
	}

	public void setRoleInRoleMap(T_Role_001 roleInRoleMap) {
		this.roleInRoleMap = roleInRoleMap;
	}

	public String getKda007ActvF() {
		return kda007ActvF;
	}

	public void setKda007ActvF(String kda007ActvF) {
		this.kda007ActvF = kda007ActvF;
	}

	public Date getKda007Inactvy() {
		return kda007Inactvy;
	}

	public void setKda007Inactvy(Date kda007Inactvy) {
		this.kda007Inactvy = kda007Inactvy;
	}

	public Timestamp getKda007CrtS() {
		return kda007CrtS;
	}

	public void setKda007CrtS(Timestamp kda007CrtS) {
		this.kda007CrtS = kda007CrtS;
	}

	public String getKda007CrtUsrC() {
		return kda007CrtUsrC;
	}

	public void setKda007CrtUsrC(String kda007CrtUsrC) {
		this.kda007CrtUsrC = kda007CrtUsrC;
	}

	public Timestamp getKda007LstUpdtS() {
		return kda007LstUpdtS;
	}

	public void setKda007LstUpdtS(Timestamp kda007LstUpdtS) {
		this.kda007LstUpdtS = kda007LstUpdtS;
	}

	public String getKda007LstUpdtUsrC() {
		return kda007LstUpdtUsrC;
	}

	public void setKda007LstUpdtUsrC(String kda007LstUpdtUsrC) {
		this.kda007LstUpdtUsrC = kda007LstUpdtUsrC;
	}

		
}
