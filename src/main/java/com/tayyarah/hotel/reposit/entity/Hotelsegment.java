package com.tayyarah.hotel.reposit.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the hotelsegments database table.
 * 
 */
@Entity
@Table(name="hotelsegments")
public class Hotelsegment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="segment_id")
	private int segmentId;
	@Column(name="countrys_excluded")
	private String countrysExcluded;
	@Column(name="countrys_included")
	private String countrysIncluded;
	@Temporal(TemporalType.DATE)
	@Column(name="from_date")
	private Date fromDate;
	@Column(name="segmen_desc")
	private String segmenDesc;
	private String segment_type;
	@Temporal(TemporalType.DATE)
	@Column(name="to_date")
	private Date toDate;
	private int vendorid;
	

	public int getSegmentId() {
		return this.segmentId;
	}
	public void setSegmentId(int segmentId) {
		this.segmentId = segmentId;
	}
	public String getCountrysExcluded() {
		return this.countrysExcluded;
	}
	public void setCountrysExcluded(String countrysExcluded) {
		this.countrysExcluded = countrysExcluded;
	}
	public String getCountrysIncluded() {
		return this.countrysIncluded;
	}
	public void setCountrysIncluded(String countrysIncluded) {
		this.countrysIncluded = countrysIncluded;
	}
	public Date getFromDate() {
		return this.fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public String getSegmenDesc() {
		return this.segmenDesc;
	}
	public void setSegmenDesc(String segmenDesc) {
		this.segmenDesc = segmenDesc;
	}
	public String getSegment_type() {
		return this.segment_type;
	}
	public void setSegment_type(String segment_type) {
		this.segment_type = segment_type;
	}
	public Date getToDate() {
		return this.toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public int getVendorid() {
		return this.vendorid;
	}
	public void setVendorid(int vendorid) {
		this.vendorid = vendorid;
	}
}