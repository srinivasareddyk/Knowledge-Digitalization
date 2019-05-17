package com.thirdware.entity;

import java.io.Serializable;
import java.sql.Date;
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
@Table(name="MKDA001_ROLE")
@NamedQuery(name="T_Role_001.findAll", query="SELECT e FROM T_Role_001 e")
public class T_Role_001 implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public T_Role_001() {
	
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="KDA001_ROLE_SA_K")
	private String kda001rolesak;

	@OneToMany(mappedBy = "roleInRoleMap",cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    private List<T_RoleMap_007> roleMap;
	
	@Column(name="KDA001_ROLE_C")
	private String kda001rolec;
	
	@Column(name="KDA001_ROLE_N")
	private String kda001rolen;
	
	@Column(name="KDA001_ACTV_F")
	private String kda001ActvF;
	
	@Column(name="KDA001_INACTV_Y")
	private Date kda001Inactvy;
	
	
	@Column(name="KDA001_CRT_S")
	private Timestamp kda001CrtS;

	@Column(name="KDA001_CRT_USR_C")
	private String kda001CrtUsrC;
	
	@Column(name="KDA001_LST_UPDT_S")
	private Timestamp kda001LstUpdtS;

	@Column(name="KDA001_LST_UPDT_USR_C")
	private String kda001LstUpdtUsrC;

	public String getKda001rolesak() {
		return kda001rolesak;
	}

	public void setKda001rolesak(String kda001rolesak) {
		this.kda001rolesak = kda001rolesak;
	}

	public List<T_RoleMap_007> getRoleMap() {
		return roleMap;
	}

	public void setRoleMap(List<T_RoleMap_007> roleMap) {
		this.roleMap = roleMap;
	}

	public String getKda001rolec() {
		return kda001rolec;
	}

	public void setKda001rolec(String kda001rolec) {
		this.kda001rolec = kda001rolec;
	}

	public String getKda001rolen() {
		return kda001rolen;
	}

	public void setKda001rolen(String kda001rolen) {
		this.kda001rolen = kda001rolen;
	}

	public String getKda001ActvF() {
		return kda001ActvF;
	}

	public void setKda001ActvF(String kda001ActvF) {
		this.kda001ActvF = kda001ActvF;
	}

	public Date getKda001Inactvy() {
		return kda001Inactvy;
	}

	public void setKda001Inactvy(Date kda001Inactvy) {
		this.kda001Inactvy = kda001Inactvy;
	}

	public Timestamp getKda001CrtS() {
		return kda001CrtS;
	}

	public void setKda001CrtS(Timestamp kda001CrtS) {
		this.kda001CrtS = kda001CrtS;
	}

	public String getKda001CrtUsrC() {
		return kda001CrtUsrC;
	}

	public void setKda001CrtUsrC(String kda001CrtUsrC) {
		this.kda001CrtUsrC = kda001CrtUsrC;
	}

	public Timestamp getKda001LstUpdtS() {
		return kda001LstUpdtS;
	}

	public void setKda001LstUpdtS(Timestamp kda001LstUpdtS) {
		this.kda001LstUpdtS = kda001LstUpdtS;
	}

	public String getKda001LstUpdtUsrC() {
		return kda001LstUpdtUsrC;
	}

	public void setKda001LstUpdtUsrC(String kda001LstUpdtUsrC) {
		this.kda001LstUpdtUsrC = kda001LstUpdtUsrC;
	}

	
	
}
