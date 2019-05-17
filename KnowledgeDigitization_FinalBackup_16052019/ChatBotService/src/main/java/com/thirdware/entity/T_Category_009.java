package com.thirdware.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="MKDA009_CATG")
@NamedQuery(name="T_Category_009.findAll", query="SELECT t FROM T_Category_009 t")
public class T_Category_009 implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@Column(name="KDA009_APPL_CATG_K")
	private int kda009ApplCatgKey;
	
	
	@ManyToOne(cascade= CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name = "KDA004_APPL_ITMS_R")
	private T_Appl_Dtl_004 kda004ApplItmsNum;

	@Column(name="KDA009_CATG_N")
	private String kda009CatgName;
	
	
	
	@Column(name="KDA009_CRT_S")
	private Timestamp kda009CrtS;

	@Column(name="KDA009_CRT_USR_C")
	private String kda009CrtUsrC;

	public int getKda009ApplCatgKey() {
		return kda009ApplCatgKey;
	}

	public void setKda009ApplCatgKey(int kda009ApplCatgKey) {
		this.kda009ApplCatgKey = kda009ApplCatgKey;
	}

	public T_Appl_Dtl_004 getKda004ApplItmsNum() {
		return kda004ApplItmsNum;
	}

	public void setKda004ApplItmsNum(T_Appl_Dtl_004 kda004ApplItmsNum) {
		this.kda004ApplItmsNum = kda004ApplItmsNum;
	}

	public String getKda009CatgName() {
		return kda009CatgName;
	}

	public void setKda009CatgName(String kda009CatgName) {
		this.kda009CatgName = kda009CatgName;
	}

	public Timestamp getKda009CrtS() {
		return kda009CrtS;
	}

	public void setKda009CrtS(Timestamp kda009CrtS) {
		this.kda009CrtS = kda009CrtS;
	}

	public String getKda009CrtUsrC() {
		return kda009CrtUsrC;
	}

	public void setKda009CrtUsrC(String kda009CrtUsrC) {
		this.kda009CrtUsrC = kda009CrtUsrC;
	}

	
}
