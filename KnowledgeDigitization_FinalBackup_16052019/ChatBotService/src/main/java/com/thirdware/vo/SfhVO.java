package com.thirdware.vo;

import org.springframework.stereotype.Component;

@Component
public class SfhVO {
	private int rowId;
	private String itms;
	private String subModule;
	private String module;
	private String selectedScreen;
	private String helpDocFileName;
	private String helpDocLoc;
	private String helpVideoFileName;
	private String helpVideoLoc;
	private String statusSearch;
	private String errorMsg;
	private String helpDocRequired;
	private String helpVideoRequired;
	private String altDocRequired;

	private byte[] altDocument;

	private boolean isReqHelpDoc;

	private boolean isReqHelpVideo;

	private boolean isReqAltDoc;
	public boolean isReqAltDocForDisplay() {
		return isReqAltDoc;
	}

	private String createdBy;
	private String createdTime;
	private String updatedBy;
	private String updatedTime;

	public SfhVO() {

	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getStatusSearch() {
		return statusSearch;
	}

	public void setStatusSearch(String statusSearch) {
		this.statusSearch = statusSearch;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	public boolean isReqHelpDoc() {
		return isReqHelpDoc;
	}

	public void setIsReqHelpDoc(boolean isReqHelpDoc) {
		this.isReqHelpDoc = isReqHelpDoc;
	}

	public boolean isReqHelpVideo() {
		return isReqHelpVideo;
	}

	public void setIsReqHelpVideo(boolean isReqHelpVideo) {
		this.isReqHelpVideo = isReqHelpVideo;
	}

	public boolean isReqAltDoc() {
		return isReqAltDoc;
	}

	public void setIsReqAltDoc(boolean isReqAltDoc) {
		this.isReqAltDoc = isReqAltDoc;
	}

	public byte[] getAltDocument() {
		return altDocument;
	}

	public void setAltDocument(byte[] altDocument) {
		this.altDocument = altDocument;
	}

	public String getHelpDocRequired() {
		return helpDocRequired;
	}

	public void setHelpDocRequired(String helpDocRequired) {
		this.helpDocRequired = helpDocRequired;
	}

	public String getHelpVideoRequired() {
		return helpVideoRequired;
	}

	public void setHelpVideoRequired(String helpVideoRequired) {
		this.helpVideoRequired = helpVideoRequired;
	}

	public String getAltDocRequired() {
		return altDocRequired;
	}

	public void setAltDocRequired(String altDocRequired) {
		this.altDocRequired = altDocRequired;
	}

	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	public String getItms() {
		return itms;
	}

	public void setItms(String itms) {
		this.itms = itms;
	}

	public String getSubModule() {
		return subModule;
	}

	public void setSubModule(String subModule) {
		this.subModule = subModule;
		//setSubModuleId(Integer.parseInt(this.subModule.split("-")[0]));

	}

	public String getSelectedScreen() {
		return selectedScreen;
	}

	public void setSelectedScreen(String selectedScreen) {
		this.selectedScreen = selectedScreen;
	}

	public String getHelpDocFileName() {
		return helpDocFileName;
	}

	public void setHelpDocFileName(String helpDocFileName) {
		this.helpDocFileName = helpDocFileName;
	}

	public String getHelpDocLoc() {
		return helpDocLoc;
	}

	public void setHelpDocLoc(String helpDocLoc) {
		this.helpDocLoc = helpDocLoc;
	}

	public String getHelpVideoFileName() {
		return helpVideoFileName;
	}

	public void setHelpVideoFileName(String helpVideoFileName) {
		this.helpVideoFileName = helpVideoFileName;
	}

	public String getHelpVideoLoc() {
		return helpVideoLoc;
	}

	public void setHelpVideoLoc(String helpVideoLoc) {
		this.helpVideoLoc = helpVideoLoc;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
		//setModuleId(Integer.parseInt(this.module.split("-")[0]));
	}

}
