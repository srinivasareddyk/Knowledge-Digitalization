package com.thirdware.vo.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.thirdware.vo.SelectedItem;

@Component
public class MapRoleVo {
	
	private SelectedItem screenSearch=new SelectedItem();
	private String functionString;
	private String roleNameString;
	private List<MapRoleDataTableVo> maproleList = new ArrayList<MapRoleDataTableVo>();
	private String errorMsg;
	private String successmsg;
	private List<String> errors;
	private List<String> functionNames;
	private List<String> roles;
	public SelectedItem getScreenSearch() {
		return screenSearch;
	}
	public void setScreenSearch(SelectedItem screenSearch) {
		this.screenSearch = screenSearch;
	}
	public List<MapRoleDataTableVo> getMaproleList() {
		return maproleList;
	}
	public void setMaproleList(List<MapRoleDataTableVo> maproleList) {
		this.maproleList = maproleList;
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
	public List<String> getFunctionNames() {
		return functionNames;
	}
	public void setFunctionNames(List<String> functionNames) {
		this.functionNames = functionNames;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	public String getFunctionString() {
		return functionString;
	}
	public void setFunctionString(String functionString) {
		this.functionString = functionString;
	}
	public String getRoleNameString() {
		return roleNameString;
	}
	public void setRoleNameString(String roleNameString) {
		this.roleNameString = roleNameString;
	}
}
