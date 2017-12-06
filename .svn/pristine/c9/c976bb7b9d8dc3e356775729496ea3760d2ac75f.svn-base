package com.tayyarah.hotel.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;


/**
 * The persistent class for the reznext_city database table.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="reznext_city")
@NamedQuery(name="ReznextCity.findAll", query="SELECT r FROM ReznextCity r")
public class ReznextCity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String city;

	private String country;

	@Column(name="country_code")
	private String countryCode;

	private String state;

	//bi-directional many-to-one association to HotelSearchCity
	@OneToMany(mappedBy="reznextCity")
	private List<HotelSearchCity> hotelCities;

	public ReznextCity() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<HotelSearchCity> getHotelCities() {
		return this.hotelCities;
	}

	public void setHotelCities(List<HotelSearchCity> hotelCities) {
		this.hotelCities = hotelCities;
	}

	public HotelSearchCity addHotelCity(HotelSearchCity hotelCity) {
		getHotelCities().add(hotelCity);
		hotelCity.setReznextCity(this);

		return hotelCity;
	}

	public HotelSearchCity removeHotelCity(HotelSearchCity hotelCity) {
		getHotelCities().remove(hotelCity);
		hotelCity.setReznextCity(null);

		return hotelCity;
	}

}