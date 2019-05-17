package com.thirdware.vo.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.thirdware.vo.SelectedItem;

@Component
public class UserManageVo {
	private String cdsidSearch;
	private String fNameSearch;
	private String lNameSearch;
	private SelectedItem roleSearch=new SelectedItem();	
	private SelectedItem statusSearch=new SelectedItem();
	private List<UserDataTableVo> userDataList = new ArrayList<UserDataTableVo>();
	public String getCdsidSearch() {
		return cdsidSearch;
	}
	public void setCdsidSearch(String cdsidSearch) {
		this.cdsidSearch = cdsidSearch;
	}
	public String getfNameSearch() {
		return fNameSearch;
	}
	public void setfNameSearch(String fNameSearch) {
		this.fNameSearch = fNameSearch;
	}
	public String getlNameSearch() {
		return lNameSearch;
	}
	public void setlNameSearch(String lNameSearch) {
		this.lNameSearch = lNameSearch;
	}
	public SelectedItem getRoleSearch() {
		return roleSearch;
	}
	public void setRoleSearch(SelectedItem roleSearch) {
		this.roleSearch = roleSearch;
	}
	public SelectedItem getStatusSearch() {
		return statusSearch;
	}
	public void setStatusSearch(SelectedItem statusSearch) {
		this.statusSearch = statusSearch;
	}
	public List<UserDataTableVo> getUserDataList() {
		return userDataList;
	}
	public void setUserDataList(List<UserDataTableVo> userDataList) {
		this.userDataList = userDataList;
	}
	
	
	
	
	
}
