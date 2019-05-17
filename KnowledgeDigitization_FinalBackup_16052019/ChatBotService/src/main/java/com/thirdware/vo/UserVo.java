package com.thirdware.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class UserVo {
	
	private String wslId;
	private String fName;
	private String lName;
	private String email;
	private String wPhone;
	private String addr1;
	private String adrr2;
	private String city;
	private String state;
	private String pCode;
	private SelectedItem country=new SelectedItem();
	private SelectedItem lang=new SelectedItem();
	private boolean srchErrFlag;
	private String wslIdSrch;
	private SelectedItem status=new SelectedItem();
	private List<String> errors;
	
	
	
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public String getWslIdSrch() {
		return wslIdSrch;
	}
	public void setWslIdSrch(String wslIdSrch) {
		this.wslIdSrch = wslIdSrch;
	}
	public boolean isSrchErrFlag() {
		return srchErrFlag;
	}
	public void setSrchErrFlag(boolean srchErrFlag) {
		this.srchErrFlag = srchErrFlag;
	}
	public String getWslId() {
		return wslId;
	}
	public void setWslId(String wslId) {
		this.wslId = wslId;
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
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
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
	public void setLang(SelectedItem status) {
		this.lang = lang;
	}
	public SelectedItem getStatus() {
		return status;
	}
	public void setStatus(SelectedItem status) {
		this.status = status;
	}
	
	
	

}


