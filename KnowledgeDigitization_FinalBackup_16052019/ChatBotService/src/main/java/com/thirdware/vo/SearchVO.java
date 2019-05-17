package com.thirdware.vo;

import java.util.ArrayList;
import java.util.List;

public class SearchVO {
	private SelectedItem appItmsName=new SelectedItem();
	private List<String> itmsList=new ArrayList<String>();
	private List<String> moduleList=new ArrayList<String>();
	private List<String> submoduleList=new ArrayList<String>();
	private  List<String> screenNames=new ArrayList<String>();
	
	
	public List<String> getItmsList() {
		return itmsList;
	}
	public void setItmsList(List<String> itmsList) {
		this.itmsList = itmsList;
	}
	public SelectedItem getAppItmsName() {
		return appItmsName;
	}
	public void setAppItmsName(SelectedItem appItmsName) {
		this.appItmsName = appItmsName;
	}
	public List<String> getModuleList() {
		return moduleList;
	}
	public void setModuleList(List<String> moduleList) {
		this.moduleList = moduleList;
	}
	public List<String> getSubmoduleList() {
		return submoduleList;
	}
	public void setSubmoduleList(List<String> submoduleList) {
		this.submoduleList = submoduleList;
	}
	public List<String> getScreenNames() {
		return screenNames;
	}
	public void setScreenNames(List<String> screenNames) {
		this.screenNames = screenNames;
	}
	
	

}
