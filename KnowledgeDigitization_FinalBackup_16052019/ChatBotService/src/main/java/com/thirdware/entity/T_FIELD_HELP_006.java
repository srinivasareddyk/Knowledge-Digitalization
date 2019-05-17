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


/**
 * The persistent class for the NESIP007_DSID database table.
 * 
 */
@Entity
@Table(name="MKDA006_FLD_HELP")
@NamedQuery(name="T_FIELD_HELP_006.findAll", query="SELECT t FROM T_FIELD_HELP_006 t")
public class T_FIELD_HELP_006 implements Serializable {
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="KDA006_FLD_K")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long kda006FieldId;

	@ManyToOne(cascade= CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name = "KDA005_MDUL_K")
	private T_Module_005 kda005MdulId;
	
	@Column(name="KDA006_FLD_C")
	private String kda006FldCode;
	
	@Column(name="KDA006_FLD_N")
	private String kda006FldName;
	
	@Column(name="KDA006_AUDO_HELP_F")
	private String kda006AudoHelpFlag;
	
	@Column(name="KDA006_TOOL_TIP_F")
	private String kda006ToolTipFlag;
	
	@Column(name="KDA006_HELP_TEXT_X")
	private String kda006HelpTextDesc;
	
	@Column(name="KDA006_CRT_USR_C")
	private String kda006CrtUsrCode;

	@Column(name="KDA006_CRT_S")
	private Timestamp kda006CreateDate;

	@Column(name="KDA006_CRT_PROC_C")
	private String kda006CreateProcess;
	
	@Column(name="KDA006_LST_UPDT_USR_C")
	private String kda006LastUpdateUser;
	
	@Column(name="KDA006_LST_UPDT_S")
	private Timestamp kda006LastUpdateDate;

	@Column(name="KDA006_LST_UPDT_PROC_C")
	private String kda006LastUpdateProcess;

	public T_FIELD_HELP_006() {
	}

	public Long getKda006FieldId() {
		return kda006FieldId;
	}

	public void setKda006FieldId(Long kda006FieldId) {
		this.kda006FieldId = kda006FieldId;
	}

	public T_Module_005 getKda005MdulId() {
		return kda005MdulId;
	}

	public void setKda005MdulId(T_Module_005 kda005MdulId) {
		this.kda005MdulId = kda005MdulId;
	}

	public String getKda006FldCode() {
		return kda006FldCode;
	}

	public void setKda006FldCode(String kda006FldCode) {
		this.kda006FldCode = kda006FldCode;
	}

	public String getKda006FldName() {
		return kda006FldName;
	}

	public void setKda006FldName(String kda006FldName) {
		this.kda006FldName = kda006FldName;
	}

	public String getKda006AudoHelpFlag() {
		return kda006AudoHelpFlag;
	}

	public void setKda006AudoHelpFlag(String kda006AudoHelpFlag) {
		this.kda006AudoHelpFlag = kda006AudoHelpFlag;
	}

	public String getKda006ToolTipFlag() {
		return kda006ToolTipFlag;
	}

	public void setKda006ToolTipFlag(String kda006ToolTipFlag) {
		this.kda006ToolTipFlag = kda006ToolTipFlag;
	}

	public String getKda006HelpTextDesc() {
		return kda006HelpTextDesc;
	}

	public void setKda006HelpTextDesc(String kda006HelpTextDesc) {
		this.kda006HelpTextDesc = kda006HelpTextDesc;
	}

	public String getKda006CrtUsrCode() {
		return kda006CrtUsrCode;
	}

	public void setKda006CrtUsrCode(String kda006CrtUsrCode) {
		this.kda006CrtUsrCode = kda006CrtUsrCode;
	}

	public Timestamp getKda006CreateDate() {
		return kda006CreateDate;
	}

	public void setKda006CreateDate(Timestamp kda006CreateDate) {
		this.kda006CreateDate = kda006CreateDate;
	}

	public String getKda006CreateProcess() {
		return kda006CreateProcess;
	}

	public void setKda006CreateProcess(String kda006CreateProcess) {
		this.kda006CreateProcess = kda006CreateProcess;
	}

	public String getKda006LastUpdateUser() {
		return kda006LastUpdateUser;
	}

	public void setKda006LastUpdateUser(String kda006LastUpdateUser) {
		this.kda006LastUpdateUser = kda006LastUpdateUser;
	}

	public Timestamp getKda006LastUpdateDate() {
		return kda006LastUpdateDate;
	}

	public void setKda006LastUpdateDate(Timestamp kda006LastUpdateDate) {
		this.kda006LastUpdateDate = kda006LastUpdateDate;
	}

	public String getKda006LastUpdateProcess() {
		return kda006LastUpdateProcess;
	}

	public void setKda006LastUpdateProcess(String kda006LastUpdateProcess) {
		this.kda006LastUpdateProcess = kda006LastUpdateProcess;
	}

		

}