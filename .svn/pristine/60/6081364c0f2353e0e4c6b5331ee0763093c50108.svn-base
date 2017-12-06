package com.tayyarah.hotel.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;


/**
 * The persistent class for the rezlive_city database table.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="rezlive_city")
@NamedQuery(name="RezliveCity.findAll", query="SELECT r FROM RezliveCity r")
public class RezliveCity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String city;

	@Column(name="country_code")
	private String countryCode;

	private String state;

	//bi-directional many-to-one association to HotelSearchCity
	@OneToMany(mappedBy="rezliveCity")
	private List<HotelSearchCity> hotelCities;

	public RezliveCity() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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
		hotelCity.setRezliveCity(this);

		return hotelCity;
	}

	public HotelSearchCity removeHotelCity(HotelSearchCity hotelCity) {
		getHotelCities().remove(hotelCity);
		hotelCity.setRezliveCity(null);

		return hotelCity;
	}

}