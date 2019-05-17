package com.thirdware.entity;

import java.io.Serializable;
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
@Table(name="MKDA005_MDUL")
@NamedQuery(name="T_Module_005.findAll", query="SELECT t FROM T_Module_005 t")
public class T_Module_005 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="KDA005_MDUL_K")
	private Long kda005MdulKey;
	
	
	@Column(name="KDA005_MDUL_N")
	private String kda005MdulName;
	
	@Column(name = "KDA005_SCRN_DESC")
	private String kda005ScrnDesc;
	
	@Column(name="KDA005_MDUL_F")
	private String kda005ModuleFlag;
	
	@ManyToOne(cascade= CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name = "KDA005_PARNT_MDUL_K")
	private T_Module_005 kda005ParntMdulKey;
	
	
	@Column(name="KDA005_SEQ_R")
	private int kda005SeqNumber;
	
	@Column(name="KDA005_STAT_F")
	private char kda005StatusFlag;
	
	@Column(name="KDA005_CRT_USR_C")
	private String kda005CrtUsrC;
	
	@Column(name="KDA005_CRT_S")
	private Timestamp kda005CrtS;
	
	@Column(name="KDA005_CRT_PROC_C")
	private String kda005CrtProcC;
	
	@Column(name="KDA005_LST_UPDT_USR_C")
	private String kda005LstUpdtUsrC;
	
	@Column(name="KDA005_LST_UPDT_S")
	private Timestamp kda005LstUpdtS;
	
	@Column(name="KDA005_LST_UPDT_PROC_C")
	private String kda005LstUpdtProcC;
	
	@ManyToOne(cascade= CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name = "KDA004_APPL_ITMS_R")
	private T_Appl_Dtl_004 kda004ApplItmsNum;
	
	@Column(name = "KDA005_MDUL_C")
	private String kda005ModuleCode;

	public Long getKda005MdulKey() {
		return kda005MdulKey;
	}

	public void setKda005MdulKey(Long kda005MdulKey) {
		this.kda005MdulKey = kda005MdulKey;
	}

	public String getKda005MdulName() {
		return kda005MdulName;
	}

	public void setKda005MdulName(String kda005MdulName) {
		this.kda005MdulName = kda005MdulName;
	}

	public String getKda005ScrnDesc() {
		return kda005ScrnDesc;
	}

	public void setKda005ScrnDesc(String kda005ScrnDesc) {
		this.kda005ScrnDesc = kda005ScrnDesc;
	}

	public String getKda005ModuleFlag() {
		return kda005ModuleFlag;
	}

	public void setKda005ModuleFlag(String kda005ModuleFlag) {
		this.kda005ModuleFlag = kda005ModuleFlag;
	}

	public T_Module_005 getKda005ParntMdulKey() {
		return kda005ParntMdulKey;
	}

	public void setKda005ParntMdulKey(T_Module_005 kda005ParntMdulKey) {
		this.kda005ParntMdulKey = kda005ParntMdulKey;
	}

	public int getKda005SeqNumber() {
		return kda005SeqNumber;
	}

	public void setKda005SeqNumber(int kda005SeqNumber) {
		this.kda005SeqNumber = kda005SeqNumber;
	}

	public char getKda005StatusFlag() {
		return kda005StatusFlag;
	}

	public void setKda005StatusFlag(char kda005StatusFlag) {
		this.kda005StatusFlag = kda005StatusFlag;
	}

	public String getKda005CrtUsrC() {
		return kda005CrtUsrC;
	}

	public void setKda005CrtUsrC(String kda005CrtUsrC) {
		this.kda005CrtUsrC = kda005CrtUsrC;
	}

	public Timestamp getKda005CrtS() {
		return kda005CrtS;
	}

	public void setKda005CrtS(Timestamp kda005CrtS) {
		this.kda005CrtS = kda005CrtS;
	}

	public String getKda005CrtProcC() {
		return kda005CrtProcC;
	}

	public void setKda005CrtProcC(String kda005CrtProcC) {
		this.kda005CrtProcC = kda005CrtProcC;
	}

	public String getKda005LstUpdtUsrC() {
		return kda005LstUpdtUsrC;
	}

	public void setKda005LstUpdtUsrC(String kda005LstUpdtUsrC) {
		this.kda005LstUpdtUsrC = kda005LstUpdtUsrC;
	}

	public Timestamp getKda005LstUpdtS() {
		return kda005LstUpdtS;
	}

	public void setKda005LstUpdtS(Timestamp kda005LstUpdtS) {
		this.kda005LstUpdtS = kda005LstUpdtS;
	}

	public String getKda005LstUpdtProcC() {
		return kda005LstUpdtProcC;
	}

	public void setKda005LstUpdtProcC(String kda005LstUpdtProcC) {
		this.kda005LstUpdtProcC = kda005LstUpdtProcC;
	}

	public T_Appl_Dtl_004 getKda004ApplItmsNum() {
		return kda004ApplItmsNum;
	}

	public void setKda004ApplItmsNum(T_Appl_Dtl_004 kda004ApplItmsNum) {
		this.kda004ApplItmsNum = kda004ApplItmsNum;
	}

	public String getKda005ModuleCode() {
		return kda005ModuleCode;
	}

	public void setKda005ModuleCode(String kda005ModuleCode) {
		this.kda005ModuleCode = kda005ModuleCode;
	}
	
	
	
}
