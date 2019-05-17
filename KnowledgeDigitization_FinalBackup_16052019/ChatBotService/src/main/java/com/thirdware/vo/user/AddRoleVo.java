package com.thirdware.vo.user;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;


@Component
public class AddRoleVo {
	
	List<AddRoleDataTableVo> addRoleList=new ArrayList<AddRoleDataTableVo>();
	private String errorMsg;
	private String successmsg;
	private List<String> errors;
	public List<AddRoleDataTableVo> getAddRoleList() {
		return addRoleList;
	}
	public void setAddRoleList(List<AddRoleDataTableVo> addRoleList) {
		this.addRoleList = addRoleList;
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
	
}
