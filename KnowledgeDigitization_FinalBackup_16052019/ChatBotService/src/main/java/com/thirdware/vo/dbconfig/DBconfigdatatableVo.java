package com.thirdware.vo.dbconfig;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.thirdware.vo.SelectedItem;

public class DBconfigdatatableVo {
	

private Integer id;
private String itmsNo;
private String applicationName;
private String applicationAcronym;
private String databaseType;
private String hostName;
private String portNumber;
private String driverName;
private String databaseName;
private String userName;
private String password;
private String ipAddress;
private Date createdDateAndTime;
private Date lastUpdatedDateAndTime;
private String errorMsg;

//initiall search to display values in dropdowns

private List<String> listitmsNo;
private List<SelectedItem> listapplicationName;
private List<String> listapplicationAcronym;
private Set<String> listdatabaseType;
private List<String> listdatabaseName;
private boolean selected;
private String hashKey;


public List<String> getListitmsNo() {
	return listitmsNo;
}
public void setListitmsNo(List<String> listitmsNo) {
	this.listitmsNo = listitmsNo;
}
public List<SelectedItem> getListapplicationName() {
	return (List<SelectedItem>) listapplicationName;
}
public void setListapplicationName(List<SelectedItem> appName) {
	this.listapplicationName = appName;
}
public List<String> getListapplicationAcronym() {
	return listapplicationAcronym;
}
public void setListapplicationAcronym(List<String> listapplicationAcronym) {
	this.listapplicationAcronym = listapplicationAcronym;
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
public void setApplicationName(String string) {
	this.applicationName = string;
}
public String getApplicationAcronym() {
	return applicationAcronym;
}
public void setApplicationAcronym(String applicationAcronym) {
	this.applicationAcronym = applicationAcronym;
}
public String getDatabaseType() {
	return databaseType;
}
public void setDatabaseType(String databaseType) {
	this.databaseType = databaseType;
}
public String getHostName() {
	return hostName;
}
public void setHostName(String hostName) {
	this.hostName = hostName;
}
public String getPortNumber() {
	return portNumber;
}
public void setPortNumber(String portNumber) {
	this.portNumber = portNumber;
}
public String getDriverName() {
	return driverName;
}
public void setDriverName(String driverName) {
	this.driverName = driverName;
}
public String getDatabaseName() {
	return databaseName;
}
public void setDatabaseName(String databaseName) {
	this.databaseName = databaseName;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public Date getCreatedDateAndTime() {
	return createdDateAndTime;
}
public void setCreatedDateAndTime(Date createdDateAndTime) {
	this.createdDateAndTime = createdDateAndTime;
}
public Date getLastUpdatedDateAndTime() {
	return lastUpdatedDateAndTime;
}
public void setLastUpdatedDateAndTime(Date lastUpdatedDateAndTime) {
	this.lastUpdatedDateAndTime = lastUpdatedDateAndTime;
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getIpAddress() {
	return ipAddress;
}
public void setIpAddress(String ipAddress) {
	this.ipAddress = ipAddress;
}
public Set<String> getListdatabaseType() {
	return listdatabaseType;
}
public void setListdatabaseType(Set<String> databasetype2) {
	this.listdatabaseType = databasetype2;
}
public List<String> getListdatabaseName() {
	return listdatabaseName;
}
public void setListdatabaseName(List<String> listdatabaseName) {
	this.listdatabaseName = listdatabaseName;
}

public String getErrorMsg() {
	return errorMsg;
}
public void setErrorMsg(String errorMsg) {
	this.errorMsg = errorMsg;
}
public String getHashKey() {
	return hashKey;
}
public void setHashKey(String hashKey) {
	this.hashKey = hashKey;
}
public void setSelected(boolean selected) {
	this.selected = selected;
}
}