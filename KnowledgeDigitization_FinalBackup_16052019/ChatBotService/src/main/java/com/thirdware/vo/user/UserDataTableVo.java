package com.thirdware.vo.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.thirdware.vo.SelectedItem;

@Component
public class UserDataTableVo {
	private int  id;	
	private String cdsid;
	private String fName;
	private String lName;
	private String email;
	private String wPhone;
	private String password;
	
	//private String role;
	private boolean disableFlag;
	private String statusFlag;
	private String adrr2;
	private String city;
	private String state;
	private String pCode;	
	private Date submitDate;	
	private SelectedItem country=new SelectedItem();
	private SelectedItem lang=new SelectedItem();
	private SelectedItem role=new SelectedItem();
	private String approveorDeny;
	private String comments;
	private List<String> areaSelectedList=new ArrayList<String>();
	private List<String> areaList=new ArrayList<String>();
	private SelectedItem status=new SelectedItem();
	private String addr1;
	public String getCdsid() {
		return cdsid;
	}
	public void setCdsid(String cdsid) {
		this.cdsid = cdsid;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getwPhone() {
		return wPhone;
	}
	public void setwPhone(String wPhone) {
		this.wPhone = wPhone;
	}
	

	public String getAdrr2() {
		return adrr2;
	}
	public void setAdrr2(String adrr2) {
		this.adrr2 = adrr2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getpCode() {
		return pCode;
	}
	public void setpCode(String pCode) {
		this.pCode = pCode;
	}
	public SelectedItem getCountry() {
		return country;
	}
	public void setCountry(SelectedItem country) {
		this.country = country;
	}
	public SelectedItem getLang() {
		return lang;
	}
	public void setLang(SelectedItem lang) {
		this.lang = lang;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	
	public SelectedItem getRole() {
		return role;
	}
	public void setRole(SelectedItem role) {
		this.role = role;
	}
	public SelectedItem getStatus() {
		return status;
	}
	public void setStatus(SelectedItem status) {
		this.status = status;
	}
	public boolean isDisableFlag() {
		return disableFlag;
	}
	public void setDisableFlag(boolean disableFlag) {
		this.disableFlag = disableFlag;
	}
	public String getStatusFlag() {
		return statusFlag;
	}
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}
	public String getApproveorDeny() {
		return approveorDeny;
	}
	public void setApproveorDeny(String approveorDeny) {
		this.approveorDeny = approveorDeny;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public List<String> getAreaSelectedList() {
		return areaSelectedList;
	}
	public void setAreaSelectedList(List<String> areaSelectedList) {
		this.areaSelectedList = areaSelectedList;
	}
	public List<String> getAreaList() {
		return areaList;
	}
	public void setAreaList(List<String> areaList) {
		this.areaList = areaList;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
