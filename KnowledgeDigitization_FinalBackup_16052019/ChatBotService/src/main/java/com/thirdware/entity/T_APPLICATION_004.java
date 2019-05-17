package com.thirdware.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the NESIP007_DSID database table.
 * 
 */
@Entity
@Table(name="MKDA004_APPL_DTL")
@NamedQuery(name="T_APPLICATION_004.findAll", query="SELECT t FROM T_APPLICATION_004 t")
public class T_APPLICATION_004 implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="KDA004_APPL_ITMS_R")
	private String kda004AppliITMSNo;

	@Column(name="KDA004_APPL_N")
	private String kda004AppliName;

	@Column(name="KDA004_APPL_ACRNM_C")
	private String kda004AppliAcrnmNo;
	
	@Column(name="KDA004_APPL_X")
	private String kda004AppliDesc;
	
	
	@Column(name="KDA004_CRT_USR_C")
	private String kda004CreateUser;

	@Column(name="KDA004_CRT_S")
	private Timestamp kda004CreateDate;

	@Column(name="KDA004_CRT_PROC_C")
	private String kda004CreateProcess;
	
	@Column(name="KDA004_LST_UPDT_USR_C")
	private String kda004LastUpdateUser;
	
	@Column(name="KDA004_LST_UPDT_S")
	private Timestamp kda004LastUpdateDate;

	@Column(name="KDA004_LST_UPDT_PROC_C")
	private String kda004LastUpdateProcess;

	public T_APPLICATION_004() {
	}

	public String getKda004AppliITMSNo() {
		return kda004AppliITMSNo;
	}

	public void setKda004AppliITMSNo(String kda004AppliITMSNo) {
		this.kda004AppliITMSNo = kda004AppliITMSNo;
	}

	public String getKda004AppliName() {
		return kda004AppliName;
	}

	public void setKda004AppliName(String kda004AppliName) {
		this.kda004AppliName = kda004AppliName;
	}

	public String getKda004AppliAcrnmNo() {
		return kda004AppliAcrnmNo;
	}

	public void setKda004AppliAcrnmNo(String kda004AppliAcrnmNo) {
		this.kda004AppliAcrnmNo = kda004AppliAcrnmNo;
	}

	public String getKda004AppliDesc() {
		return kda004AppliDesc;
	}

	public void setKda004AppliDesc(String kda004AppliDesc) {
		this.kda004AppliDesc = kda004AppliDesc;
	}

	public String getKda004CreateUser() {
		return kda004CreateUser;
	}

	public void setKda004CreateUser(String kda004CreateUser) {
		this.kda004CreateUser = kda004CreateUser;
	}

	public Timestamp getKda004CreateDate() {
		return kda004CreateDate;
	}

	public void setKda004CreateDate(Timestamp kda004CreateDate) {
		this.kda004CreateDate = kda004CreateDate;
	}

	public String getKda004CreateProcess() {
		return kda004CreateProcess;
	}

	public void setKda004CreateProcess(String kda004CreateProcess) {
		this.kda004CreateProcess = kda004CreateProcess;
	}

	public String getKda004LastUpdateUser() {
		return kda004LastUpdateUser;
	}

	public void setKda004LastUpdateUser(String kda004LastUpdateUser) {
		this.kda004LastUpdateUser = kda004LastUpdateUser;
	}

	public Timestamp getKda004LastUpdateDate() {
		return kda004LastUpdateDate;
	}

	public void setKda004LastUpdateDate(Timestamp kda004LastUpdateDate) {
		this.kda004LastUpdateDate = kda004LastUpdateDate;
	}

	public String getKda004LastUpdateProcess() {
		return kda004LastUpdateProcess;
	}

	public void setKda004LastUpdateProcess(String kda004LastUpdateProcess) {
		this.kda004LastUpdateProcess = kda004LastUpdateProcess;
	}

	

}