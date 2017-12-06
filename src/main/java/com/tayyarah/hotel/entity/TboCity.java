package com.tayyarah.hotel.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The persistent class for the TBO_HotelCity database table.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="TBO_HotelCity")
@NamedQuery(name="TboCity.findAll", query="SELECT r FROM TboCity r")
public class TboCity implements Serializable {
	

	@Override
	public String toString() {
		return "TboCity [Cityid=" + Cityid + ", Destination=" + Destination
				+ ", Stateprovince=" + Stateprovince + ", Country=" + Country
				+ ", Countrycode=" + Countrycode + ", Isactivated="
				+ Isactivated + "]";
	}

	private static final long serialVersionUID = 1L;

	
	@Id 	
	@Column(name="Cityid")
	private Integer Cityid;
	
	@Column(name="Destination")
	private String Destination;

	@Column(name="Stateprovince")
	private String Stateprovince;
	
	@Column(name="Country")
	private String Country;
	
	@Column(name="Countrycode")
	private String Countrycode;

	@Column(name="Isactivated")
	private String Isactivated;

	
	//bi-directional many-to-one association to HotelSearchCity
	/*@OneToOne(mappedBy="tboCity")
	private HotelSearchCity hotelCity;
*/
	public TboCity() {
	}
	public Integer getCityid() {
		return Cityid;
	}

	public void setCityid(Integer cityid) {
		Cityid = cityid;
	}

	public String getDestination() {
		return Destination;
	}

	public void setDestination(String destination) {
		Destination = destination;
	}

	public String getStateprovince() {
		return Stateprovince;
	}

	public void setStateprovince(String stateprovince) {
		Stateprovince = stateprovince;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getCountrycode() {
		return Countrycode;
	}

	public void setCountrycode(String countrycode) {
		Countrycode = countrycode;
	}

	public String getIsactivated() {
		return Isactivated;
	}

	public void setIsactivated(String isactivated) {
		Isactivated = isactivated;
	}
	
	
	
	
}