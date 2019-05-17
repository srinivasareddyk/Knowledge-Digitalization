package com.thirdware.vo.configUsrManual;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.thirdware.vo.SelectedItem;



@Component
public class ConfigUsrManualVo {
	
	/** DataTable Fields */
	private int rowId;
	private int configSakeyId;
	private String appItmsNo;
	private String appItmsNoId;
	private String appItmsNoDesc;
	private String module;
	private String moduleId;
	private String moduleDesc;
	private String subModule;
	private String subModuleId;
	private String subModuleDesc;
	private String screenName;
	private String screenNameId;
	private String screenNameDesc;
	private String fieldCode;
	private String fieldName;
	private String audioHelp;
	private String toolTipHelp;
	private boolean audioHelpCheck;
	private boolean toolTipHelpCheck;
	private String helpText;
	private String createDateTime;
	private String lastUpdateDateTime;
	private List<SelectedItem> applItmsNoList=new ArrayList<SelectedItem>();
	private List<SelectedItem> moduleList=new ArrayList<SelectedItem>();
	private List<SelectedItem> subModuleList=new ArrayList<SelectedItem>();
	private List<SelectedItem> screenNameList=new ArrayList<SelectedItem>();
	private List<String> fieldNameList=new ArrayList<String>();
	
	
	/** Search Input Fields */
	private List<String> searchApplItmsList;
	private List<String> searchModuleList;
	private List<String> searchSubModuleList;
	private List<String> searchScreenNameList;
	private List<String> searchFieldNameList;
	private String searchStrModule;
	private String searchStrSubModule;
	private String searchStrScreenName;
	private String searchStrFieldName;
	
