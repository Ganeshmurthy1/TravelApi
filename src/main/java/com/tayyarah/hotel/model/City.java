package com.tayyarah.hotel.model;



import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;


/**
 * The persistent class for the city database table.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "city")
//@NamedQuery(name="City.findAll", query="SELECT c FROM City c")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class City implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String City;

	public City() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	@Override
	public String toString() {
		return "City [id=" + id + ", City=" + City + "]";
	}



}