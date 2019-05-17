package com.thirdware.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MKDA005_MDUL" )
//@NamedQuery(name = "MKDA005_MDUL.findAllModulesT", query = "SELECT t.moduleId as KDA005_MDUL_K, t.moduleName as KDA005_MDUL_N FROM MKDA005_MDUL t where t.moduleFlag='M'")
public class MKDA005_MDUL {
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="KDA005_MDUL_K")
	private Long moduleId;
	
	@Column(name="KDA005_MDUL_N")
	private String moduleName;
	@Column(name="KDA005_SCRN_DESC")
	private String screenDesc;
	@Column(name="KDA005_PARNT_MDUL_K")
	private String parentModuleKey;
	
	@Column(name="KDA004_APPL_ITMS_R")
	private int itmsNumber;
	
	@Column(name="KDA005_MDUL_N",insertable=false ,updatable=false) 
	private String applicationDetails;
	
	@Column(name="KDA005_MDUL_F") 
	private String moduleFlag;
	
	/*
	 * 
	 * 
	 * @Column(name="KDA005_MDUL_N",insertable=false ,updatable=false) private
	 * String mainMadule;
	 * 
	 * @Column(name="KDA005_MDUL_F",insertable=false ,updatable=false) private
	 * String subMadule;
	 * 
	 * @Column(name="KDA005_SCRN_DESC",insertable=false ,updatable=false) private
	 * String screen;
	 */
	
	
	public String getApplicationDetails() {
		return applicationDetails;
	}
	public void setApplicationDetails(String applicationDetails) {
		this.applicationDetails = applicationDetails;
	}
	public String getModuleFlag() {
		return moduleFlag;
	}
	public void setModuleFlag(String moduleFlag) {
		this.moduleFlag = moduleFlag;
	}
	public int getItmsNumber() {
		return itmsNumber;
	}
	public void setItmsNumber(int itmsNumber) {
		this.itmsNumber = itmsNumber;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getParentModuleKey() {
		return parentModuleKey;
	}
	public void setParentModuleKey(String parentModuleKey) {
		this.parentModuleKey = parentModuleKey;
	}
	
	
	
	@Override
	public String toString() {
		return "KDA005 [moduleId=" + moduleId + ", moduleName=" + moduleName + ", screenDesc=" + screenDesc
				+ ", parentModuleKey=" + parentModuleKey + "]";
	}
	public String getScreenDesc() {
		return screenDesc;
	}
	public void setScreenDesc(String screenDesc) {
		this.screenDesc = screenDesc;
	}
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	
	
	
	
	
	
	
}
