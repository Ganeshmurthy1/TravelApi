package com.tayyarah.hotel.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;


/**
 * The persistent class for the rsamenitytypes database table.
 * 
 */
@Entity
@Table(name="rsamenitytypes")
@JsonInclude(JsonInclude.Include.NON_NULL)
//@NamedQuery(name="Rsamenitytype.findAll", query="SELECT r FROM Rsamenitytype r")
public class RzAmenityType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int amenityid;
	

	@Column(name="amenitydesc")
	private String amenityDesc;

	@Column(name="amenityname")
	private String amenityName;

	@Column(name="amenitytype")
	private String amenityType;
	
	public RzAmenityType() {
	}

	public int getAmenityid() {
		return this.amenityid;
	}

	public void setAmenityid(int amenityid) {
		this.amenityid = amenityid;
	}

	public String getAmenityDesc() {
		return this.amenityDesc;
	}

	public void setAmenityDesc(String amenityDesc) {
		this.amenityDesc = amenityDesc;
	}

	public String getAmenityName() {
		return this.amenityName;
	}

	public void setAmenityName(String amenityName) {
		this.amenityName = amenityName;
	}

	public String getAmenityType() {
		return this.amenityType;
	}

	public void setAmenityType(String amenityType) {
		this.amenityType = amenityType;
	}

	@Override
	public String toString() {
		return "RzAmenityType [amenityid=" + amenityid + ", amenityDesc="
				+ amenityDesc + ", amenityName=" + amenityName
				+ ", amenityType=" + amenityType + "]";
	}

}