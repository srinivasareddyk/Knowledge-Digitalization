package com.thirdware.vo.dbconfig;

import java.util.List;

public class DBconfigSearchVo {
	
	
	
	//inputs for searching from user
	private List<String> inputItmsNo;
	private String inputApplicationName;
	private List<String> inputDatabaseType;
	private List<String> inputDatabaseName;
	public List<String> getInputItmsNo() {
		return inputItmsNo;
	}
	public void setInputItmsNo(List<String> inputItmsNo) {
		this.inputItmsNo = inputItmsNo;
	}
	public String getInputApplicationName() {
		return inputApplicationName;
	}
	public void setInputApplicationName(String inputApplicationName) {
		this.inputApplicationName = inputApplicationName;
	}
	public List<String> getInputDatabaseType() {
		return inputDatabaseType;
	}
	public void setInputDatabaseType(List<String> inputDatabaseType) {
		this.inputDatabaseType = inputDatabaseType;
	}
	public List<String> getInputDatabaseName() {
		return inputDatabaseName;
	}
	public void setInputDatabaseName(List<String> inputDatabaseName) {
		this.inputDatabaseName = inputDatabaseName;
	}
	
		
	}