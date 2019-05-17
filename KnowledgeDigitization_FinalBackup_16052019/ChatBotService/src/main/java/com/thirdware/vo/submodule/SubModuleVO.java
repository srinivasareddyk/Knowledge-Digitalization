package com.thirdware.vo.submodule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.thirdware.vo.SelectedItem;

public class SubModuleVO {
	
	/****Data table Fields****/
	private Long moduleKey;
	private Long submoduleKey;
	private String submodulecode;
	private String submodulename;
	private Date createdDate;
	private Date lastUpdatedDate;
	private int id;
	private SelectedItem appItmsNoName=new SelectedItem();
	private SelectedItem modulecodename=new SelectedItem();
	private List<SelectedItem> modulecodenameList=new ArrayList<SelectedItem>();
	private String remarks;
	private List<String> errors=new ArrayList<String>();

	
	/*****Search input fields******/
	private List<String> itmsList=new ArrayList<String>();
	private List<String> moduleCodeNameList=new ArrayList<String>();
	private List<String> submoduleCodeNameList=new ArrayList<String>();
	
	
	
	
	public List<SelectedItem> getModulecodenameList() {
		return modulecodenameList;
	}
	public void setModulecodenameList(List<SelectedItem> modulecodenameList) {
		this.modulecodenameList = modulecodenameList;
	}
	public Long getModuleKey() {
		return moduleKey;
	}
	public void setModuleKey(Long moduleKey) {
		this.moduleKey = moduleKey;
	}
	public Long getSubmoduleKey() {
		return submoduleKey;
	}
	public void setSubmoduleKey(Long submoduleKey) {
		this.submoduleKey = submoduleKey;
	}
	public SelectedItem getModulecodename() {
		return modulecodename;
	}
	public void setModulecodename(SelectedItem modulecodename) {
		this.modulecodename = modulecodename;
	}
	public String getSubmodulecode() {
		return submodulecode;
	}
	public void setSubmodulecode(String submodulecode) {
		this.submodulecode = submodulecode;
	}
	public String getSubmodulename() {
		return submodulename;
	}
	public void setSubmodulename(String submodulename) {
		this.submodulename = submodulename;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public SelectedItem getAppItmsNoName() {
		return appItmsNoName;
	}
	public void setAppItmsNoName(SelectedItem appItmsNoName) {
		this.appItmsNoName = appItmsNoName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public List<String> getItmsList() {
		return itmsList;
	}
	public void setItmsList(List<String> itmsList) {
		this.itmsList = itmsList;
	}
	public List<String> getModuleCodeNameList() {
		return moduleCodeNameList;
	}
	public void setModuleCodeNameList(List<String> moduleCodeNameList) {
		this.moduleCodeNameList = moduleCodeNameList;
	}
	public List<String> getSubmoduleCodeNameList() {
		return submoduleCodeNameList;
	}
	public void setSubmoduleCodeNameList(List<String> submoduleCodeNameList) {
		this.submoduleCodeNameList = submoduleCodeNameList;
	}
	
	
	
}
