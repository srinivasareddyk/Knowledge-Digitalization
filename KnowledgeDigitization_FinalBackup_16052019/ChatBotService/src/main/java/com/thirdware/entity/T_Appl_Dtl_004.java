package com.thirdware.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="MKDA004_APPL_DTL")
@NamedQuery(name="T_Appl_Dtl_004.findAll", query="SELECT t FROM T_Appl_Dtl_004 t")
public class T_Appl_Dtl_004 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="KDA004_APPL_ITMS_R")
	private Integer kda004ApplItmsNum;
	
	@Column(name="KDA004_APPL_N")
	private String kda004ApplName;

	@Column(name="KDA004_APPL_ACRNM_C")
	private String kda004ApplAcrnymCode;

	@Column(name="KDA004_APPL_X")
	private String kda004ApplDesc;
	
	@OneToMany(mappedBy = "kda004ApplItmsNum",cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    private List<T_Category_009> category; 
    
    @OneToMany(mappedBy = "kda004ApplItmsNum",cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    private List<T_Module_005> modules; 
	
	@Column(name="KDA004_CRT_PROC_C")
	private String kda004CrtProcC;

	@Column(name="KDA004_CRT_S")
	private Timestamp kda004CrtS;

	@Column(name="KDA004_CRT_USR_C")
	private String kda004CrtUsrC;
	
	@Column(name="KDA004_LST_UPDT_PROC_C")
	private String kda004LstUpdtProcC;

	@Column(name="KDA004_LST_UPDT_S")
	private Timestamp kda004LstUpdtS;

	@Column(name="KDA004_LST_UPDT_USR_C")
	private String kda004LstUpdtUsrC;

	public Integer getKda004ApplItmsNum() {
		return kda004ApplItmsNum;
	}

	public void setKda004ApplItmsNum(Integer kda004ApplItmsNum) {
		this.kda004ApplItmsNum = kda004ApplItmsNum;
	}

	public String getKda004ApplName() {
		return kda004ApplName;
	}

	public void setKda004ApplName(String kda004ApplName) {
		this.kda004ApplName = kda004ApplName;
	}

	public String getKda004ApplAcrnymCode() {
		return kda004ApplAcrnymCode;
	}

	public void setKda004ApplAcrnymCode(String kda004ApplAcrnymCode) {
		this.kda004ApplAcrnymCode = kda004ApplAcrnymCode;
	}

	public String getKda004ApplDesc() {
		return kda004ApplDesc;
	}

	public void setKda004ApplDesc(String kda004ApplDesc) {
		this.kda004ApplDesc = kda004ApplDesc;
	}

	public List<T_Category_009> getCategory() {
		return category;
	}

	public void setCategory(List<T_Category_009> category) {
		this.category = category;
	}

	public List<T_Module_005> getModules() {
		return modules;
	}

	public void setModules(List<T_Module_005> modules) {
		this.modules = modules;
	}

	public String getKda004CrtProcC() {
		return kda004CrtProcC;
	}

	public void setKda004CrtProcC(String kda004CrtProcC) {
		this.kda004CrtProcC = kda004CrtProcC;
	}

	public Timestamp getKda004CrtS() {
		return kda004CrtS;
	}

	public void setKda004CrtS(Timestamp kda004CrtS) {
		this.kda004CrtS = kda004CrtS;
	}

	public String getKda004CrtUsrC() {
		return kda004CrtUsrC;
	}

	public void setKda004CrtUsrC(String kda004CrtUsrC) {
		this.kda004CrtUsrC = kda004CrtUsrC;
	}

	public String getKda004LstUpdtProcC() {
		return kda004LstUpdtProcC;
	}

	public void setKda004LstUpdtProcC(String kda004LstUpdtProcC) {
		this.kda004LstUpdtProcC = kda004LstUpdtProcC;
	}

	public Timestamp getKda004LstUpdtS() {
		return kda004LstUpdtS;
	}

	public void setKda004LstUpdtS(Timestamp kda004LstUpdtS) {
		this.kda004LstUpdtS = kda004LstUpdtS;
	}

	public String getKda004LstUpdtUsrC() {
		return kda004LstUpdtUsrC;
	}

	public void setKda004LstUpdtUsrC(String kda004LstUpdtUsrC) {
		this.kda004LstUpdtUsrC = kda004LstUpdtUsrC;
	}

		
}