	private List<ConfigUsrManualVo> datableList = new ArrayList<ConfigUsrManualVo>();
	private String errorMsg;
	private String successmsg;
	private List<String> errors;
	
	
	/** Excel Upload Input Fields */
	private String excelAppItmsNo;
	private String excelModuleCode;
	private String excelSubModuleCode;
	private String excelScreenNameCode;
	private String excelModuleMasterSakey;
	private String excelFieldHelpSakey;
	private String excelFieldCode;
	private String excelFieldName;
	private String excelAudioHelp;
	private String excelToolTipHelp;
	private String excelHelpText;
	
	
	/*private List<String> dsidSearchList = new ArrayList<String>();*/
	
	
	public int getRowId() {
		return rowId;
	}
	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getSubModule() {
		return subModule;
	}
	public void setSubModule(String subModule) {
		this.subModule = subModule;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getAudioHelp() {
		return audioHelp;
	}
	public void setAudioHelp(String audioHelp) {
		this.audioHelp = audioHelp;
	}
	public String getToolTipHelp() {
		return toolTipHelp;
	}
	public void setToolTipHelp(String toolTipHelp) {
		this.toolTipHelp = toolTipHelp;
	}
	public String getHelpText() {
		return helpText;
	}
	public void setHelpText(String helpText) {
		this.helpText = helpText;
	}
	public String getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(String createDateTime) {
		this.createDateTime = createDateTime;
	}
	public String getLastUpdateDateTime() {
		return lastUpdateDateTime;
	}
	public void setLastUpdateDateTime(String lastUpdateDateTime) {
		this.lastUpdateDateTime = lastUpdateDateTime;
	}
	public List<String> getSearchModuleList() {
		return searchModuleList;
	}
	public void setSearchModuleList(List<String> searchModuleList) {
		this.searchModuleList = searchModuleList;
	}
	public List<String> getSearchSubModuleList() {
		return searchSubModuleList;
	}
	public void setSearchSubModuleList(List<String> searchSubModuleList) {
		this.searchSubModuleList = searchSubModuleList;
	}
	public List<String> getSearchScreenNameList() {
		return searchScreenNameList;
	}
	public void setSearchScreenNameList(List<String> searchScreenNameList) {
		this.searchScreenNameList = searchScreenNameList;
	}
	public List<String> getSearchFieldNameList() {
		return searchFieldNameList;
	}
	public void setSearchFieldNameList(List<String> searchFieldNameList) {
		this.searchFieldNameList = searchFieldNameList;
	}
	public List<ConfigUsrManualVo> getDatableList() {
		return datableList;
	}
	public void setDatableList(List<ConfigUsrManualVo> datableList) {
		this.datableList = datableList;
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
	public String getExcelFieldName() {
		return excelFieldName;
	}
	public void setExcelFieldName(String excelFieldName) {
		this.excelFieldName = excelFieldName;
	}
	public String getExcelAudioHelp() {
		return excelAudioHelp;
	}
	public void setExcelAudioHelp(String excelAudioHelp) {
		this.excelAudioHelp = excelAudioHelp;
	}
	public String getExcelToolTipHelp() {
		return excelToolTipHelp;
	}
	public void setExcelToolTipHelp(String excelToolTipHelp) {
		this.excelToolTipHelp = excelToolTipHelp;
	}
	public String getExcelHelpText() {
		return excelHelpText;
	}
	public void setExcelHelpText(String excelHelpText) {
		this.excelHelpText = excelHelpText;
	}
	public String getSearchStrModule() {
		return searchStrModule;
	}
	public void setSearchStrModule(String searchStrModule) {
		this.searchStrModule = searchStrModule;
	}
	public String getSearchStrSubModule() {
		return searchStrSubModule;
	}
	public void setSearchStrSubModule(String searchStrSubModule) {
		this.searchStrSubModule = searchStrSubModule;
	}
	public String getSearchStrScreenName() {
		return searchStrScreenName;
	}
	public void setSearchStrScreenName(String searchStrScreenName) {
		this.searchStrScreenName = searchStrScreenName;
	}
	public String getSearchStrFieldName() {
		return searchStrFieldName;
	}
	public void setSearchStrFieldName(String searchStrFieldName) {
		this.searchStrFieldName = searchStrFieldName;
	}
	public List<SelectedItem> getModuleList() {
		return moduleList;
	}
	public void setModuleList(List<SelectedItem> moduleList) {
		this.moduleList = moduleList;
	}
	public List<SelectedItem> getSubModuleList() {
		return subModuleList;
	}
	public void setSubModuleList(List<SelectedItem> subModuleList) {
		this.subModuleList = subModuleList;
	}
	public List<SelectedItem> getScreenNameList() {
		return screenNameList;
	}
	public void setScreenNameList(List<SelectedItem> screenNameList) {
		this.screenNameList = screenNameList;
	}
	public List<String> getFieldNameList() {
		return fieldNameList;
	}
	public void setFieldNameList(List<String> fieldNameList) {
		this.fieldNameList = fieldNameList;
	}
	public boolean isAudioHelpCheck() {
		return audioHelpCheck;
	}
	public void setAudioHelpCheck(boolean audioHelpCheck) {
		this.audioHelpCheck = audioHelpCheck;
	}
	public boolean isToolTipHelpCheck() {
		return toolTipHelpCheck;
	}
	public void setToolTipHelpCheck(boolean toolTipHelpCheck) {
		this.toolTipHelpCheck = toolTipHelpCheck;
	}
	public int getConfigSakeyId() {
		return configSakeyId;
	}
	public void setConfigSakeyId(int configSakeyId) {
		this.configSakeyId = configSakeyId;
	}
	public String getModuleDesc() {
		return moduleDesc;
	}
	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}
	public String getSubModuleDesc() {
		return subModuleDesc;
	}
	public void setSubModuleDesc(String subModuleDesc) {
		this.subModuleDesc = subModuleDesc;
	}
	public String getScreenNameDesc() {
		return screenNameDesc;
	}
	public void setScreenNameDesc(String screenNameDesc) {
		this.screenNameDesc = screenNameDesc;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getSubModuleId() {
		return subModuleId;
	}
	public void setSubModuleId(String subModuleId) {
		this.subModuleId = subModuleId;
	}
	public String getScreenNameId() {
		return screenNameId;
	}
	public void setScreenNameId(String screenNameId) {
		this.screenNameId = screenNameId;
	}
	public String getAppItmsNo() {
		return appItmsNo;
	}
	public void setAppItmsNo(String appItmsNo) {
		this.appItmsNo = appItmsNo;
	}
	public String getAppItmsNoId() {
		return appItmsNoId;
	}
	public void setAppItmsNoId(String appItmsNoId) {
		this.appItmsNoId = appItmsNoId;
	}
	public String getAppItmsNoDesc() {
		return appItmsNoDesc;
	}
	public void setAppItmsNoDesc(String appItmsNoDesc) {
		this.appItmsNoDesc = appItmsNoDesc;
	}
	public List<SelectedItem> getApplItmsNoList() {
		return applItmsNoList;
	}
	public void setApplItmsNoList(List<SelectedItem> applItmsNoList) {
		this.applItmsNoList = applItmsNoList;
	}
	public List<String> getSearchApplItmsList() {
		return searchApplItmsList;
	}
	public void setSearchApplItmsList(List<String> searchApplItmsList) {
		this.searchApplItmsList = searchApplItmsList;
	}
	public String getFieldCode() {
		return fieldCode;
	}
	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}
	public String getExcelAppItmsNo() {
		return excelAppItmsNo;
	}
	public void setExcelAppItmsNo(String excelAppItmsNo) {
		this.excelAppItmsNo = excelAppItmsNo;
	}
	public String getExcelFieldCode() {
		return excelFieldCode;
	}
	public void setExcelFieldCode(String excelFieldCode) {
		this.excelFieldCode = excelFieldCode;
	}
	public String getExcelModuleCode() {
		return excelModuleCode;
	}
	public void setExcelModuleCode(String excelModuleCode) {
		this.excelModuleCode = excelModuleCode;
	}
	public String getExcelSubModuleCode() {
		return excelSubModuleCode;
	}
	public void setExcelSubModuleCode(String excelSubModuleCode) {
		this.excelSubModuleCode = excelSubModuleCode;
	}
	public String getExcelScreenNameCode() {
		return excelScreenNameCode;
	}
	public void setExcelScreenNameCode(String excelScreenNameCode) {
		this.excelScreenNameCode = excelScreenNameCode;
	}
	public String getExcelFieldHelpSakey() {
		return excelFieldHelpSakey;
	}
	public void setExcelFieldHelpSakey(String excelFieldHelpSakey) {
		this.excelFieldHelpSakey = excelFieldHelpSakey;
	}
	public String getExcelModuleMasterSakey() {
		return excelModuleMasterSakey;
	}
	public void setExcelModuleMasterSakey(String excelModuleMasterSakey) {
		this.excelModuleMasterSakey = excelModuleMasterSakey;
	}
	
}
