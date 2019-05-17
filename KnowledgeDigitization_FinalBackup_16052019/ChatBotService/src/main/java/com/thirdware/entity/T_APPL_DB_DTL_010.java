package com.thirdware.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//@IdClass(T_Appl_Dtl_004.class)
@Entity
@Table(name="MKDA010_APPL_DB_DTL")
public class T_APPL_DB_DTL_010 implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="KDA004_APPL_ITMS_R")
	private Integer itmsNo;

@Column(name="KDA010_DB_N")
private String databasename;

@Column(name="KDA010_DB_TYP_C")
private String databasetype;

@Column(name="KDA010_HST_N")
private String hostname;


@Column(name="KDA010_IP_R")
private String ipaddress;

@Column(name="KDA010_CRT_USR_C")
private String createdUN;

@Column(name="KDA010_PORT_R")
private Integer portno;
@Column(name="KDA010_DRVR_N")
private String drivername;

@Column(name="KDA010_USR_C")
private String username;

@Column(name="KDA010_PSWD_X")
private String password;


@Column(name="KDA010_CRT_S")
private Date createddate;

@Column(name="KDA010_UPDT_S")
private Date lastupdateddate;


@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
@JoinColumn(name="KDA004_APPL_ITMS_R")
private T_Appl_Dtl_004 application;



public String getDatabasetype() {
	return databasetype;
}
public void setDatabasetype(String databasetype) {
	this.databasetype = databasetype;
}
public String getHostname() {
	return hostname;
}
public void setHostname(String hostname) {
	this.hostname = hostname;
}
public Integer getPortno() {
	return portno;
}
public void setPortno(Integer portno) {
	this.portno = portno;
}
public String getDrivername() {
	return drivername;
}
public void setDrivername(String drivername) {
	this.drivername = drivername;
}
public String getDatabasename() {
	return databasename;
}
public void setDatabasename(String databasename) {
	this.databasename = databasename;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public Date getCreateddate() {
	return createddate;
}
public void setCreateddate(Date createddate) {
	this.createddate = createddate;
}
public Date getLastupdateddate() {
	return lastupdateddate;
}
public void setLastupdateddate(Date lastupdateddate) {
	this.lastupdateddate = lastupdateddate;
}
public String getIpaddress() {
	return ipaddress;
}
public void setIpaddress(String ipaddress) {
	this.ipaddress = ipaddress;
}
public String getCreatedUN() {
	return createdUN;
}
public void setCreatedUN(String createdUN) {
	this.createdUN = createdUN;
}
public T_Appl_Dtl_004 getApplication() {
	return application;
}
public void setApplication(T_Appl_Dtl_004 application) {
	this.application = application;
}
public Integer getItmsNo() {
	return itmsNo;
}
public void setItmsNo(Integer itmsNo) {
	this.itmsNo = itmsNo;
}


}