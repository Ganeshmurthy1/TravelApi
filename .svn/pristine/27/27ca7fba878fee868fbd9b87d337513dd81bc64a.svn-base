package com.tayyarah.hotel.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;



/**
 * The persistent class for the hotel_city database table.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="hotel_city")
@NamedQuery(name="HotelSearchCity.findAll", query="SELECT h FROM HotelSearchCity h")
public class HotelSearchCity implements Serializable {
	
	
	
	private static final long serialVersionUID = 1L;
	private String aliasname;
	private String aliasname2;
	private String city;
	@Column(name="country_code")
	private String countryCode;	
	@Column(name = "autocompleter_flag" ,columnDefinition="BIT(1) default 0")	
	private Boolean autocompleterFlag;	
	@Column(name="duplicate_id_data")
	private String duplicateIdData;	

	@Id
	private Integer id;
	private String state;

	//bi-directional many-to-one association to TgCity
	@ManyToOne
	@JoinColumn(name="id_TG")
	private TgCity tgCity;

	//bi-directional many-to-one association to ReznextCity
	@ManyToOne
	@JoinColumn(name="id_reznext")
	private ReznextCity reznextCity;

	//bi-directional many-to-one association to RezliveCity
	@ManyToOne
	@JoinColumn(name="id_rezlive")
	private RezliveCity rezliveCity;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="id_TBO")
	private TboCity tboCity;		
	

	public String getAliasname() {
		return this.aliasname;
	}

	public void setAliasname(String aliasname) {
		this.aliasname = aliasname;
	}

	public String getAliasname2() {
		return this.aliasname2;
	}

	public void setAliasname2(String aliasname2) {
		this.aliasname2 = aliasname2;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public TgCity getTgCity() {
		return this.tgCity;
	}

	public void setTgCity(TgCity tgCity) {
		this.tgCity = tgCity;
	}

	public ReznextCity getReznextCity() {
		return this.reznextCity;
	}

	public void setReznextCity(ReznextCity reznextCity) {
		this.reznextCity = reznextCity;
	}

	public RezliveCity getRezliveCity() {
		return this.rezliveCity;
	}

	public void setRezliveCity(RezliveCity rezliveCity) {
		this.rezliveCity = rezliveCity;
	}


	public TboCity getTboCity() {
		return tboCity;
	}

	public void setTboCity(TboCity tboCity) {
		this.tboCity = tboCity;
	}

	public Boolean getAutocompleterFlag() {
		return autocompleterFlag;
	}

	public void setAutocompleterFlag(Boolean autocompleterFlag) {
		this.autocompleterFlag = autocompleterFlag;
	}

	public String getDuplicateIdData() {
		return duplicateIdData;
	}

	public void setDuplicateIdData(String duplicateIdData) {
		this.duplicateIdData = duplicateIdData;
	}
}