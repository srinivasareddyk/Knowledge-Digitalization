package com.thirdware.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthStatusvo {
	
	private String userRegStatus;
	private String activeStatus;
	private String firstName;
	private String credentials;
	private String cdsid;
	
	
	
	public String getCdsid() {
		return cdsid;
	}
	public void setCdsid(String cdsid) {
		this.cdsid = cdsid;
	}
	public String getCredentials() {
		return credentials;
	}
	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

}
