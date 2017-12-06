package com.tayyarah.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@Entity
@Table(name = "airport")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Airport implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="airport_code")	
	private String airport_code ;	
	@Column(name="city")
	private String city;
	@Column(name="country")
	private String country ;
	@Column(name="airport_name")
	private String airport_name ;
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAirport_name() {
		return airport_name;
	}
	public void setAirport_name(String airport_name) {
		this.airport_name = airport_name;
	}
	public String getAirport_code() {
		return airport_code;
	}
	public void setAirport_code(String airport_code) {
		this.airport_code = airport_code;
	}

	@Override
	public String toString() {
		return "Airport [city=" + city + ", country=" + country
				+ ", airport_name=" + airport_name + ", airport_code="
				+ airport_code + "]";
	}

}
