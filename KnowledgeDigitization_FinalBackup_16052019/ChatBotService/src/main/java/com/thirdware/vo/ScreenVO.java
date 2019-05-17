package com.thirdware.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScreenVO {
	
	private Long moduleKey;
	private Long submoduleKey;
	private Long screenKey;
	private String screenCode;
	private String screenName;
	private String screenPurpose;
	private Date createdDate;
	private Date lastUpdatedDate;
	private int id;
	private SelectedItem appItmsNoName=new SelectedItem();
	private SelectedItem moduleCodeName=new SelectedItem();
	private SelectedItem submoduleCodeName=new SelectedItem();
	private List<SelectedItem> modulecodenameList=new ArrayList<SelectedItem>();
	private List<SelectedItem> submodulecodenameList=new ArrayList<SelectedItem>();
	private List<String> errors=new ArrayList<String>();
	private boolean isSubModuleExists;
	private String remarks;
	
	
	public String getScreenCode() {
		return screenCode;
	}
	public void setScreenCode(String screenCode) {
		this.screenCode = screenCode;
	}
	public SelectedItem getModuleCodeName() {
		return moduleCodeName;
	}
	public void setModuleCodeName(SelectedItem moduleCodeName) {
		this.moduleCodeName = moduleCodeName;
	}
	public SelectedItem getSubmoduleCodeName() {
		return submoduleCodeName;
	}
	public void setSubmoduleCodeName(SelectedItem submoduleCodeName) {
		this.submoduleCodeName = submoduleCodeName;
	}
	public List<SelectedItem> getModulecodenameList() {
		return modulecodenameList;
	}
	public void setModulecodenameList(List<SelectedItem> modulecodenameList) {
		this.modulecodenameList = modulecodenameList;
	}
	public List<SelectedItem> getSubmodulecodenameList() {
		return submodulecodenameList;
	}
	public void setSubmodulecodenameList(List<SelectedItem> submodulecodenameList) {
		this.submodulecodenameList = submodulecodenameList;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Long getSubmoduleKey() {
		return submoduleKey;
	}
	public void setSubmoduleKey(Long submoduleKey) {
		this.submoduleKey = submoduleKey;
	}
	public Long getScreenKey() {
		return screenKey;
	}
	public void setScreenKey(Long screenKey) {
		this.screenKey = screenKey;
	}
	public Long getModuleKey() {
		return moduleKey;
	}
	public void setModuleKey(Long moduleKey) {
		this.moduleKey = moduleKey;
	}
	public boolean isSubModuleExists() {
		return isSubModuleExists;
	}
	public void setSubModuleExists(boolean isSubModuleExists) {
		this.isSubModuleExists = isSubModuleExists;
	}
	public SelectedItem getAppItmsNoName() {
		return appItmsNoName;
	}
	public void setAppItmsNoName(SelectedItem appItmsNoName) {
		this.appItmsNoName = appItmsNoName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getScreenPurpose() {
		return screenPurpose;
	}
	public void setScreenPurpose(String screenPurpose) {
		this.screenPurpose = screenPurpose;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	

}
