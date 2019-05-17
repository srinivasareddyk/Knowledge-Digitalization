package com.thirdware.vo;

import org.springframework.stereotype.Component;

@Component
public class AuthorizationVo {
	
	private String screenName;
	private String functionName;
    private String screenSakey;
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public String getScreenSakey() {
		return screenSakey;
	}
	public void setScreenSakey(String screenSakey) {
		this.screenSakey = screenSakey;
	}
	
}
