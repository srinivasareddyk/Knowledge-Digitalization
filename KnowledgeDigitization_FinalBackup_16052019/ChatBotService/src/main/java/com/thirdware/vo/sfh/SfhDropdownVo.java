package com.thirdware.vo.sfh;

import java.util.List;

import org.springframework.stereotype.Component;

import com.thirdware.vo.SelectedItem;

@Component

public class SfhDropdownVo {
	
	/*
	 * private List<String> dsId; private String dsidString ; private String
	 * operation;
	 */
	/* private SelectedItem statusSearch =new SelectedItem(); */
	private String errorMsg;
	private String successmsg;
	private List<String> errors;
	private List<String> itms;
	private List<String> module;
	private List<String> subModule;
	private List<String> screen;
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
	
	public List<String> getItms() {
		return itms;
	}
	public void setItms(List<String> itms) {
		this.itms = itms;
	}
	public List<String> getModule() {
		return module;
	}
	public void setModule(List<String> module) {
		this.module = module;
	}
	public List<String> getSubModule() {
		return subModule;
	}
	public void setSubModule(List<String> subModule) {
		this.subModule = subModule;
	}
	public List<String> getScreen() {
		return screen;
	}
	public void setScreen(List<String> screen) {
		this.screen = screen;
	}
	
}
