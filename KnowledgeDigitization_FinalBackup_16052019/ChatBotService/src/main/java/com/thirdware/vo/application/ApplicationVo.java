package com.thirdware.vo.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;



@Component
public class ApplicationVo {
	
	/** DataTable Fields */
	private int rowId;
	private String itmsNo;
	private String applicationName;
	private String applicationAcronym;
	private String applicationDesc;
	private String createDateTime;
	private String lastUpdateDateTime;
	
	
	/** Search Input Fields */
	private List<String> searchItmsId;
	private String searchStrItmsNo;
	private String searchApplicationName;
	private String searchApplicationAcronym;
	private String searchApplicationDesc;
	
	private List<ApplicationVo> datableList = new ArrayList<ApplicationVo>();
	private String errorMsg;
	private String successmsg;
	private List<String> errors;
	
	
	/** Excel Upload Input Fields */
	private String excelItmsNo;
	private String excelAppliName;
	private String excelAppliAcronym;
	private String excelAppliDesc;
	
	
	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	public String getItmsNo() {
		return itmsNo;
	}
	public void setItmsNo(String itmsNo) {
		this.itmsNo = itmsNo;
	}
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	public String getApplicationAcronym() {
		return applicationAcronym;
	}
	public void setApplicationAcronym(String applicationAcronym) {
		this.applicationAcronym = applicationAcronym;
	}
	public String getApplicationDesc() {
		return applicationDesc;
	}
	public void setApplicationDesc(String applicationDesc) {
		this.applicationDesc = applicationDesc;
	}
	public String getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(String createDateTime) {
		this.createDateTime = createDateTime;
	}
	public String getLastUpdateDateTime() {
		return lastUpdateDateTime;
	}
	public void setLastUpdateDateTime(String lastUpdateDateTime) {
		this.lastUpdateDateTime = lastUpdateDateTime;
	}
	public List<String> getSearchItmsId() {
		return searchItmsId;
	}
	public void setSearchItmsId(List<String> searchItmsId) {
		this.searchItmsId = searchItmsId;
	}
	public String getSearchStrItmsNo() {
		return searchStrItmsNo;
	}
	public void setSearchStrItmsNo(String searchStrItmsNo) {
		this.searchStrItmsNo = searchStrItmsNo;
	}
	public String getSearchApplicationName() {
		return searchApplicationName;
	}
	public void setSearchApplicationName(String searchApplicationName) {
		this.searchApplicationName = searchApplicationName;
	}
	public String getSearchApplicationAcronym() {
		return searchApplicationAcronym;
	}
	public void setSearchApplicationAcronym(String searchApplicationAcronym) {
		this.searchApplicationAcronym = searchApplicationAcronym;
	}
	public String getSearchApplicationDesc() {
		return searchApplicationDesc;
	}
	public void setSearchApplicationDesc(String searchApplicationDesc) {
		this.searchApplicationDesc = searchApplicationDesc;
	}
	public List<ApplicationVo> getDatableList() {
		return datableList;
	}
	public void setDatableList(List<ApplicationVo> datableList) {
		this.datableList = datableList;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getSuccessmsg() {
		return successmsg;
	}
	public void setSuccessmsg(String successmsg) {
		this.successmsg = successmsg;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public String getExcelItmsNo() {
		return excelItmsNo;
	}
	public void setExcelItmsNo(String excelItmsNo) {
		this.excelItmsNo = excelItmsNo;
	}
	public String getExcelAppliName() {
		return excelAppliName;
	}
	public void setExcelAppliName(String excelAppliName) {
		this.excelAppliName = excelAppliName;
	}
	public String getExcelAppliAcronym() {
		return excelAppliAcronym;
	}
	public void setExcelAppliAcronym(String excelAppliAcronym) {
		this.excelAppliAcronym = excelAppliAcronym;
	}
	public String getExcelAppliDesc() {
		return excelAppliDesc;
	}
	public void setExcelAppliDesc(String excelAppliDesc) {
		this.excelAppliDesc = excelAppliDesc;
	}
	
	
	
	
	
	
	
	

}
