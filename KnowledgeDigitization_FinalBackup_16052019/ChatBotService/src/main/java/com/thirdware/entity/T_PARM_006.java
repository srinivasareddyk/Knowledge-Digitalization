package com.thirdware.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="MKDA011_PARM")
@NamedQuery(name="T_PARM_006.findAll", query="SELECT t FROM T_PARM_006 t")
public class T_PARM_006 implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	public T_PARM_006() {
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="KDA011_PARM_SA_K")
	private Long kda011ParmSaK;
	
	@Column(name="KDA011_PARM_C")
	private String kda011ParmC;
	
	@Column(name="KDA011_PARM_N")
	private String kda011ParmN;
	
	@Column(name="KDA011_PARM_X")
	private String kda011ParmX;
	
	@Column(name="KDA011_PARM_TYP_C")
	private String kda011ParmTypC;
	
	@Column(name="KDA011_PARM_VAL")
	private String kda011ParmVal;
	
	@Column(name="KDA011_ACTV_F")
	private String kda011ActvF;
	
	@Column(name="KDA011_INACTV_Y")
	private String kda011InActvY;
	
	@Column(name="KDA011_EDIT_F")
	private String kda011EditF;
	
	@Column(name="KDA011_CRT_S")
	private Timestamp kda011CrtS;

	@Column(name="KDA011_CRT_USR_C")
	private String kda011CrtUsrC;
	
	@Column(name="KDA011_LST_UPDT_S")
	private Timestamp kda011LstUpdtS;

	@Column(name="KDA011_LST_UPDT_USR_C")
	private String kda011LstUpdtUsrC;

	public Long getKda011ParmSaK() {
		return kda011ParmSaK;
	}

	public void setKda011ParmSaK(Long kda011ParmSaK) {
		this.kda011ParmSaK = kda011ParmSaK;
	}

	public String getKda011ParmC() {
		return kda011ParmC;
	}

	public void setKda011ParmC(String kda011ParmC) {
		this.kda011ParmC = kda011ParmC;
	}

	public String getKda011ParmN() {
		return kda011ParmN;
	}

	public void setKda011ParmN(String kda011ParmN) {
		this.kda011ParmN = kda011ParmN;
	}

	public String getKda011ParmX() {
		return kda011ParmX;
	}

	public void setKda011ParmX(String kda011ParmX) {
		this.kda011ParmX = kda011ParmX;
	}

	public String getKda011ParmTypC() {
		return kda011ParmTypC;
	}

	public void setKda011ParmTypC(String kda011ParmTypC) {
		this.kda011ParmTypC = kda011ParmTypC;
	}

	public String getKda011ParmVal() {
		return kda011ParmVal;
	}

	public void setKda011ParmVal(String kda011ParmVal) {
		this.kda011ParmVal = kda011ParmVal;
	}

	public String getKda011ActvF() {
		return kda011ActvF;
	}

	public void setKda011ActvF(String kda011ActvF) {
		this.kda011ActvF = kda011ActvF;
	}

	public String getKda011InActvY() {
		return kda011InActvY;
	}

	public void setKda011InActvY(String kda011InActvY) {
		this.kda011InActvY = kda011InActvY;
	}

	public String getKda011EditF() {
		return kda011EditF;
	}

	public void setKda011EditF(String kda011EditF) {
		this.kda011EditF = kda011EditF;
	}

	public Timestamp getKda011CrtS() {
		return kda011CrtS;
	}

	public void setKda011CrtS(Timestamp kda011CrtS) {
		this.kda011CrtS = kda011CrtS;
	}

	public String getKda011CrtUsrC() {
		return kda011CrtUsrC;
	}

	public void setKda011CrtUsrC(String kda011CrtUsrC) {
		this.kda011CrtUsrC = kda011CrtUsrC;
	}

	public Timestamp getKda011LstUpdtS() {
		return kda011LstUpdtS;
	}

	public void setKda011LstUpdtS(Timestamp kda011LstUpdtS) {
		this.kda011LstUpdtS = kda011LstUpdtS;
	}

	public String getKda011LstUpdtUsrC() {
		return kda011LstUpdtUsrC;
	}

	public void setKda011LstUpdtUsrC(String kda011LstUpdtUsrC) {
		this.kda011LstUpdtUsrC = kda011LstUpdtUsrC;
	}

			
}
