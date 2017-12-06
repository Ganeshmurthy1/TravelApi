package com.tayyarah.hotel.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;


/**
 * The persistent class for the tg_city database table.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name="tg_city")
@NamedQuery(name="TgCity.findAll", query="SELECT t FROM TgCity t")
public class TgCity implements Serializable {
	/*@Override
	public String toString() {
		return "TgCity [id=" + id + ", city=" + city + ", countryCode=" + countryCode + ", countryName=" + countryName
				+ ", hotelCities=" + hotelCities + "]";
	}*/

	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String city;

	@Column(name="country_code")
	private String countryCode;
	
	@Column(name="country_name")
	private String countryName;

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	//bi-directional many-to-one association to HotelSearchCity
	@OneToMany(mappedBy="tgCity",fetch= FetchType.LAZY)
	private List<HotelSearchCity> hotelCities;

	public TgCity() {
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

	public String getCountryCodexx() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public List<HotelSearchCity> getHotelCities() {
		return this.hotelCities;
	}

	public void setHotelCities(List<HotelSearchCity> hotelCities) {
		this.hotelCities = hotelCities;
	}

	public HotelSearchCity addHotelCity(HotelSearchCity hotelCity) {
		getHotelCities().add(hotelCity);
		hotelCity.setTgCity(this);

		return hotelCity;
	}

	public HotelSearchCity removeHotelCity(HotelSearchCity hotelCity) {
		getHotelCities().remove(hotelCity);
		hotelCity.setTgCity(null);

		return hotelCity;
	}

}