package com.tayyarah.hotel.reposit.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.math.BigInteger;


/**
 * The persistent class for the hotelroomfares database table.
 * 
 */
@Entity
@Table(name="hotelroomfares")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Hotelroomfare implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="fareid")
	private String fareid;
	@Column(name="fit_normal_wd")
	private BigDecimal fitNormalWd;
	@Column(name="fit_normal_wd_with_breakfast")
	private BigDecimal fitNormalWdWithBreakfast;
	@Column(name="git_peak_we")
	private BigDecimal gitPeakWe;
	@Column(name="git_peak_we_type")
	private String gitPeakWeType;
	private Integer roomno;
	private Integer segmentid;
	private Integer vendorid;
	
	public String getFareid() {
		return this.fareid;
	}
	public void setFareid(String fareid) {
		this.fareid = fareid;
	}
	public BigDecimal getFitNormalWd() {
		return this.fitNormalWd;
	}
	public void setFitNormalWd(BigDecimal fitNormalWd) {
		this.fitNormalWd = fitNormalWd;
	}
	public BigDecimal getFitNormalWdWithBreakfast() {
		return this.fitNormalWdWithBreakfast;
	}
	public void setFitNormalWdWithBreakfast(BigDecimal fitNormalWdWithBreakfast) {
		this.fitNormalWdWithBreakfast = fitNormalWdWithBreakfast;
	}
	public BigDecimal getGitPeakWe() {
		return this.gitPeakWe;
	}
	public void setGitPeakWe(BigDecimal gitPeakWe) {
		this.gitPeakWe = gitPeakWe;
	}
	public String getGitPeakWeType() {
		return this.gitPeakWeType;
	}
	public void setGitPeakWeType(String gitPeakWeType) {
		this.gitPeakWeType = gitPeakWeType;
	}
	public Integer getRoomno() {
		return this.roomno;
	}
	public void setRoomno(Integer roomno) {
		this.roomno = roomno;
	}
	public Integer getSegmentid() {
		return this.segmentid;
	}
	public void setSegmentid(Integer segmentid) {
		this.segmentid = segmentid;
	}
	public Integer getVendorid() {
		return this.vendorid;
	}
	public void setVendorid(Integer vendorid) {
		this.vendorid = vendorid;
	}
}