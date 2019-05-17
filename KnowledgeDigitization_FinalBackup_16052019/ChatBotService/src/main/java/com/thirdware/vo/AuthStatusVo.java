package com.thirdware.vo;

import org.springframework.stereotype.Component;

@Component
public class AuthStatusVo {
	
	private String userRegStatus;
	private String activeStatus;
	public String getUserRegStatus() {
		return userRegStatus;
	}
	public void setUserRegStatus(String userRegStatus) {
		this.userRegStatus = userRegStatus;
	}
	public String getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

}
