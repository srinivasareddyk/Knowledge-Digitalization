package com.thirdware.vo.user;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.thirdware.vo.SelectedItem;
@Component
public class AddRoleDataTableVo {
	private int id ;
	private String roleCode;
	private String roleName;
	private SelectedItem status;
	private Date createdDate;
	private Date lastUpdatedDate;
	private String errorMsg;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public SelectedItem getStatus() {
		return status;
	}
	public void setStatus(SelectedItem status) {
		this.status = status;
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
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}




}
