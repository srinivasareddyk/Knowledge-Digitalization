package com.thirdware.vo.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.thirdware.vo.SelectedItem;

@Component
public class MapRoleDataTableVo {
	private int id ;
	private List<String> selectedFunctionVoList=new ArrayList<String>();
	private List<String> functionVoList=new ArrayList<String>();
	private List<SelectedItem> roleVoList=new ArrayList<SelectedItem>();
	private List<SelectedItem> screenVoList=new ArrayList<SelectedItem>();
	private SelectedItem role=new SelectedItem();
	private SelectedItem screen=new SelectedItem();
	private String roleSakey;
	private List<Long> roleMapSakey=new ArrayList<Long>();
	private Long screenSakey;
	private String errorMsg;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<String> getSelectedFunctionVoList() {
		return selectedFunctionVoList;
	}
	public void setSelectedFunctionVoList(List<String> selectedFunctionVoList) {
		this.selectedFunctionVoList = selectedFunctionVoList;
	}
	public SelectedItem getRole() {
		return role;
	}
	public void setRole(SelectedItem role) {
		this.role = role;
	}
	public SelectedItem getScreen() {
		return screen;
	}
	public void setScreen(SelectedItem screen) {
		this.screen = screen;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getRoleSakey() {
		return roleSakey;
	}
	public void setRoleSakey(String roleSakey) {
		this.roleSakey = roleSakey;
	}
	
	public Long getScreenSakey() {
		return screenSakey;
	}
	public void setScreenSakey(Long long1) {
		this.screenSakey = long1;
	}
	
	public List<String> getFunctionVoList() {
		return functionVoList;
	}
	public void setFunctionVoList(List<String> functionVoList) {
		this.functionVoList = functionVoList;
	}
	public List<SelectedItem> getRoleVoList() {
		return roleVoList;
	}
	public void setRoleVoList(List<SelectedItem> roleVoList) {
		this.roleVoList = roleVoList;
	}
	public List<SelectedItem> getScreenVoList() {
		return screenVoList;
	}
	public void setScreenVoList(List<SelectedItem> screenVoList) {
		this.screenVoList = screenVoList;
	}
	public List<Long> getRoleMapSakey() {
		return roleMapSakey;
	}
	public void setRoleMapSakey(List<Long> roleMapSakey) {
		this.roleMapSakey = roleMapSakey;
	}
	
}
