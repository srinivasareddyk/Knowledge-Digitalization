package com.thirdware.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
@Entity
@Table(name="MKDA008_MDUL_HELP")
public class MKDA008_MDUL_HELP {
	@Id
	@Column(name="KDA008_MDUL_K")
	Long module;
	@Column(name="KDA008_HELP_DOC_F")
	String helpDocFileName;
	@Column(name="KDA008_HELP_DOC_LOC_X")
	String helpDocLoc;
	@Column(name="KDA008_HELP_VIDEO_F")
	String helpVideoFileName;
	@Column(name="KDA008_HELP_VIDEO_LOC_X")
	String helpVideoLoc;
	@Column(name="KDA008_ALT_HELP_DOC_F")
	String altDocFileName;
	@Column(name="KDA008_ALT_HELP_DOC_LOC_X")
	@Lob
	byte[] altDoc;
	@Column(name="KDA008_CRT_USR_C")
	String createdUser;
	@Column(name="KDA008_CRT_S")
	Timestamp createdTime;
	@Column(name="KDA008_CRT_PROC_C")
	String createdProcess;
	@Column(name="KDA008_LST_UPDT_USR_C")
	String updatedUser;
	@Column(name="KDA008_LST_UPDT_S")
	Timestamp updatedTime;
	@Column(name="KDA008_LST_UPDT_PROC_C")
	String updatedProcess;

	/*
	 * @Column(name="KDA008_STAT_F") String status;
	 * 
	 * public String getStatus() { return status; } public void setStatus(String
	 * status) { this.status = status; }
	 */
	public Long getModule() {
		return module;
	}
	public void setModule(Long module) {
		this.module = module;
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
	public String getAltDocFileName() {
		return altDocFileName;
	}
	public void setAltDocFileName(String altDocFileName) {
		this.altDocFileName = altDocFileName;
	}
	public byte[] getAltDoc() {
		return altDoc;
	}
	public void setAltDoc(byte[] altDoc) {
		this.altDoc = altDoc;
	}
	public String getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(String createdUser) {
		this.createdUser = createdUser;
	}
	public Timestamp getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}
	public String getCreatedProcess() {
		return createdProcess;
	}
	public void setCreatedProcess(String createdProcess) {
		this.createdProcess = createdProcess;
	}
	public String getUpdatedUser() {
		return updatedUser;
	}
	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}
	public Timestamp getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}
	public String getUpdatedProcess() {
		return updatedProcess;
	}
	public void setUpdatedProcess(String updatedProcess) {
		this.updatedProcess = updatedProcess;
	}
	
	
}
