package com.thirdware.vo.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.thirdware.vo.SelectedItem;

public class ModuleVO {
	/****Data table Fields****/
	private Long moduleKey;
	private String module;
	private String modulecode;
	private Date createdDate;
	private Date lastUpdatedDate;
	private int id;
	private SelectedItem appItmsNoName=new SelectedItem();
	private String remarks;
	private List<String> errors=new ArrayList<String>();
	
	/*****Search input fields******/
	private List<String> itmsList=new ArrayList<String>();
	private List<String> moduleList=new ArrayList<String>();
	private List<String> moduleCodeList=new ArrayList<String>();
	private List<String> moduleCodeNameList=new ArrayList<String>();
	
	/****Excel fields*******/
	private Integer itmsno;
	
	
	
	
	
	public List<String> getModuleCodeNameList() {
		return moduleCodeNameList;
	}
	public void setModuleCodeNameList(List<String> moduleCodeNameList) {
		this.moduleCodeNameList = moduleCodeNameList;
	}
	public Integer getItmsno() {
		return itmsno;
	}
	public void setItmsno(Integer itmsno) {
		this.itmsno = itmsno;
	}
	public List<String> getItmsList() {
		return itmsList;
	}
	public void setItmsList(List<String> itmsList) {
		this.itmsList = itmsList;
	}
	public List<String> getModuleList() {
		return moduleList;
	}
	public void setModuleList(List<String> moduleList) {
		this.moduleList = moduleList;
	}
	public List<String> getModuleCodeList() {
		return moduleCodeList;
	}
	public void setModuleCodeList(List<String> moduleCodeList) {
		this.moduleCodeList = moduleCodeList;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public String getModulecode() {
		return modulecode;
	}
	public void setModulecode(String modulecode) {
		this.modulecode = modulecode;
	}
	public Long getModuleKey() {
		return moduleKey;
	}
	public void setModuleKey(Long moduleKey) {
		this.moduleKey = moduleKey;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
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
	
	

}
