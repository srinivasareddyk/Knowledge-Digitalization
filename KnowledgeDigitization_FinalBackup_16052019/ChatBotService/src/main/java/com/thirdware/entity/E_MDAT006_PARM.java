package com.thirdware.entity;



import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MKDA011_PARM")
public class E_MDAT006_PARM implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="KDA011_PARM_SA_K")
	private Integer parameterSakey;
	
	@Column(name="KDA011_PARM_N")
	private String parameterName;
	
	@Column(name="KDA011_PARM_C")
	private String parameterCode;
	
	@Column(name="KDA011_PARM_X")
	private String parameterDescription;
	
	@Column(name="KDA011_ACTV_F")
	private Character activeFlag;
	
	@Column(name="KDA011_INACTV_Y")
	private Date inactiveYear;
	
	@Column(name="KDA011_EDIT_F")
	private Character editFlag;
	
	@Column(name="KDA011_CRT_USR_C")
	private String createUserCode;
	
	@Column(name="KDA011_CRT_S")
	private Date createDateTime;
	
	@Column(name="KDA011_LST_UPDT_USR_C")
	private String lastUpdatedUserCode;
	
	@Column(name="KDA011_LST_UPDT_S")
	private Date lastUpdatedDate;
	
	@Column(name="KDA011_PARM_TYP_C")
	private Character paramType;

	public Integer getParameterSakey() {
		return parameterSakey;
	}

	public void setParameterSakey(Integer parameterSakey) {
		this.parameterSakey = parameterSakey;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getParameterCode() {
		return parameterCode;
	}

	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}

	public String getParameterDescription() {
		return parameterDescription;
	}

	public void setParameterDescription(String parameterDescription) {
		this.parameterDescription = parameterDescription;
	}

	public Character getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(Character activeFlag) {
		this.activeFlag = activeFlag;
	}

	public Date getInactiveYear() {
		return inactiveYear;
	}

	public void setInactiveYear(Date inactiveYear) {
		this.inactiveYear = inactiveYear;
	}

	public Character getEditFlag() {
		return editFlag;
	}

	public void setEditFlag(Character editFlag) {
		this.editFlag = editFlag;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}

	public Date getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getLastUpdatedUserCode() {
		return lastUpdatedUserCode;
	}

	public void setLastUpdatedUserCode(String lastUpdatedUserCode) {
		this.lastUpdatedUserCode = lastUpdatedUserCode;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Character getParamType() {
		return paramType;
	}

	public void setParamType(Character paramType) {
		this.paramType = paramType;
	}



}