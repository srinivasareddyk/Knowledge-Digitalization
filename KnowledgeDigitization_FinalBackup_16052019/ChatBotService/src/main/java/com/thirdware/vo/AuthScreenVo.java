package com.thirdware.vo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class AuthScreenVo {
	
	private String screen;
	private String function;
	private List<AuthorizationVo> authList=new ArrayList<AuthorizationVo>();
	public String getScreen() {
		return screen;
	}
	public void setScreen(String screen) {
		this.screen = screen;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public List<AuthorizationVo> getAuthList() {
		return authList;
	}
	public void setAuthList(List<AuthorizationVo> authList) {
		this.authList = authList;
	}
	
	

}
