package com.thirdware.vo.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserAreaVo {
	
	private UserDataTableVo userObj;
	//private List<String> selectedList=new ArrayList<String>();
	public UserDataTableVo getUserObj() {
		return userObj;
	}
	public void setUserObj(UserDataTableVo userObj) {
		this.userObj = userObj;
	}
	/*public List<String> getSelectedList() {
		return selectedList;
	}
	public void setSelectedList(List<String> selectedList) {
		this.selectedList = selectedList;
	}*/
	

}
